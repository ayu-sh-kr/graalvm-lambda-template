---
name: mock-aaa-tests
description: Write isolated Kotlin unit tests with explicit Arrange, Act, Assert stages and business-branch coverage.
---

# Mock Aaa Tests

- Construct the subject directly and mock only its collaborators; keep native full-context integration tests free from mock bean replacements.
- Use descriptive backtick test names, separated Arrange/Act/Assert stages, and one behavior per test.
- Verify dependency arguments, call counts, and early-exit behavior where contract-relevant.
- Assert expected result, state, and exception contract. Avoid asserting incidental implementation details.
- Keep feature fixtures local and deterministic. Do not borrow production external endpoints.
- Add controller and DTO integration tests separately for reflection reachability; mock tests do not prove native binding.
