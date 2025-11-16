@echo off
setlocal
cd /d "%~dp0CVBuilder"
call mvnw.cmd -q clean javafx:jlink
echo.
echo Runtime image created at: %CD%\target\app
echo Zip at: %CD%\target\app.zip
echo Run using: target\app\bin\app.bat
endlocal
