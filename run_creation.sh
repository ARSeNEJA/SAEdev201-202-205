#!/usr/bin/env bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

cd "$SCRIPT_DIR/ApplicationCreationPlateau"
mkdir -p class

javac -encoding UTF-8 -d class \
	src/Controleur.java \
	src/metier/*.java \
	src/metier/enums/*.java \
	src/ihm/*.java

java -cp class Controleur
