@echo off
setlocal
cd /d "%~dp0CVBuilder"
call mvnw.cmd javafx:run
endlocal
