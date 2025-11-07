#!/usr/bin/env python3

import sys

from utils_python.client import delete_namespace


def main():
    if len(sys.argv) != 4:
        sys.exit('Usage: python3 env_clean <namespace> <pipeline_id> <job_id>')

    namespace = sys.argv[1]
    pipeline_id = sys.argv[2]
    job_id = sys.argv[3]

    delete_namespace(namespace, pipeline_id, job_id)


if __name__ == "__main__":
    main()
