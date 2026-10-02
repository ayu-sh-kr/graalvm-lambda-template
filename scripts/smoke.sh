#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."
base_url="${1:-http://localhost:8080}"
python3 - "$base_url" <<'PY'
import json, sys, urllib.error, urllib.request
base = sys.argv[1].rstrip('/')
def call(path, data=None):
    request = urllib.request.Request(base + path, data=data, headers={'Content-Type': 'application/json'})
    with urllib.request.urlopen(request, timeout=15) as response:
        assert response.status == 200, response.status
        return json.load(response)
assert call('/health') == {'status': 'UP'}
assert call('/hello') == {'message': 'Hello, World!', 'tags': []}
assert call('/hello?name=Ada') == {'message': 'Hello, Ada!', 'tags': []}
assert call('/hello', b'{"name":" Ada ","tags":["native"]}') == {'message': 'Hello, Ada!', 'tags': ['native']}
assert call('/hello', b'{}') == {'message': 'Hello, World!', 'tags': []}
try:
    call('/hello', b'{')
except urllib.error.HTTPError as error:
    assert error.code == 400, error.code
else:
    raise AssertionError('Malformed JSON must return HTTP 400')
print('Native HTTP smoke checks passed.')
PY
