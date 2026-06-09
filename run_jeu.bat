@echo off
setlocal

pushd "%~dp0"
if not exist class mkdir class

javac -encoding UTF-8 -d class @ApplicationJeu\src\compile.list

if errorlevel 1 (
	popd
	exit /b 1
)
java -cp class src.Controleur
popd
