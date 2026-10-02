---
name: api-integration-test
description: Write Spring Boot HTTP integration tests that exercise routing, binding, filters, services, and observable side effects.
---

# Api Integration Test

- Model tests on integration/greeting/GreetingControllerIntegrationTests.kt.
- Use @ActiveProfiles("test") and @SpringBootTest(classes = [Application::class]). Use RestTestClient.bindToApplicationContext with the real WebApplicationContext; include route filters explicitly when relevant.
- Send requests through HTTP routing, rather than calling controller methods directly. Assert status and typed response fields; assert durable side effects when present.
- Keep one observable behavior per test, with explicit setup, exchange, decoding, and assertions.
- Cover rejected/malformed input, successful requests, omitted optional fields, and each new route. Access every response field to exercise reachability.
- Keep tests independent. Use shared IntegrationTestConfiguration and Testcontainers only after the application introduces those resources. Stub external boundaries, never production services.
- Keep Spring test contexts compatible with native AOT; avoid mock bean replacements in native integration suites.
- Name suites *IntegrationTests under integration/<feature>. Run scripts/test.sh, scripts/trace.sh, and scripts/test-native.sh. Smoke test the production container separately.
