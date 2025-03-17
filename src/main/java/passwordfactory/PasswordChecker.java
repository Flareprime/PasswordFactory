/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: PasswordChecker.java
 Author(s): Jordan Bassett & Lizbeth Garcia-Lopez
 Last Updated: March 16, 2025
 
 Description:
    This interface defines methods for evaluating password strength and providing feedback
    It is implemented by PasswordValidator to enforce password rules and provide user guidance.
 
 Features:
    - Defines a contract for password validation strategies
    - Ensures all password evaluators provide strength checking and feedback methods.
 
 Dependencies:
    - Implemented by: PasswordValidator.java */
package passwordfactory;


// PasswordChecker interface defines methods for password validation and feedback.
// is implemented by PasswordValidator.
public interface PasswordChecker
{
    /**
     * Evaluates the strength of a given password
     * @param password The password to check
     * @return Strength level as an integer:
     *         0 = Weak
     *         1 = Moderate
     *         2 = Strong
     */
    int checkStrength(String password);

    /**
     * Provides feedback on how to improve password strength.
     * @param password The password to analyze.
     * @return A feedback message about password strength and recommendations.
     */
    String giveFeedback(String password);
}
