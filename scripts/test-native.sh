#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."
command -v native-image >/dev/null || { echo "Use GraalVM JDK 25 with native-image installed." >&2; exit 1; }
exec ./mvnw -B clean -PnativeTest test "$@"
