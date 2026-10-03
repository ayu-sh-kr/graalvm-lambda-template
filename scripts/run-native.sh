#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")/.."

# The local binary uses Spring's local profile; the image uses prod.
if [[ "${1:-}" == "--local" ]]; then
  exec env SPRING_PROFILES_ACTIVE=local ./target/native-lambda
fi

image="${1:-native-lambda:latest}"
port="${2:-9000}"

exec docker run --rm \
  --platform linux/arm64 \
  --publish "$port:8080" \
  --env SPRING_PROFILES_ACTIVE=prod \
  --env SPRING_CLOUD_FUNCTION_DEFINITION=functionRouter \
  "$image"
