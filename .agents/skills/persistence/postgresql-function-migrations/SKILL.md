---
name: postgresql-function-migrations
description: Create versioned PostgreSQL functions and triggers through Flyway when PostgreSQL persistence exists.
---

# Postgresql Function Migrations

- Confirm PostgreSQL/Flyway exists or is requested. Inspect current function signatures, dependent triggers, tables, and migration order.
- Add a new versioned migration; use CREATE OR REPLACE only when PostgreSQL permits the signature change.
- Keep search paths, privileges, transaction behavior, return types, and trigger timing explicit.
- Handle nulls and cardinality deliberately; avoid hidden behavior different from repository constraints.
- Test function outcomes, failure cases, and triggers using Testcontainers, then apply relevant migration/formatting skills.
