@echo off
chcp 65001 >nul
if not exist out mkdir out
javac -d out src\*.java || exit /b 1
java -Dstdout.encoding=UTF-8 -cp out Main
