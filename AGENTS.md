# Repository guidelines

## Configuration and build

Keep the source backend's deployment conventions: Java/GraalVM 25, Maven, AWS Serverless Java Container, `LambdaHandler`, and the `provided:al2023` image with `/var/runtime/bootstrap`. Do not replace them with Web Adapter, custom runtime wiring, environment overrides, or deployment infrastructure unless requested.

- Keep the Kotlin top-level main class (`com.example.lambda.ApplicationKt`) explicit in the Spring Boot Maven plugin. Update it when renaming the file or package.
- Keep the Maven artifact name and Docker binary path aligned. The image build uses the source project's ARM64 platform.
- Organize code by feature, keep controllers thin, and use constructor injection. Add infrastructure dependencies only when needed.

## Native reflection and metadata

The biggest hurdle is making the native executable work. JVM success alone is insufficient.

1. Use immutable Kotlin `data class` DTOs. Register DTOs, entities, nested models, enums, and polymorphic subtypes that require binding in `config/NativeReflectionConfiguration.kt` using `@RegisterReflectionForBinding`.
2. Keep binding registrations in that one configuration. Avoid custom reflection registrars, `@Import`, and `@ImportRuntimeHints` for binding. Resource-only registrars are permitted when needed.
3. Keep generated metadata under `src/main/resources/META-INF/native-image/com.example/native-lambda/`. Register internal runtime implementations by `classNames` on the same annotation when needed; the sample includes Kotlin's `EmptyList`.
4. Use `scripts/trace.sh` to record integration calls with the source caller-filter and merge convention. Review metadata for stale types and environment-specific paths. Tracing covers only executed paths.
5. Run native integration tests and verify the production artifact with the intended deployment setup. Report exactly which checks ran.

## Integration tests

- Follow `.agents/skills/test/api-integration-test/SKILL.md`. Add `*IntegrationTests` under `integration/<feature>` for controller changes. Exercise routing, request binding, rejection paths, and typed responses through the controller.
- Follow `.agents/skills/test/dto-integration-test/SKILL.md`. Test Jackson serialization and deserialization for every entity/DTO class or data class using the application's `JsonMapper`. Access and assert every field, including nested values, defaults, nullable fields, collections, and subtypes.
- Keep tests deterministic and independent. Add persistence test infrastructure only when persistence is introduced.

## Skills and commits

Read the relevant `code-quality`, `code-structure`, `clean-code`, and `code-documentation` skills before editing. Use API, DTO, and documentation skills for their respective work. Persistence skills apply only when that integration exists.

Use `github/atomic-domain-commits` for coherent commits. Prefix branches, commits, and PR titles with an action such as `feat`, `fix`, `add`, `patch`, `chores`, or `bump`. Separate configuration/script changes from unrelated documentation.

## Commands

| Script | Purpose |
|---|---|
| `run.sh` | JVM server |
| `test.sh` | JVM tests |
| `trace.sh` | JVM integration tests and reachability metadata |
| `test-native.sh` | Native tests |
| `test-integration.sh` | Tracing followed by native integration tests |
| `build-native.sh [--local]` | Native executable, optionally with local AOT profile |
| `build-image.sh [image:tag]` | ARM64 Lambda container |
| `run-native.sh [--local\|image:tag] [port]` | Local binary or source-style container run |
| `invoke-lambda.sh [port]` | Local Lambda greeting invocation |

All scripts live under `scripts/`. Maven scripts accept additional Maven arguments.
