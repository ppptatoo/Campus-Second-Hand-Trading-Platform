#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

SQL=./sql/fix_dates_2025_11.sql
if [ ! -f "$SQL" ]; then
  echo "Not found: $SQL" >&2
  exit 1
fi

if command -v docker-compose >/dev/null 2>&1; then
  docker-compose exec -T mysql mysql -uroot -pstartech market < "$SQL"
else
  docker compose exec -T mysql mysql -uroot -pstartech market < "$SQL"
fi

echo "Dates normalized to >= 2025-11 successfully."