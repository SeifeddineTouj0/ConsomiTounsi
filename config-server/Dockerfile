FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN apk add --no-cache curl wget

COPY target/config-server-0.0.1-SNAPSHOT.jar config_server.jar

HEALTHCHECK --interval=10s --timeout=5s --start-period=30s --retries=5 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8888/actuator/health || exit 1

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "config_server.jar"]