---
name: clean-code
description: Write idiomatic, readable Kotlin with immutable models, clear names, and compact formatting.
---

# Clean Code

- Follow the nearest feature for style; use two-space indentation, explicit imports, and trailing commas in wrapped declarations.
- Name methods as actions and types by their responsibility. Prefer immutable data classes and val properties.
- Use expression bodies for a clear single operation; use blocks for validation and sequential transformations.
- Keep data flow linear. Avoid nested scope-function chains and helpers that merely hide a one-line operation.
- Use named arguments for multi-parameter domain calls when they clarify intent. Wrap only when a grouped line becomes difficult to read.
- Extract shared constants or independently meaningful operations; keep single-use, simple logic near its owner.
- Use buildMap/buildString for iterative construction, direct string templates for simple fixed strings.
- Add comments for non-obvious intent, not to narrate syntax. Apply code-quality and native-binding guidelines from AGENTS.md.
