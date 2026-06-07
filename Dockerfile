FROM maven:3.8.8-eclipse-temurin-8 AS builder

WORKDIR /app

COPY pom.xml .
COPY src src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:8u362-b09-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", \
            "-XX:MaxRAMPercentage=60.0", \
            "-XX:+UseContainerSupport",
            "-jar", \
            "app.jar"]
