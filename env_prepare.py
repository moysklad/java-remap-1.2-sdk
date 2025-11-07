#!/usr/bin/env python3

import sys

from utils_python.client import setup_namespace


def write_namespace_to_file(env: str):
    with open("namespace-export-file", "w+") as file:
        file.truncate(0)
        file.write(env)
        print("Namespace: {} is successfully exported to file".format(env))


def main():
    if len(sys.argv) != 6:
        sys.exit("Usage: python3 env_prepare <ref> <pipeline_id> <job_id> <profile>")

    ref = sys.argv[1]
    pipeline_id = sys.argv[2]
    job_id = sys.argv[3]
    version = sys.argv[4]
    profile = sys.argv[5]

    namespace = setup_namespace(ref, pipeline_id, job_id, version, profile)

    write_namespace_to_file(namespace)


if __name__ == "__main__":
    main()
