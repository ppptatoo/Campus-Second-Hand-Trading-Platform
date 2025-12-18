#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

rm -rf webapps/ROOT
mkdir -p uploads

echo "Cleaned exploded webapp directory and ensured uploads/ exists."