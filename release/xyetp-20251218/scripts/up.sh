#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

# Start MySQL and Tomcat with mounted WAR and uploads directory
if command -v docker-compose >/dev/null 2>&1; then
  docker-compose up -d
else
  docker compose up -d
fi

echo "XYETP started."
echo "Student: http://localhost:8080/goods/index"
echo "Admin:   http://localhost:8080/admin/toLogin"