
package passwordfactory;
/**
 * Test program for password validation, separate from the GUI application.
 */
public class PasswordValidatorTest {

    public static void main(String[] args) {
        PasswordValidator validator = new PasswordValidator();
        
        String[] testPasswords = {
            "123456", "Password123!", "HelloWorld!", "Str0ngP@ss!", "SuperSecure2025!", "qwerty1234"
        };

        for (String pw : testPasswords) {
            System.out.println("Testing: " + pw);
            System.out.println("Score: " + validator.checkStrength(pw)); // Show numeric score
            System.out.println("Feedback: " + validator.giveFeedback(pw));
            System.out.println("------------");
        }
    }
}
