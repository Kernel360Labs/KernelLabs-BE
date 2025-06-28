FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle gradle
RUN gradle build || return 0  # Gradle 캐시 적용

COPY . .
RUN gradle clean build -x test
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

COPY --from=builder /app/src/main/resources/application-prod.yml /app/config/application-prod.yml

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Dspring.config.location=classpath:/,file:/app/config/", "-jar", "app.jar"]

EXPOSE 8080