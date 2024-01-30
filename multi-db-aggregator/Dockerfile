FROM openjdk:17-jdk-slim

LABEL authors="Dmytro Romanov"

COPY target/multi-db-aggregator-0.0.1-SNAPSHOT.jar /app/multi-db-aggregator-0.0.1-SNAPSHOT.jar

WORKDIR /app

CMD ["java", "-jar", "multi-db-aggregator-0.0.1-SNAPSHOT.jar"]

