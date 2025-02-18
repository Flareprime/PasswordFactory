Jordan Bassett & Lizbeth Garcia-Lopez
Password Factory
CS236 - Advanced OOP (Java 2)
Final Project: Strong Password Creation Game for Kids
Updated circa February 17, 2025

# Password Factory To-Do List
- Fix UML (good atm)
X add java/javaFX specs to readme and App.java. Maybe an About screen
    done but looks generic
- try/catch all over the place
- Update Password Factory Classes, Variables, and Methods document with variables/methods we actually used
- and UML
- remove System.out.println() debugging stuff, or do a Global debug mode flag 

## Password Validation
- Make password validation smarter (avoid common substitutions like "P@ssword1")
- Think the Javascript book has an example array of common words
- Implement real-time password strength feedback for the UI
- List of common weak words that should be avoided in passwords in PasswordValidator can be expanded. Maybe from a text file?

## Game

## Storage

## UI Enhancements

## Code Refactoring & Testing

## Password Strength Scoring
We’ll calculate a score based on:

### Length
- +1 point for 8-11 characters
- +2 points for 12+ characters

### Character Variety
- +1 point for Uppercase letters
- +1 point for Lowercase letters
- +1 point for Numbers
- +1 point for Symbols

### Common Word Check (Penalty)
- -2 points if a common word is detected (e.g., "password", "qwerty")

### Final Strength Categories
- 0-2 points → Weak
- 3-4 points → Moderate
- 5+ points → Strong
