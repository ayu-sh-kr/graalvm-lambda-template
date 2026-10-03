---
name: jdbc-kotlin-row-mapper
description: Write explicit Kotlin Spring RowMappers with safe nullability, SQL projection alignment, and time conversion.
---

# Jdbc Kotlin Row Mapper

- Inspect the exact SQL projection and schema before mapping fields; do not assume an entire table is selected.
- Prefer explicit constructor mapping over reflective JdbcClient.query(Class) for native applications.
- Handle JDBC primitive null values using getObject or wasNull as appropriate.
- Map time types consistently with the domain and database timezone semantics.
- Keep shared mappers in one discoverable file when reused; keep feature-specific projections with their feature.
- Test each projected field, nullable value, and conversion using realistic database rows.
- Apply jdbc-kotlin-entity-mapping for schema-to-model design and dto-integration-test for JSON binding.
