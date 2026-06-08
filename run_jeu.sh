#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

cd "$SCRIPT_DIR"

mkdir -p class

javac -encoding UTF-8 -d class @ApplicationJeu/src/compile.list

java -cp class src.ControleurJeu
