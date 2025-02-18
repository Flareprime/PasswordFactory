/*  Lizbeth Garcia-Lopez & Jordan Bassett    
    Password Factory
    CS236 - Advanced OOP (Java 2)
    Final Project: Strong Password Creation Game for Kids
    Updated circa February 17, 2025
    Desc: This is an abstract superclass for password-related stuff. 
    It enforces password validation rules while allowing subclasses to use 
    specific password validation logic.

    - Implements the PasswordChecker interface
    - Defines an abstract method for evaluating password strength
    - Defines an abstract method for generating password feedbac.
    - Provides a helper method to check if a password contains common weak words

    - Superclass for: PasswordValidator.java
    - Implements: PasswordChecker.java
*/
package passwordfactory;
public abstract class PasswordComponent implements PasswordChecker {

    // Abstract method for checking password strength (must be implemented by subclasses)
    @Override
    public abstract int checkStrength(String password);

    // Abstract method for providing feedback (must be implemented by subclasses)
    @Override
    public abstract String giveFeedback(String password);

    //shared utility method
    protected boolean containsCommonWord(String password, String[] commonWords)
    {
        for (String word : commonWords) {
            if (password.toLowerCase().contains(word.toLowerCase())) {
                return true; // Password contains a weak common word
            }
        }
        return false;
    }
}
