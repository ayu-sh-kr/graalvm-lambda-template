---
name: repository-cache
description: Introduce or revise repository caching when cache infrastructure exists or is explicitly requested.
---

# Repository Cache

- Confirm the cache dependency/infrastructure is present or part of the task. The template ships without caching.
- Inspect every read/write path and authorization scope before selecting keys and cache entries.
- Include tenant/user dimensions where needed. Avoid caching unsafe or incomplete projections.
- Define TTLs and eviction after successful mutations; test failures, transaction rollback, and stale-data behavior.
- Follow redis-cache-mapping for custom serializers and JSON contracts.
- Verify cache annotations/keys and real observable read/write behavior; register binding types centrally for native execution.
