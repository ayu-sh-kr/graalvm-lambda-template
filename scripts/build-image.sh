#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")/.."

# Match the source backend's Lambda architecture and image options.
image="${1:-native-lambda:latest}"

exec docker build \
  --pull \
  --platform linux/arm64 \
  --provenance=false \
  --sbom=false \
  --tag "$image" \
  .
