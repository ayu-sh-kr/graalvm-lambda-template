---
name: feature-behavior-docs
description: Write plain-English feature documentation based on Kotlin application source, tests, and deployment configuration.
---

# Feature Behavior Docs

- Read the feature entry points, models, services, persistence when present, and integration tests.
- Explain the trigger, inputs, execution path, observable result, rejection paths, and side effects in connected prose.
- Distinguish intended behavior from implemented and tested behavior.
- Include operational configuration and native binding details only where they affect the feature.
- Keep guides and diagrams together under docs/<feature>/. Use svg-api-flow-diagrams when an SVG is requested.
- Verify every linked path and command; avoid invented infrastructure or guarantees.
