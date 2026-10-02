---
name: api-route-conventions
description: Design or revise HTTP routes in a Kotlin Spring MVC application.
---

# Api Route Conventions

Inspect the controller and integration tests before changing a public contract.
- Preserve existing HTTP methods, field names, response shapes, and status codes unless a migration is requested.
- Choose GET for reads without a request body, POST for creation or explicit operations, and PUT/PATCH/DELETE where their semantics fit.
- Use feature-owned routes with consistent naming. Do not put credentials or capability tokens in query strings.
- Keep Lambda Web Adapter routing identical to the local Spring application; configure a base path deliberately if adding API Gateway.
- Keep /health fast and dependency-free in this sample; document stronger readiness checks if added.
- Add controller integration tests using api-integration-test. Run scripts/test.sh and trace changed binding paths.
