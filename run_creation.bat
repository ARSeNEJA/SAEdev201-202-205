@echo off
setlocal

pushd "%~dp0ApplicationCreationPlateau"
if not exist class mkdir class
javac -encoding UTF-8 -d class src\ControleurCreation.java src\metier\*.java src\metier\enums\*.java src\ihm\*.java
if errorlevel 1 exit /b 1
java -cp class controleur.ControleurCreation
popd
