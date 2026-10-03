# Kotlin native Lambda template

A generic version of the portfolio backend's Kotlin/Spring Boot setup: Java 25, Kotlin 2.4.20, Spring Boot 4.1.1, and AWS Serverless Java Container 3.0.2.

The original Docker configuration is preserved with a generic binary name and updated build image. GraalVM compiles the app; `provided:al2023` packages it at `/var/runtime/bootstrap`. `LambdaHandler` uses the source project's HTTP API v2 servlet handler. Deployment stays with your existing Lambda setup.

## Local application

Use JDK 25 and the Maven wrapper:

```bash
scripts/test.sh
scripts/run.sh
```

The sample exposes `GET /hello?name=Ada`, `POST /hello` with `{"name":"Ada","tags":["native"]}`, and `GET /health`. The greeting defaults to World. No database, Redis, email, payment, or app-specific secrets are included.

## Native build and tests

Select GraalVM Community 25.4.4.1.1 (JDK 25.0.4.1.1) with `native-image` on PATH and a host C/C++ toolchain.

```bash
scripts/trace.sh
scripts/test-native.sh
scripts/build-native.sh --local
scripts/run-native.sh --local
```

`trace.sh` runs integration tests with the original caller-filter and metadata-merge convention. Review generated metadata before committing. `test-integration.sh` runs tracing and native integration tests together.

`build-native.sh` accepts `--local` for the original `native-local` AOT profile; without it, it uses `native`. Maven scripts accept additional Maven arguments, including `-Dtest=SomeIntegrationTests`.

Register application binding types in `config/NativeReflectionConfiguration.kt`. Add controller and Jackson integration tests for each model, accessing every field. A passing JVM suite does not prove native compatibility.

## Lambda container

The image build keeps the source project's ARM64 platform and disables provenance and SBOM output:

```bash
scripts/build-image.sh
# Optional image name and tag:
scripts/build-image.sh my-app:v1

scripts/run-native.sh
# Optional image and host port:
scripts/run-native.sh my-app:v1 9000
scripts/invoke-lambda.sh 9000
```

`run-native.sh` retains the source container's production profile and function-router environment setting. `invoke-lambda.sh` posts a generic greeting event to the local Lambda invocation endpoint using the source status script's convention.

The Dockerfile retains the source layout and updates the executable name and GraalVM image. Image builds pull the current AWS `provided:al2023` runtime. It adds no Web Adapter, port/readiness overrides, custom entry point, or deployment infrastructure. Publish the image and configure Lambda using your existing deployment process. Container/AWS execution must be verified separately; the CI workflow builds the JVM package and runs integration tests.

## Customize

Rename `com.example.lambda`, Maven coordinates, and `spring.application.name`. Update the explicit Spring Boot main class in `pom.xml`, the Docker binary path, and the tracing metadata path together.

Read [AGENTS.md](AGENTS.md) and the relevant repository skills. Mark the repository as a GitHub template after merging.

## Dependency baseline

Stable releases checked on October 3, 2026: Maven 3.10.0 (wrapper 3.3.4), Kotlin 2.4.20, Spring Boot 4.1.1, Jackson 3.2.3 (shared annotations 2.22 via Jackson 2 BOM 2.22.3), Native Build Tools 1.1.14, and AWS Serverless Java Container 3.0.2. Other framework dependencies follow Spring Boot dependency management.

Java 25 remains the compilation target because the latest GraalVM Community native toolchain is based on JDK 25.0.4.1.1; Java 27 is the newest standalone Java release. The GraalVM build image uses `25i4-25.0.4.1.1-ol9`; the Lambda runtime remains AWS `provided:al2023`.
