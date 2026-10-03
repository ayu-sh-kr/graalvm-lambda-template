---
name: code-structure
description: Organize Kotlin Spring applications by feature with shared configuration and mirrored tests.
---

# Code Structure

- Keep feature packages under com.example.lambda; use greeting and health as sample references.
- Keep controllers thin and services responsible for business rules. Add repositories and transaction boundaries only when persistence exists.
- Place cross-cutting configuration under config or a shared platform package when warranted.
- Keep all application reflection binding in config/NativeReflectionConfiguration.kt. Keep generated supplemental metadata in the single native-image resource directory.
- Mirror feature packages under integration for full-context tests and mock for isolated unit tests when needed.
- Keep configuration properties and resources close to their owners. Do not add unused infrastructure, placeholder layers, or empty shared utilities.
- When introducing JDBC, share SQL and explicit row mappers in a discoverable location; use persistence skills only for that integration.
- Update the explicit main class and script/resource paths when renaming application packages.
