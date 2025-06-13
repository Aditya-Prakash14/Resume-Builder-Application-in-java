#!/bin/bash

# Resume Builder Application Launcher
# This script launches the Resume Builder desktop application

echo "==================================="
echo "   Resume Builder Application"
echo "==================================="
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed or not in PATH"
    echo "Please install Java 11 or higher and try again"
    echo "Download from: https://adoptium.net/"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 11 ]; then
    echo "❌ Java 11 or higher is required"
    echo "Current Java version: $JAVA_VERSION"
    echo "Please upgrade Java and try again"
    exit 1
fi

echo "✅ Java version check passed"
echo "🚀 Starting Resume Builder..."
echo ""

# Launch the application
java -jar ResumeBuilder.jar

# Keep terminal open on macOS/Linux
echo ""
echo "Application closed. Press any key to exit..."
read -n 1
