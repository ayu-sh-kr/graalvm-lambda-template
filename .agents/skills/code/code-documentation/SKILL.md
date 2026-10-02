---
name: code-documentation
description: Write concise Kotlin KDoc and comments that explain intent, constraints, and operational behavior.
---

# Code Documentation

- Read the source and tests before describing behavior.
- Explain why a type exists and where it is constructed or consumed. Document fields when constraints, nullability, serialization, or security implications matter.
- Document methods for non-obvious ordering, side effects, transactions, native restrictions, and failure contracts.
- Avoid restating a name or signature. Keep KDoc to a few contextual lines and inline comments to non-obvious intent.
- Add OpenAPI documentation only if the project includes it; do not add dependencies solely to document a trivial endpoint.
- Keep README commands and native build paths synchronized with scripts and pom.xml.
