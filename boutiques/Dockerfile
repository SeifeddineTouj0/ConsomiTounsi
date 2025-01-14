FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN apk add --no-cache wget curl bash

COPY target/boutiques-0.0.1-SNAPSHOT.jar app.jar

COPY docker-entrypoint.sh .
RUN chmod +x docker-entrypoint.sh

EXPOSE 8082

ENTRYPOINT ["sh", "/app/docker-entrypoint.sh"]