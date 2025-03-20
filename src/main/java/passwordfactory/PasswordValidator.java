/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: PasswordValidator.java
 Author(s): Jordan Bassett & Lizbeth Garcia-Lopez
 Last Updated: March 16, 2025
 
 Description:
    Validates password strength and provides feedback
    Implements difficulty scaling, detects common words, and offers hints for improvement
	extends PasswordComponent (inheritance) and uses HintManager and PasswordFileManager

 Features:
    - Evaluates password strength based on length, character variety, and common word usage
    - Provides dynamic hints for password improvement
    - Scales requirements by difficulty level
 
 Dependencies:
    - Extends: PasswordComponent.java
    - Uses: PasswordFileManager.java, HintManager.java
 */

package passwordfactory;
import java.util.List;


// Validates password strength and provides feedback
public class PasswordValidator extends PasswordComponent
{
    private HintManager hintManager;
    private List<String> commonWords;

    private String difficulty;
    private final int commonWordPenalty;
    private final int moderateThreshold;
    private final int strongThreshold;

    /**
     * Constructor for PasswordValidator.
     * @param fileManager The file manager to load common words.
     * @param difficulty The selected difficulty level (easy, medium, hard).
     */
    public PasswordValidator(PasswordFileManager fileManager, String difficulty)
    {
        this.hintManager = new HintManager();
        this.commonWords = fileManager.loadCommonWords("common_words.txt");
        this.difficulty = difficulty.toLowerCase();
        switch (this.difficulty)
        {
            case "easy":
                commonWordPenalty = 2;
                moderateThreshold = 3;
                strongThreshold = 5;
                break;
            case "medium":
                commonWordPenalty = 3;
                moderateThreshold = 4;
                strongThreshold = 5;
                break;
            case "hard":
                commonWordPenalty = 4;
                moderateThreshold = 4;
                strongThreshold = 5;
                break;
            default:
                commonWordPenalty = 2;
                moderateThreshold = 3;
                strongThreshold = 5;
        }
    }

    /**
     * Evaluates the strength of the password.
     * @param password The password to evaluate.
     * @return 0 = Weak, 1 = Moderate, 2 = Strong
     */
    @Override
    public int checkStrength(String password)
    {
        int score = 0;
        hintManager.clearDynamicHints();
        if (password.length() >= 8) {
            score++;
        }
        else {
            hintManager.addDynamicHint(new Hint("Try making it longer (8+ characters).", "Length"));
        }

        if (password.matches(".*[A-Z].*")) {
            score++;
        }
        else {
            hintManager.addDynamicHint(new Hint("Add at least one uppercase letter.", "Uppercase"));
        }

        if (password.matches(".*[a-z].*")) {
            score++;
        }
        else {
            hintManager.addDynamicHint(new Hint("Add at least one lowercase letter.", "Lowercase"));
        }

        if (password.matches(".*\\d.*")) {
            score++;
        }
        else { 
            hintManager.addDynamicHint(new Hint("Add at least one number.", "Numbers"));
        }

        if (password.matches(".*[!@#$%&*].*")) {
            score++;
        }
        else {
            hintManager.addDynamicHint(new Hint("Add at least one special character (!@#$%&*).", "Special Characters"));
        }

        String matchedCommonWord = getMatchedCommonWord(password.toLowerCase(), commonWords);

        if (matchedCommonWord != null)
        {
            hintManager.addDynamicHint(new Hint(matchedCommonWord + " is a common word! Try something more unique.", "Common Words"));
            score -= commonWordPenalty;
            if (difficulty.equals("hard")) {
                return 0;
            }
        }

        if (score >= strongThreshold) {
            return 2;
        }
        else if (score >= moderateThreshold) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * Provides feedback for improving a password.
     * @param password The password to analyze.
     * @return Feedback message.
     */
    @Override
    public String giveFeedback(String password)
    {
        int strength = checkStrength(password);

        if (strength == 2) {
            return "Strong Password! Great job!";
        }

        if (hintManager.getDynamicHints().isEmpty()) {
            return "Good! But there's room for improvement.";
        }

        StringBuilder feedback = new StringBuilder("Suggestions:\n");
        for (Hint hint : hintManager.getDynamicHints()) {
            feedback.append("- ").append(hint.getHint()).append("\n");
        }

        return feedback.toString();
    }

    /**
     * Checks if the password contains any common word.
     * @param password The password to check.
     * @param commonWords List of common words.
     * @return The matched common word, or null if none found.
     */
    private String getMatchedCommonWord(String password, List<String> commonWords)
    {
        for (String word : commonWords)
        {
            if (password.contains(word)) {
                return word;
            }
        }
        return null;
    }

    /**
     * Returns the HintManager instance.
     * @return The HintManager.
     */
    public HintManager getHintManager() {
        return hintManager;
    }
}
