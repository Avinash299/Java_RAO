#!/usr/bin/env bash

set -x

# Replace placeholders in docker cloud with final values.
sed -i "s/DOCKER_CONFIG/${DOCKER_CONFIG}/g" docker-cloud.yml
sed -i "s/JAVA_OPTIONS/${JAVA_OPTIONS}/g" docker-cloud.yml
sed -i "s/MAX_LOG_FILES/${MAX_LOG_FILES}/g" docker-cloud.yml
sed -i "s/MAX_LOG_SIZE/${MAX_LOG_SIZE}/g" docker-cloud.yml
sed -i "s/REPLICA_COUNT/${REPLICA_COUNT}/g" docker-cloud.yml
