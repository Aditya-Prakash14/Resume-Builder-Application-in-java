# Resume Builder - Installation & Deployment Guide

## Quick Start

### Prerequisites
- Java 11 or higher installed on your system
- Download from: [Eclipse Temurin](https://adoptium.net/) (recommended)

### For Windows Users
1. Double-click `start.bat` to run the application
2. If you get a security warning, click "More info" then "Run anyway"

### For macOS/Linux Users
1. Double-click `start.sh` or run it from terminal: `./start.sh`
2. If permission denied, run: `chmod +x start.sh` then try again

### Alternative Method (All Platforms)
Open terminal/command prompt and run:
```bash
java -jar ResumeBuilder.jar
```

## Features
- ✅ User registration and login system
- ✅ Create and manage multiple resumes
- ✅ Professional resume templates
- ✅ Export to PDF format
- ✅ Data persistence with SQLite database
- ✅ Modern, user-friendly interface

## System Requirements
- **Operating System**: Windows 10+, macOS 10.14+, or Linux
- **Java**: Version 11 or higher
- **Memory**: Minimum 512MB RAM
- **Storage**: 50MB free space
- **Display**: 1024x768 resolution or higher

## Deployment Options

### 1. Standalone Application (Current)
- Single JAR file with all dependencies
- Easy distribution and installation
- No additional setup required

### 2. Web Application (Future Enhancement)
- Deploy to cloud platforms (AWS, Heroku, etc.)
- Accessible via web browser
- Requires application server setup

### 3. Desktop Installer
- Create platform-specific installers
- Professional installation experience
- Automatic Java dependency management

## Troubleshooting

### Java Not Found
If you see "Java is not installed" error:
1. Download and install Java from [Eclipse Temurin](https://adoptium.net/)
2. Restart your computer
3. Try running the application again

### Database Issues
- The application creates `resume_builder.db` automatically
- Delete this file to reset all data (users and resumes)
- Backup this file to preserve your data

### Performance Issues
- Ensure you have at least 512MB free RAM
- Close other applications if the system is slow
- Restart the application if it becomes unresponsive

### Export Problems
- Ensure you have write permissions to the selected folder
- Try exporting to Desktop or Documents folder
- Check available disk space

## Security Notes
- This version stores passwords in plain text (development version)
- Database file contains user data - keep it secure
- For production use, implement proper password hashing

## Support
- Source code: [GitHub Repository](https://github.com/Aditya-Prakash14/Resume-Builder-Application-in-java)
- Report issues on GitHub
- Check README.md for development setup

## Version Information
- Version: 1.0
- Build Date: June 2025
- Java Target: 11+
- Dependencies: SQLite, iText PDF, FlatLaf UI
