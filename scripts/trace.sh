#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")/.."

metadata_directory=src/main/resources/META-INF/native-image/com.example/native-lambda
caller_filter=src/test/resources/native-image-agent-caller-filter.json

# Later -Dtest arguments can select a smaller integration suite.
exec ./mvnw clean -Pnative test '-Dtest=*IntegrationTests' "$@" \
  "-DargLine=-agentlib:native-image-agent=caller-filter-file=$caller_filter,config-merge-dir=$metadata_directory"
