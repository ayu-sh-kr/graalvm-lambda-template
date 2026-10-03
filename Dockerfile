FROM ghcr.io/graalvm/native-image-community:25 AS deps

WORKDIR /app

ENV LANG=C.UTF-8 \
    LC_ALL=C.UTF-8 \
    MAVEN_OPTS="-Dmaven.repo.local=/m2"

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B -q dependency:go-offline

FROM deps AS build

COPY src/ src/
RUN ./mvnw -B -Pnative -DskipTests native:compile

FROM public.ecr.aws/lambda/provided:al2023

COPY --from=build /app/target/native-lambda /var/runtime/bootstrap
RUN chmod +x /var/runtime/bootstrap

CMD ["bootstrap"]
