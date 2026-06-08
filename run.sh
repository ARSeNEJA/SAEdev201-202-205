#!/bin/bash

mkdir class

javac -d class scr/controleur/Controleur.java src/vue/*.java src/metier/*.java

sleep 2

java class/controleur/Controleur
