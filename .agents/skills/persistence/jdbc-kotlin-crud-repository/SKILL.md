---
name: jdbc-kotlin-crud-repository
description: Implement Kotlin JdbcClient repositories with correct lookup cardinality, SQL placement, and write-count validation.
---

# Jdbc Kotlin Crud Repository

- Confirm JDBC exists or is explicitly being added. Inspect schema constraints before choosing a single, optional, or list return type.
- Use constructor-injected JdbcClient, named SQL parameters, and explicit row mappers.
- Keep shared queries in a discoverable feature/grouped location and avoid reflective query(Class) when explicit mapping is appropriate.
- Validate write counts against the business contract: exact, zero-or-one, or bounded affected rows.
- Put transaction ownership in services when operations must commit atomically.
- Test constraints, missing rows, insert/update/upsert behavior, and side effects against a real supported database via Testcontainers.
- Apply central native registration and JSON field coverage when models require binding.
