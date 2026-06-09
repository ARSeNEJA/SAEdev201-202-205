@echo off
setlocal

pushd "%~dp0ApplicationJeu"
if not exist class mkdir class

javac -encoding UTF-8 -d class
	src\ControleurJeu.java
	src\metier\*.java
	src\metier\enums\*.java
	src\ihm\*.java

if errorlevel 1 exit /b 1
java -cp class src.ControleurJeu
popd
