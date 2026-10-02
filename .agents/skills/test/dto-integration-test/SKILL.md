---
name: dto-integration-test
description: Verify configured Jackson serialization and deserialization for all Kotlin DTOs and entities, including native reachability.
---

# Dto Integration Test

- Model coverage on integration/greeting/GreetingDtoIntegrationTests.kt.
- Use @ActiveProfiles("test"), @SpringBootTest(classes = [Application::class]), and the injected application JsonMapper. Never build a separate mapper for application contracts.
- Add coverage for every DTO/entity implemented as a class or data class, including nested types and enum/polymorphic values.
- Serialize and deserialize explicit realistic values. Access/assert every field and nested value; verify JSON names, defaults, nullable values, empty/nonempty collections, and optional inbound omissions.
- Deserialize polymorphic input through its public base type and assert each supported subtype.
- Keep round-trip actions and field assertions in each test, not hidden in generic helpers.
- Register new binding types centrally. Run JVM tests with tracing and the native suite; agent coverage is limited to exercised paths.
- Use api-integration-test for endpoint behavior and persistence; keep DTO tests focused on conversion.
