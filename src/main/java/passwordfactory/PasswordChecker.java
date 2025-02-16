/*
    Jordan Bassett
    Lizbeth Garcia-Lopez
    Password Factory
    CS236 - Advanced OOP (Java 2)
    Final Project: Strong Password Creation Game for Kids
    Updated circa February 16, 2025

    Description:
    This interface defines methods for evaluating password strength and providing feedback
    It is implemented by PasswordValidator to enforce password rules
*/
package passwordfactory;

/**
 * The PasswordChecker interface provides a structure for validating passwords
 * Implementing classes must define methods for evaluating password strength
 * and providing feedback
 */
public interface PasswordChecker
{
    /*Evaluates the strength of a given password based on security criteria
     @param password The password to check.
     @return Strength level as an integer:
              0 = Weak
              1 = Moderate
              2 = Strong
     */
    int checkStrength(String password);
    /* Provides feedback on how to improve password strength
     @param password The password to analyze.
     @return A feedback message about password strength and recommendations.
     */
    String giveFeedback(String password);
}
