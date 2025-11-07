import json
import os
import time

import requests
from requests import Response

from utils_python.print import _print_t


def require_env(name: str) -> str:
    res = os.environ.get(name)

    if res is None:
        raise Exception("Env variable {} not found".format(name))

    return res


DMS_API_ENDPOINT = require_env("DMS_API_ENDPOINT")
DMS_API_TOKEN = require_env("DMS_API_TOKEN")

DEFAULT_HEADERS = {
    "Authorization": "Bearer " + DMS_API_TOKEN,
    "Content-Type": "application/json",
}

DEPLOY_TIMEOUT_SECONDS = 600
DEPLOY_CHECK_DELAY_SECONDS = 10
DEPLOY_CHECK_ATTEMPTS = round(DEPLOY_TIMEOUT_SECONDS / DEPLOY_CHECK_DELAY_SECONDS)
RETRY_DELAY_SECONDS = 5
ENV_TYPE = "TEST"
ENV_TTL_MINUTES = round(DEPLOY_TIMEOUT_SECONDS / 60) + 10


def setup_namespace(ref: str, pipeline_id: str, job_id: str, version: str, profile: str) -> str:
    _print_t("start of creating namespace")

    user = require_env("GITLAB_USER_LOGIN")

    init_result = __setup_namespace_init(ref, pipeline_id, job_id, version, profile, user)
    result = __setup_namespace_wait(init_result["auto-deploy-id"])
    __prolong(init_result["auto-deploy-id"])

    env = result["env"]

    _print_t("Env initialized: {}".format(env))
    return env


def __setup_namespace_init(ref: str, pipeline_id: str, job_id: str, version: str, profile: str, user: str):
    url = "{}/integrations/auto-deployment".format(DMS_API_ENDPOINT)
    headers = {"idempotency-key": job_id} | DEFAULT_HEADERS
    payload = {
        "auto-clean-delay-minutes": ENV_TTL_MINUTES,
        "type": ENV_TYPE,
        "user": user,
        "purpose": "http-tests of pipeline {}".format(pipeline_id),
        "apps": [
            {
                "app": "moysklad",
                "profile": profile,
                "version": {
                    "ref": ref,
                    "version": version
                }
            }
        ]
    }

    _print_t("request: POST {}\n{}".format(url, json.dumps(payload)))

    def call():
        with requests.post(url, headers=headers, json=payload) as response:
            return check_response(response)

    return retry(call)


def __setup_namespace_wait(auto_deploy_id: str):
    url = "{}/integrations/auto-deployment/{}".format(DMS_API_ENDPOINT, auto_deploy_id)
    headers = DEFAULT_HEADERS

    for _ in range(DEPLOY_CHECK_ATTEMPTS):
        time.sleep(DEPLOY_CHECK_DELAY_SECONDS)

        _print_t("request: GET {}".format(url))

        def call():
            with requests.get(url, headers=headers) as response:
                return check_response(response)

        body = retry(call)
        if not is_pending_status(body["status"]):
            return body

    raise EnvManagementException("Deploy timeout")


def __prolong(auto_deploy_id: str):
    url = "{}/integrations/auto-deployment/{}".format(DMS_API_ENDPOINT, auto_deploy_id)
    headers = DEFAULT_HEADERS

    _print_t("request: PUT {}".format(url))

    def call():
        with requests.put(url, headers=headers) as response:
            return check_response(response, False)

    retry(call)


def check_response(response: Response, parse_body: bool = True):
    status = response.status_code

    if status == 200:

        if not parse_body:
            return

        body = response.json()

        _print_t("response body:\n{}".format(body))

        if is_error_status(body["status"]):
            raise EnvManagementException("DMS Request failed")

        return body

    _print_t("Error HTTP response {}:\n{}".format(response.status_code, response.text))

    if status == 502 or status == 503 or status == 404:
        raise RetryableException(EnvManagementException("DMS temporary unavailable"))

    if status == 401 or status == 403:
        raise EnvManagementException("Bad credentials")

    if status == 400:
        raise EnvManagementException("Bad request")

    raise EnvManagementException("Unexpected error status")


def is_error_status(status: str) -> bool:
    return status == "FAILED" or status == "CANCELED" or status == "PARTIAL"


def is_pending_status(status: str) -> bool:
    return status == "PENDING" or status == "CANCELING"


def retry(func, times: int = 5):
    counter = times
    while True:
        counter -= 1
        try:
            return func()
        except RetryableException as e:
            if counter <= 0:
                _print_t("retry attempts exceeded")
                raise e.__cause__

            _print_t("error occurred. Retry after {} seconds: {}".format(RETRY_DELAY_SECONDS, str(e.__cause__)))

            time.sleep(RETRY_DELAY_SECONDS)


def delete_namespace(env: str, pipeline_id: str, job_id: str) -> None:
    _print_t("deleting env {}".format(env))

    user = require_env("GITLAB_USER_LOGIN")

    url = "{}/integrations/auto-deployment/env".format(DMS_API_ENDPOINT)
    headers = {"idempotency-key": job_id} | DEFAULT_HEADERS
    payload = {
        "type": ENV_TYPE,
        "env": env,
        "user": user,
        "purpose": "http-tests of pipeline {}".format(pipeline_id)
    }

    _print_t("request: DELETE {}\n{}".format(url, json.dumps(payload)))

    def call():
        with requests.delete(url, headers=headers, json=payload) as response:
            check_response(response, parse_body=False)

    retry(call)


class EnvManagementException(Exception):
    pass


class RetryableException(Exception):
    pass
