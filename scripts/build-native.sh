#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")/.."

# Use the source project's local AOT profile when requested.
profile=native
if [[ "${1:-}" == "--local" ]]; then
  profile=native,native-local
  shift
fi

exec ./mvnw "-P$profile" native:compile "$@"
