#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")/.."

port="${1:-9000}"

# Send the same HTTP API v2 event format used by the source status script.
exec curl --fail-with-body --silent --show-error \
  --request POST \
  --header 'Content-Type: application/json' \
  --data '{"version":"2.0","routeKey":"$default","rawPath":"/hello","requestContext":{"http":{"method":"GET","path":"/hello"}},"headers":{},"body":null,"isBase64Encoded":false}' \
  "http://localhost:$port/2015-03-31/functions/function/invocations"
