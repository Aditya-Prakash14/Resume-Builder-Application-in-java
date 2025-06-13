@echo off
title Resume Builder Application

echo ===================================
echo    Resume Builder Application
echo ===================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed or not in PATH
    echo Please install Java 11 or higher and try again
    echo Download from: https://adoptium.net/
    pause
    exit /b 1
)

echo ✅ Java installation detected
echo 🚀 Starting Resume Builder...
echo.

REM Launch the application
java -jar ResumeBuilder.jar

REM Keep window open
echo.
echo Application closed. Press any key to exit...
pause >nul
