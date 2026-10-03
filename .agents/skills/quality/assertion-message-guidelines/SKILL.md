---
name: assertion-message-guidelines
description: Choose safe and useful validation messages and failure contracts in Kotlin applications.
---

# Assertion Message Guidelines

- Inspect existing exception/guard types and controller error handling first. Do not assume a custom exception framework exists.
- Match the guard to the failure: invalid input, missing resource, illegal state, or unexpected persistence row count.
- Preserve documented HTTP status and error shape unless changing the contract explicitly.
- Keep client messages safe and actionable. Log internal condition/context without secrets, tokens, or private payloads.
- Do not convert programmer or infrastructure errors to success responses.
- Test changed failure branches through controllers and isolated domain tests as appropriate.
