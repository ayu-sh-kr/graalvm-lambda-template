---
name: flyway-migrations
description: Create and review Flyway migrations when a Kotlin application adds or changes persistence.
---

# Flyway Migrations

- Confirm JDBC/Flyway is present or explicitly requested; the template has no database dependency.
- Inspect existing migration locations, naming, and configured Flyway settings. Use the next ordered version; never edit an applied migration.
- Match table/column nullability, constraints, indexes, defaults, and data backfills to the feature contract.
- Keep migrations deterministic and safe for populated tables. Document destructive or operationally expensive changes.
- Test migrations against the supported database using Testcontainers. Do not run against production from tests.
- Follow sql-migration-formatting and the relevant Kotlin JDBC skills when adding models/repositories.
