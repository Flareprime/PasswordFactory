package passwordfactory;

/**
 * Abstract (super)class that provides a base for all password-related components.
 * Defines shared structure and enforces password validation rules.
  PasswordComponent the superclass while still enforcing the contract from the PasswordChecker interface.
  * hared helper method (containsCommonWord) that other classes can use.
 */
public abstract class PasswordComponent implements PasswordChecker {

    // Abstract method for checking password strength (must be implemented by subclasses)
    @Override
    public abstract int checkStrength(String password);

    // Abstract method for providing feedback (must be implemented by subclasses)
    @Override
    public abstract String giveFeedback(String password);

    // Optional: A shared utility method (not abstract)
    protected boolean containsCommonWord(String password, String[] commonWords) {
        for (String word : commonWords) {
            if (password.toLowerCase().contains(word.toLowerCase())) {
                return true; // Password contains a weak common word
            }
        }
        return false;
    }
}
