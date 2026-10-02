---
name: code-quality
description: Review Kotlin production and test code for nullability, errors, immutability, logging, and native compatibility.
---

# Code Quality

- Inspect the implemented failure contract before changing validation. Use existing guard helpers when present; do not assume template-specific exception utilities exist.
- Validate boundary input before side effects. Expose safe client errors and keep internal failure details in contextual logs.
- Never log credentials, tokens, or private payloads. Use SLF4J placeholders and attach exceptions only to actual failures.
- Avoid !! and unsafe Optional.get(). Model missing values explicitly and fail with the intended HTTP/domain contract.
- Prefer immutable data classes, val fields, and copy for updates. Do not catch failures merely to report success.
- Keep simple logic inline; extract reused or independently complex behavior.
- Use the configured Jackson JsonMapper in tests. Keep serialization actions and every field assertion visible.
- Register binding types centrally and verify both JVM and native behavior as described in AGENTS.md.
