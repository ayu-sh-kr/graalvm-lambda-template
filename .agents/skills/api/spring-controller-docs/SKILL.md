---
name: spring-controller-docs
description: Document implemented HTTP contracts and controller boundaries in Kotlin Spring applications.
---

# Spring Controller Docs

- Read controllers, services, DTOs, filters, and integration tests before documenting behavior.
- Explain inputs, safe errors, status codes, side effects, and authorization actually implemented.
- Distinguish AWS_IAM Function URL authentication from application authentication; local HTTP has no IAM boundary.
- Describe /health as application readiness; do not claim it verifies databases or providers without code evidence.
- Add concise KDoc for a meaningful boundary. Use OpenAPI annotations only if installed or explicitly requested.
- Keep documentation-only work from altering routes, wrappers, or response types.
- Compile and run focused integration tests if changing mapping annotations.
