#!/bin/bash

echo "Waiting for config server to be ready..."

# Function to check if config server is ready
check_config_server() {
    wget --spider -q http://config-server:8888/actuator/health 2>/dev/null
    return $?
}

# Function to check if the service config is available
check_service_config() {
    wget --spider -q http://config-server:8888/ventes-microservice/default 2>/dev/null
    return $?
}

# Wait for config server to be healthy
until check_config_server; do
    echo "Config server is not ready... waiting 5s..."
    sleep 5
done

echo "Config server is up. Checking for service configuration..."

# Wait for service configuration to be available
until check_service_config; do
    echo "Service configuration is not available... waiting 5s..."
    sleep 5
done

echo "Configuration is available. Starting the application..."
exec java -jar app.jar