FROM ghcr.io/graalvm/native-image-community:25 AS build
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B -q dependency:go-offline
COPY src/ src/
RUN ./mvnw -B -Pnative -DskipTests native:compile
RUN mkdir /out && cp target/native-lambda /out/ && \
    for library in target/*.so; do [ ! -f "$library" ] || cp "$library" /out/; done

FROM public.ecr.aws/amazonlinux/amazonlinux:2023
RUN dnf install -y ca-certificates libstdc++ zlib && dnf clean all
COPY --from=public.ecr.aws/awsguru/aws-lambda-adapter:1.1.0 /lambda-adapter /opt/extensions/lambda-adapter
COPY --from=build /out/ /app/
ENV PORT=8080 AWS_LWA_PORT=8080 AWS_LWA_READINESS_CHECK_PATH=/health AWS_LWA_READINESS_CHECK_HEALTHY_STATUS=200
EXPOSE 8080
ENTRYPOINT ["/app/native-lambda"]
