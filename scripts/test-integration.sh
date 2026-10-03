#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")/.."

# Record JVM reachability first, then run the integration suite natively.
scripts/trace.sh "$@"
exec scripts/test-native.sh '-Dtest=*IntegrationTests' "$@"
