# Password Factory

Jordan Bassett  
Lizbeth Garcia-Lopez  
CS236 - Advanced OOP (Java 2)  
Final Project: Strong Password Creation Game for Kids  
Last Updated: March 16, 2025

---

## 📚 Overview
**Password Factory** is an educational game developed in JavaFX to teach kids (and anyone!) how to create strong passwords.  
Players work in a virtual "password factory," building secure passwords under time pressure while learning about password strength through real-time feedback.

---

## ✅ Project Requirements & Features
This project was developed for **CS236 - Advanced OOP (Java 2)** and meets the following requirements:

- **Object-Oriented Design**
  - Inheritance (`PasswordComponent` → `PasswordValidator`)
  - Interfaces (`PasswordChecker`)
  - Polymorphism in password validation logic

- **Password Strength Algorithm**
  - Based on length, character variety, and detection of common words
  - Adjustable difficulty settings (Easy, Medium, Hard)
  - Dynamic hint generation for improving password strength

- **File Handling**
  - Loads and saves `common_words.txt` and `phrases.txt`
  - Logs passwords and their strength in `password_log.txt`

- **Graphical User Interface (JavaFX)**
  - **Password Factory Game**: Timed challenge to generate strong passwords
  - **Password Lab**: Sandbox environment for testing passwords and editing phrases/words
  - **About Screen**: Displays system info (Java & JavaFX versions)

- **Testing & Console Apps**
  - `PasswordValidatorTest.java` for unit testing password evaluation logic
  - `PasswordFactoryConsole.java` for early console-based password generation and file handling testing

---

## ⚙️ Specifications & Tools
- **Java Version**: 17+ (Developed on OpenJDK 23.0.2)
- **JavaFX Version**: 20+
- **IDE**: Apache NetBeans IDE 24
- **Build Tool**: Maven (Simple JavaFX Maven Archetype)

---

## 🚀 How to Run Password Factory

### Prerequisites
- Java 17 or later
- JavaFX SDK 20 or later
- Maven (optional if running inside NetBeans)

### Running in NetBeans (Recommended)
1. Open the project in NetBeans (`pom.xml` is at root).
2. Clean and Build the project.
3. Run `App.java` (Main Menu).
4. Optionally, run `PasswordValidatorTest.java` for validation tests.

