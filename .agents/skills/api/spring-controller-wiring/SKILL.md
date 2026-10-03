---
name: spring-controller-wiring
description: Add or revise thin Kotlin Spring MVC controllers and their service boundaries.
---

# Spring Controller Wiring

- Read the feature service, request/response models, and existing endpoint tests first.
- Use constructor injection and @RestController. Delegate business behavior to the owning service.
- Use named function beans only when the application introduces that abstraction; do not add Spring Cloud Function for a simple HTTP adapter.
- Preserve request bodies, status codes, authorization rules, and externally visible outcomes.
- Keep tokens private and derive internal identities from the implemented authentication boundary.
- Register new models in NativeReflectionConfiguration and test HTTP binding and JSON conversion.
- Add OpenAPI annotations only when the project includes that dependency. Do not change a return type merely to document it.
