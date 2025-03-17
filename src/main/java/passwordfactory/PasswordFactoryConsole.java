/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: PasswordFactoryConsole.java
 Author(s): Jordan Bassett & Lizbeth Garcia-Lopez
 Last Updated: March 16, 2025
 
 Description:
    Early development console application for testing password generation and validation
    Allows users to select phrases, numbers, and special characters to assemble a password
    Evaluates password strength using PasswordValidator and saves the result to a file
    Runs on its own

 Features:
    - Console-based user input for selecting components of a password.
    - Assembles a password from phrases, numbers, and symbols.
    - Validates the password using PasswordValidator.
    - Saves the password and its strength evaluation to a file (pwd_output.txt).

 Dependencies:
    - Uses: PasswordValidator.java
    - Writes output to: pwd_output.txt */

package passwordfactory;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PasswordFactoryConsole
{
    //Sample phrase options for the console version
    private static final String[] PHRASES = {"sunshine", "rainbow", "monkey", "dragon", "coffee"};
    
    //Special characters that can be added to the password
    private static final String[] SPECIAL_CHARS = {"!", "@", "#", "$", "%", "&", "*"};

    /**
     * Main method for running the console application
     * Guides the user through creating a password and validates it
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        StringBuilder passwordBuilder = new StringBuilder();

        System.out.println("Welcome to Password Factory Console Edition!");
        
        //Phrase selection
        System.out.println("Choose a phrase:");
        for (int i = 0; i < PHRASES.length; i++)
        {
            System.out.println((i + 1) + ". " + PHRASES[i]);
        }

        int phraseChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (phraseChoice >= 1 && phraseChoice <= PHRASES.length)
        {
            passwordBuilder.append(PHRASES[phraseChoice - 1]);
        }
        else
        {
            System.out.println("Invalid choice. Exiting...");
            return;
        }

        // Number input
        System.out.println("Enter a number to add:");
        String number = scanner.nextLine();
        passwordBuilder.append(number);

        //special character selection
        System.out.println("Choose a special character:");
        for (int i = 0; i < SPECIAL_CHARS.length; i++)
        {
            System.out.println((i + 1) + ". " + SPECIAL_CHARS[i]);
        }

        int charChoice = scanner.nextInt();

        if (charChoice >= 1 && charChoice <= SPECIAL_CHARS.length)
        {
            passwordBuilder.append(SPECIAL_CHARS[charChoice - 1]);
        }
        else
        {
            System.out.println("Invalid choice. Exiting...");
            return;
        }

        String finalPassword = passwordBuilder.toString();

        //Validate the password
        PasswordFileManager fileManager = new PasswordFileManager("phrases.txt", "password_log.txt");
        PasswordValidator validator = new PasswordValidator(fileManager, "easy");

        int strength = validator.checkStrength(finalPassword);
        String strengthLabel = (strength == 2) ? "Strong" : (strength == 1) ? "Moderate" : "Weak";

        // Display result
        System.out.println("Generated Password: " + finalPassword);
        System.out.println("Password Strength: " + strengthLabel);

        // Save the password and strength to a file
        try (PrintWriter writer = new PrintWriter(new FileWriter("pwd_output.txt", true)))
        {
            writer.println(finalPassword + " - " + strengthLabel);
            System.out.println("Password saved to pwd_output.txt");
        }
        catch (IOException e)
        {
            System.out.println("Error saving password to file: " + e.getMessage());
        }

        scanner.close();
    }
}
