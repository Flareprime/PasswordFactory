/*
    Jordan Bassett
    Lizbeth Garcia-Lopez
    Password Factory
    CS236 - Advanced OOP (Java 2)
    Final Project: Strong Password Creation Game for Kids
    Updated circa February 16, 2025

    Description:
    This class extends PasswordComponent and implements the PasswordChecker interface.
    It validates passwords based on length, character diversity, and common password patterns.
    A scoring system is used to categorize passwords as Weak, Moderate, or Strong.
*/

package passwordfactory;
import java.util.Arrays;
import java.util.List;

//Validates passwords based on length, character diversity, and common password checks
public class PasswordValidator extends PasswordComponent {

    // List of common weak words that should be avoided in passwords
    private static final List<String> COMMON_WORDS = Arrays.asList(
        "password", "1234", "qwerty", "admin", "secure", "supersecure", "letmein"
    );

    /*Evaluates password strength using a scoring system.
     @param password The password to check.
     @return Strength level as an integer:
       0 = Weak
       1 = Moderate
       2 = Strong
     */
    @Override
    public int checkStrength(String password) {
        int score = 0; //Initialize the score

        // Length-based scoring
        if (password.length() >= 12) {
            score += 2; //Strong length bonus
        } else if (password.length() >= 8) {
            score += 1; //Moderate length bonus
        }

        //Check for character variety
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }

        //Add points for character diversity
        if (hasUpper) score += 1;
        if (hasLower) score += 1;
        if (hasDigit) score += 1;
        if (hasSpecial) score += 1;

        //Check for common weak words (apply penalty)
        if (containsCommonWord(password, COMMON_WORDS.toArray(new String[0]))) {
            score -= 2; //Penalize weak words
        }

        // Return the strength category based on the total score
        return (score >= 5) ? 2 : (score >= 3) ? 1 : 0;
    }

    /*Provides feedback based on the password's strength score.
     @param password The password to analyze.
     @return A feedback message with password security improvements.
    */
    @Override
    public String giveFeedback(String password) {
        int strength = checkStrength(password);
        switch (strength) {
            case 2: return "Strong password! Great job!";
            case 1: return "Moderate password: Try adding special characters for extra security.";
            default: return "Weak password: Use at least 8 characters, including numbers, uppercase letters, and symbols.";
        }
    }
}
