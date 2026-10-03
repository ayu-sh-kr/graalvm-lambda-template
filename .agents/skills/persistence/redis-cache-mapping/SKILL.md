---
name: redis-cache-mapping
description: Design and test Redis JSON serializers, cache keys, and native binding when Redis is introduced.
---

# Redis Cache Mapping

- Confirm Redis exists or is explicitly requested. Inspect current key naming, TTLs, versioning, and serializer configuration.
- Use the configured application JsonMapper. Validate required fields and distinguish empty collections from cache misses.
- Treat malformed/incompatible payloads as safe cache misses when the cache contract allows it; log context without private payloads.
- Keep cache namespaces and key scope consistent with authorization/tenant boundaries.
- Attach a custom serializer only to its intended cache and avoid changing unrelated default serializers.
- Test round trips, null/empty values, malformed JSON, missing fields, and compatibility with existing payloads.
- Register serialized models centrally and cover fields through dto-integration-test and native tests.
