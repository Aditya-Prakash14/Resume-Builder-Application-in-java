# Resume Builder Application

A desktop application for building professional resumes with a graphical user interface. Built with Java Swing, it provides a user-friendly interface for creating, editing, and managing multiple resumes.

## Features

- **User Authentication**: Secure login and registration system
- **Multiple Resume Templates**: Professional, Creative, Modern, and Minimalist styles
- **Full Resume Management**: Create, edit, delete, and organize multiple resumes
- **PDF Export**: Generate professional PDF resumes with customizable formatting
- **SQLite Database**: Persistent data storage for users and resumes
- **Modern UI**: Clean and intuitive interface with FlatLaf theme
- **Session Management**: Proper user session handling with logout functionality
- **Template Content**: Pre-filled resume templates with professional sections

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Aditya-Prakash14/Resume-Builder-Application-in-java.git
   cd Resume-Builder-Application-in-java
   ```

2. Build the project using Maven:
   ```bash
   mvn clean compile
   ```

3. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.resumebuilder.Main"
   ```

   **Alternative:** Build a JAR file and run it:
   ```bash
   mvn clean package
   java -jar target/resume-builder-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

## Usage

1. **Registration/Login**
   - New users can register with a username, password, and email
   - Existing users can log in with their credentials

2. **Creating a Resume**
   - Click "New Resume" to start a new resume
   - Enter a title for your resume
   - Select a template from the dropdown menu
   - Enter your resume content in the text area
   - Click "Save" to store your resume

3. **Managing Resumes**
   - View all your saved resumes in the left panel
   - Select a resume to edit its contents
   - Use the "Delete" button to remove unwanted resumes

4. **Exporting to PDF**
   - Select the resume you want to export
   - Click "Export PDF"
   - Choose a location to save the PDF file
   - The resume will be exported in a professional format

## Technical Details

- Built with Java Swing for the GUI
- Uses SQLite for data persistence
- iText library for PDF generation
- Maven for dependency management and building

## Security Note

This is a basic implementation and does not include advanced security features. In a production environment, you should:
- Implement proper password hashing
- Add input validation
- Implement proper error handling
- Add user session management

## License

This project is open source and available under the MIT License. 