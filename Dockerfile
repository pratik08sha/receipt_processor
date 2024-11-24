FROM openjdk:21-jdk-slim
FROM maven:3
LABEL authors="pratiksha sharma"
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package
COPY target/receiptProcessor-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]