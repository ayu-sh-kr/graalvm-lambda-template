---
name: jdbc-kotlin-batch-writes
description: Implement Kotlin JDBC batch inserts, updates, and upserts with clear constraints and count handling.
---

# Jdbc Kotlin Batch Writes

- Confirm persistence exists or is requested. Inspect migrations, uniqueness constraints, row mappers, and existing transaction boundaries.
- Use NamedParameterJdbcTemplate.batchUpdate with explicit named parameter sets when the operation requires batches.
- Keep SQL grouped by feature; use ON CONFLICT only with the intended unique constraint and explicit update semantics.
- Distinguish input count from actual affected rows. Handle JDBC SUCCESS_NO_INFO and EXECUTE_FAILED explicitly rather than summing blindly.
- Validate exact/bounded counts according to the business contract and keep generated-key ordering assumptions documented.
- Test empty inputs, partial conflicts, rollback behavior, and generated keys against the supported database.
- Log counts and stable context, never private row payloads. Avoid adding shared guard utilities merely to satisfy a stale skill example.
