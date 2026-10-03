---
name: sql-migration-formatting
description: Format Flyway SQL consistently with existing migration style without changing behavior.
---

# Sql Migration Formatting

- Confirm migrations exist or are explicitly being added. Inspect existing SQL as the style reference; do not invent a canonical file.
- Keep indentation, comma placement, identifiers, constraint layout, and function delimiters consistent.
- Preserve SQL behavior and migration ordering. Do not rename or modify already-applied migrations for cosmetic cleanup.
- Keep multiline DDL readable and comments focused on constraints or operational intent.
- Validate SQL with the supported database when edits affect syntax.
