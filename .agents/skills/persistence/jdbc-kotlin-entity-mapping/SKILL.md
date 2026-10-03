---
name: jdbc-kotlin-entity-mapping
description: Map database schema to Kotlin data classes and explicit Spring RowMappers when JDBC persistence exists.
---

# Jdbc Kotlin Entity Mapping

- Confirm the requested persistence integration exists; inspect migrations and exact column names, types, and nullability.
- Put immutable data-class entities in their feature package. Include fields required by the domain rather than copying every database column blindly.
- Use explicit row mappers with nullable-safe JDBC access. Map timestamp-with-time-zone through OffsetDateTime to the domain's documented time type.
- Keep snake_case SQL columns aligned with camelCase properties and test all nullable/non-null cases.
- Keep shared mapping in one discoverable location when several repositories use it.
- Register binding entities centrally and add Jackson integration tests accessing every field; add database mapping coverage separately.
