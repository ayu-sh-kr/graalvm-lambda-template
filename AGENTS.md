# Repository guidelines

## Purpose and build

This template runs a Kotlin Spring Boot HTTP application as a GraalVM native executable in an AWS Lambda container. Lambda Web Adapter handles the runtime event loop and forwards requests to Spring MVC. Native execution is the primary acceptance criterion; JVM success alone is insufficient.

- Use Java/GraalVM 25 and the Maven wrapper. Keep the Kotlin top-level main class (`com.example.lambda.ApplicationKt`) explicit in `pom.xml`, including the native plugin. Update it when renaming packages or files.
- Keep the binary name, Docker entry point, HTTP port, adapter readiness route, and Lambda architecture aligned. Build a single Linux architecture; macOS native binaries cannot run on Lambda.
- Organize code by feature, keep controllers thin, and use constructor injection. Add infrastructure dependencies only when a feature needs them.

## Native reflection and metadata

1. Use immutable Kotlin `data class` DTOs. Register every DTO, entity, nested model, enum, and polymorphic subtype that requires binding in `config/NativeReflectionConfiguration.kt`, using `@RegisterReflectionForBinding`.
2. Keep application binding registrations in that one configuration. Do not scatter annotations across models or add custom reflection registrars, `@Import`, or `@ImportRuntimeHints` for binding. Framework-generated AOT hints remain valid; a resource-only registrar is permitted when a resource cannot be inferred.
3. Keep supplemental tracing metadata together under `src/main/resources/META-INF/native-image/com.example/native-lambda/`. Binding annotations provide metadata, but they do not guarantee every Kotlin reflection path works. Exercise the actual paths in tests.
   Register internal runtime implementations by `classNames` on the same binding annotation when needed; the sample includes Kotlin's `EmptyList`. Preserve any native-image companion `.so` files beside the executable in the runtime image.
4. Run `scripts/trace.sh` to record controller and Jackson integration calls on the JVM with GraalVM's agent. It refreshes metadata only after successful tests. Review generated files for test-only classes, stale types, and environment-specific paths before committing. Agent output covers only executed paths.
5. Run `scripts/test-native.sh`, then build the Linux container and run `scripts/smoke.sh` against it. A native test executable and the production executable are different artifacts; verify both.

## Tests

- Follow `.agents/skills/test/api-integration-test/SKILL.md`. For each controller change, add `*IntegrationTests` under `src/test/kotlin/com/example/lambda/integration/<feature>/`. Use `@SpringBootTest`, the test profile, and `RestTestClient` to exercise HTTP routing, request binding, rejection paths, and typed responses. Include real filters when their behavior matters.
- Follow `.agents/skills/test/dto-integration-test/SKILL.md`. For every DTO or entity implemented as a class or data class, use the application's Jackson `JsonMapper` to test serialization and deserialization and explicitly access/assert every field, including nested values. Cover defaults, nullable fields, collections, renamed fields, and each polymorphic subtype when present.
- Keep tests independent and deterministic. Introduce shared test infrastructure only when needed; use Testcontainers for persistence and fake provider boundaries when those features are added. Never call production services from tests.

## Skills and change delivery

Read relevant skills under `.agents/skills/` before changing code: `code-quality`, `code-structure`, `clean-code`, and `code-documentation`; use API, DTO, and documentation skills for their respective work. Persistence skills apply only when the application adds that integration; the sample has no database, Redis, payment, or email dependency.

Use `github/atomic-domain-commits` for coherent commits. Prefix branch names, commits, and PR titles with an action such as `feat`, `fix`, `add`, `patch`, `chores`, or `bump`. Separate app/build changes from agent documentation changes. Describe validation actually performed and any blocked native/container/AWS checks; never claim a deployment was verified from JVM tests alone.

## Commands

- `scripts/run.sh`: local JVM server.
- `scripts/test.sh`: JVM tests.
- `scripts/trace.sh`: JVM integration tests with reachability tracing.
- `scripts/test-native.sh`: Spring AOT integration tests in a native executable.
- `scripts/build-native.sh`: host-native application build.
- `scripts/build-image.sh`: Linux Lambda container build.
- `scripts/smoke.sh [base-url]`: endpoint checks against a running application.
- `scripts/deploy.sh`: explicitly requested AWS deployment; creates ECR and Lambda stacks and pushes a unique image tag.
