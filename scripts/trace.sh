#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."
command -v native-image >/dev/null || { echo "Use GraalVM JDK 25 with native-image installed." >&2; exit 1; }
agent_directory="$(mktemp -d "${TMPDIR:-/tmp}/native-lambda-agent.XXXXXX")"
trap 'rm -rf "$agent_directory"' EXIT
# Regenerate only after the full suite succeeds; never merge stale application metadata.
./mvnw -B clean test '-Dtest=*IntegrationTests' \
  "-DargLine=-agentlib:native-image-agent=caller-filter-file=${PWD}/src/test/resources/native-image-agent-filter.json,access-filter-file=${PWD}/src/test/resources/native-image-agent-filter.json,config-output-dir=${agent_directory}"
metadata="src/main/resources/META-INF/native-image/com.example/native-lambda"
if [[ ! -s "$agent_directory/reachability-metadata.json" && ! -s "$agent_directory/reflect-config.json" ]]; then
  echo "Agent produced no reflection metadata." >&2
  exit 1
fi
mkdir -p "$metadata"
for file in reflect-config.json resource-config.json proxy-config.json serialization-config.json jni-config.json predefined-classes-config.json reachability-metadata.json; do
  rm -f "$metadata/$file"
done
cp "$agent_directory"/*.json "$metadata/"
if [[ -d "$agent_directory/agent-extracted-predefined-classes" ]]; then
  cp -R "$agent_directory/agent-extracted-predefined-classes" "$metadata/"
fi
echo "Metadata refreshed. Review the diff, then run scripts/test-native.sh."
