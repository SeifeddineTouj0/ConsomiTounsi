FROM openjdk:17-jdk-alpine

RUN apk add --no-cache curl

COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app.jar"]