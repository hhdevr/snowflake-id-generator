#-------------------------------------------------------------#
FROM eclipse-temurin:21.0.7_6-jdk-jammy AS builder

WORKDIR /app

LABEL authors="dmitrychaykin"
EXPOSE 8080

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle

RUN ./gradlew dependencies || true

COPY src src

RUN ./gradlew clean build -x test -x ktlintKotlinScriptCheck -x ktlintTestSourceSetCheck -x ktlintMainSourceSetCheck

#-------------------------------------------------------------#
FROM eclipse-temurin:21.0.7_6-jre-jammy
WORKDIR /app

RUN useradd --system --create-home --uid 1001 appuser

USER appuser

COPY --chown=appuser:appuser --from=builder /app/build/libs/*-SNAPSHOT.jar app.jar

HEALTHCHECK --interval=30s --timeout=10s --start-period=15s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/management/health

ENTRYPOINT ["java", "-jar", "app.jar"]
