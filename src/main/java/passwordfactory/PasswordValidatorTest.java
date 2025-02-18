/*  Lizbeth Garcia-Lopez & Jordan Bassett   
    Password Factory
    CS236 - Advanced OOP (Java 2)
    Final Project: Strong Password Creation Game for Kids
    Updated circa February 16, 2025
    Desc: This is a test program for validating passwords using the PasswordValidator class.
    It runs multiple test cases and prints out the password strength and feedback.

    - Uses PasswordValidator to evaluate password strength.
    - Tests a set of predefined passwords.
    - Prints the password strength score and feedback.
    - Runs independently from the main game UI.
*/

package passwordfactory;
public class PasswordValidatorTest
{
    /* Main method that runs the password validation tests.
     * @param args Command-line arguments (not used).  */
    public static void main(String[] args)
    {
        //Create an instance of the PasswordValidator
        PasswordValidator validator = new PasswordValidator();

        //array of test passwords to evaluate
        String[] testPasswords = 
        {
            "123456",            // Weak: Too short, no variety
            "Password123!",      // Moderate: Common pattern but has variety
            "HelloWorld!",       // Weak: Common phrase, lacks numbers
            "Str0ngP@ss!",       // Strong: Good length and character mix
            "SuperSecure2025!",  // Strong: Long, diverse characters
            "qwerty1234"         // Weak: Common sequence
        };

        //Loop through each password and print evaluation results
        for (String pw : testPasswords)
        {
            System.out.println("Testing: " + pw);
            System.out.println("Score: " + validator.checkStrength(pw)); // Prints numeric strength score
            System.out.println("Feedback: " + validator.giveFeedback(pw)); // Prints password improvement feedback
            System.out.println("------------");
        }
    }
}
