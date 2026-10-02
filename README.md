# Kotlin native Lambda template

A small Spring Boot application compiled to a native executable with GraalVM and shipped to AWS Lambda as a container image. It includes HTTP and Jackson integration tests, centralized reflection binding, agent tracing, Linux image packaging, and parameterized CloudFormation deployment.

The application uses Spring Boot 4.1.0, Kotlin 2.3.21, and Java/GraalVM 25, matching the source backend's language/toolchain baseline. The runtime image contains the executable, Linux libraries, and AWS Lambda Web Adapter 1.1.0; it does not need a JVM. Web Adapter owns the Lambda Runtime API loop and forwards events to the Spring HTTP server.

## Try the sample

Install JDK 25 and run:

```bash
scripts/test.sh
scripts/run.sh
```

In another terminal:

```bash
curl http://localhost:8080/health
curl 'http://localhost:8080/hello?name=Ada'
curl -H 'Content-Type: application/json' -d '{"name":"Ada","tags":["native"]}' http://localhost:8080/hello
scripts/smoke.sh
```

`GET /hello` returns `{"message":"Hello, World!","tags":[]}`. `POST /hello` accepts optional `name` and `tags` fields. `/health` returns `{"status":"UP"}` for adapter readiness. There is no database, Redis, email provider, payment configuration, or production secret.

## Verify native execution

Use GraalVM JDK 25 with `native-image` on PATH. Host builds also need a C/C++ toolchain and zlib development headers; on Ubuntu install `build-essential zlib1g-dev`. Make JAVA_HOME and PATH select the same GraalVM installation.

```bash
scripts/trace.sh
scripts/test-native.sh
scripts/build-native.sh
./target/native-lambda
# In a second terminal:
scripts/smoke.sh
```

Tracing runs all `*IntegrationTests` and records observed dynamic calls into the single native metadata directory. Review and commit those generated files after extending the tests. The template's explicit DTO registrations are in `NativeReflectionConfiguration.kt`; tracing supplements them. No application's existing reachability metadata is copied into this template.

`test-native.sh` runs the Spring AOT test executable. `build-native.sh` produces a separate application executable for the host OS and CPU. A passing JVM suite or native test suite does not replace smoke testing the production binary.

## Build the Lambda container

Install Docker with buildx. The default image targets Linux x86_64, even on an Apple Silicon host:

```bash
scripts/build-image.sh
docker run --rm -p 8080:8080 native-lambda:local
# In a second terminal:
scripts/smoke.sh
```

For ARM64 use `PLATFORM=linux/arm64 scripts/build-image.sh`; match Lambda's architecture to that choice. Cross-architecture Docker builds need emulation. Native compilation can require several GB of RAM, so give Docker enough memory. GraalVM is used during compilation; the final image runs the generated executable directly.

The verification workflow traces JVM integration tests, runs native integration tests, builds the Linux image, and smoke tests its HTTP routes. It uploads metadata and test reports without deploying to AWS.

## Deploy to AWS

Install AWS CLI and configure credentials authorized to create CloudFormation, ECR, IAM roles, Lambda functions, and log groups. Run this after verifying the container:

```bash
export AWS_REGION=your-region
export STACK_NAME=my-native-app
scripts/deploy.sh
```

The script creates an ECR repository, builds and pushes one Linux image with a unique immutable tag, and creates or updates a Lambda stack using that image. It prints the function name and URL. Set `PLATFORM=linux/arm64` for ARM64; set `IMAGE_TAG` to choose an unused release tag.

The Function URL uses `AWS_IAM`. Requests need SigV4 signing and caller permissions for both `lambda:InvokeFunctionUrl` and `lambda:InvokeFunction`; a plain curl receives 403. Use an AWS SDK with signed Function URL requests, or attach API Gateway separately. The execution role grants basic CloudWatch logging only. No real AWS deployment is performed by CI.

ECR resources are retained when deleting the ECR stack. To remove a deployed app, delete its Lambda stack and remove retained images/repository separately when no longer needed.

## Customize

Rename `com.example.lambda`, the Maven coordinates, and `spring.application.name`. Update `start-class` in `pom.xml` when renaming `Application.kt`; its top-level main compiles to `ApplicationKt`. Keep the native metadata path and trace script aligned if changing Maven coordinates.

Add DTOs and entities to the central reflection configuration and add controller/Jackson integration coverage that accesses every field. Add service dependencies and resource hints only when needed. Read [AGENTS.md](AGENTS.md) for the native workflow and generic repository skills. You can mark the repository as a GitHub template after merging.

Reference documentation: [Spring native tests](https://docs.spring.io/spring-boot/how-to/native-image/testing-native-applications.html), [GraalVM Maven plugin](https://graalvm.github.io/native-build-tools/latest/maven-plugin.html), and [AWS Lambda Web Adapter](https://github.com/aws/aws-lambda-web-adapter).
