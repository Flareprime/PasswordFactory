/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: PasswordValidatorTest.java
 Author(s): Lizbeth Garcia-Lopez & Jordan Bassett
 Last Updated: March 16, 2025
 
 Description:
    Early test class for validating the PasswordValidator logic.
    Runs a set of predefined passwords through the validator and prints
    their strength ratings and feedback to the console.

 Features:
    - Tests PasswordValidator against a variety of sample passwords
    - Displays strength ratings and improvement feedback
    - Demonstrates different difficulty settings

 Dependencies:
    - Uses: PasswordValidator.java, PasswordFileManager.java
 */

package passwordfactory;

//Early console-based test class for validating PasswordValidator.
//outputs strength evaluations and feedback for predefined passwords.
 public class PasswordValidatorTest
{
    public static void main(String[] args)
    {
        //Create file manager for test environment
        PasswordFileManager fileManager = new PasswordFileManager("phrases.txt", "password_log.txt");

        //Create validator using file manager and set difficulty (easy/medium/hard)
        String difficulty = "medium"; // Change this to test other difficulties
        PasswordValidator validator = new PasswordValidator(fileManager, difficulty);

        // Array of test passwords to evaluate
        String[] testPasswords = {
            "123456",            // Weak
            "Password123!",      // Moderate (common word)
            "HelloWorld!",       // Moderate
            "Str0ngP@ss!",       // Strong
            "SupermanLives!1",   // Moderate (common word!)
            "qwerty1234"         // Weak (common word)
        };

        System.out.println("Testing Passwords in '" + difficulty + "' Mode");
        System.out.println("==========================================");

        //loop through each password and print evaluation results
        for (String pw : testPasswords)
        {
            System.out.println("Testing: " + pw);
            int score = validator.checkStrength(pw);
            String feedback = validator.giveFeedback(pw);
            String strengthLabel;
            switch (score)
            {
                case 2:
                    strengthLabel = "Strong";
                    break;
                case 1:
                    strengthLabel = "Moderate";
                    break;
                default:
                    strengthLabel = "Weak";
                    break;
            }

            System.out.println("Strength: " + strengthLabel + " (Score: " + score + ")");
            System.out.println("Feedback:\n" + feedback);
            System.out.println("------------------------------------------");
        }
    }
}
