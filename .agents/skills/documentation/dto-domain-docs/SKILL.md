---
name: dto-domain-docs
description: Document Kotlin DTOs, entities, projections, and transport contracts with concise KDoc.
---

# Dto Domain Docs

- Read each model, its producer/consumer, serialization annotations, and JSON integration tests.
- Explain why the model crosses a boundary and when it is constructed or decoded.
- Document fields by constraints: defaults, nullability, units, serialization names, identifier scope, and sensitive values.
- Explain nested or polymorphic models through their public base contract and subtype selection.
- Keep docs factual and concise; do not invent validation or authorization guarantees.
- Link meaningful field changes to central binding registration and DTO integration coverage.
