#!/usr/bin/env bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

cd "$SCRIPT_DIR/ApplicationJeu"
mkdir -p class

javac -encoding UTF-8 -d class \
	src/Controleur.java \
	src/metier/*.java \
	src/metier/enums/*.java \
	src/ihm/*.java

cd "$SCRIPT_DIR"
java -cp ApplicationJeu/class src.Controleur