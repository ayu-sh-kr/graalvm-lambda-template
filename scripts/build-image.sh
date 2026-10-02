#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."
command -v docker >/dev/null || { echo "Docker with buildx is required." >&2; exit 1; }
exec docker buildx build --platform "${PLATFORM:-linux/amd64}" --provenance=false \
  --load -t "${IMAGE_NAME:-native-lambda:local}" . "$@"
