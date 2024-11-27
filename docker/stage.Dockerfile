FROM amazoncorretto:21 AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM amazoncorretto:21

COPY --from=builder build/libs/standard.jar standard.jar

ARG ENVIRONMENT
ENV SPRING_PROFILES_ACTIVE=${ENVIRONMENT}

ENTRYPOINT ["java","-Xms14G","-Xmx14G","-jar","/standard.jar"]
