/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: PasswordComponent.java
 Author(s): Lizbeth Garcia-Lopez & Jordan Bassett
 Last Updated: March 16, 2025
 
 Description:
    This is an abstract superclass for password-related validation logic.
    It enforces password validation rules while allowing subclasses to imlement
    specific validation and feedback logic.

 Features:
    - Implements the PasswordChecker interface.
    - Defines abstract methods for evaluating password strength and generating feedback.
    - Provides a shared utility method to check if a password contains common weak words.

 Dependencies:
    - Superclass for: PasswordValidator.java
    - Implements: PasswordChecker.java  */
package passwordfactory;

//Abstract superclass for password validation logic.
public abstract class PasswordComponent implements PasswordChecker
{
    /**
     Abstract method for checking password strength
     Must be implemented by subclasses
          @param password The password to evaluate.
     @return Strength level as an integer:
             0 = Weak
             1 = Moderate
             2 = Strong
     */
    @Override
    public abstract int checkStrength(String password);

    /**
     * Abstract method for providing feedback about a password.
     * Must be implemented by subclasses
     * @param password The password to analyze
     * @return A feedback message with suggestions for improvement.     */
    @Override
    public abstract String giveFeedback(String password);

    /**
     * Helper method to check if the given password contains a common weak word
     * @param password The password to check
     * @param commonWords array of common weak words
     * @return True if the password contains a common word, false otherwise
     */
    protected boolean containsCommonWord(String password, String[] commonWords)
    {
        for (String word : commonWords)
        {
            if (password.toLowerCase().contains(word.toLowerCase()))
            {
                return true; // Password contains a weak common word
            }
        }
        return false;
    }
}
