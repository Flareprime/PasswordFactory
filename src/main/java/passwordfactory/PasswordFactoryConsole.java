/*  Jordan Bassett & Lizbeth Garcia-Lopez
    Password Factory
    CS236 - Advanced OOP (Java 2)
    Final Project: Strong Password Creation Game for Kids
    Updated circa February 17, 2025
    Desc:     This is a console-based version of the Password Factory game, designed as an alternative 
    to the GUI implementation, mostly for testing purposes
    Users can assemble a password by selecting a phrase, 
    number, and special character, then evaluates its strength.

    - Displays a list of phrases for the user to choose from.
    - Allows selection of a number (0-9) and a special character.
    - Generates a password using the selected components.
    - Evaluates password strength using PasswordValidator.
    - Saves the generated password to a file (selected_phrases.txt).

    - Runs independently from the GUI version (App.java).
    - Uses PasswordValidator.java for strength checking.
    - Writes to a file for storage.

    Usage: Run this file, it has a main()
*/
package passwordfactory;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PasswordFactoryConsole {
    // Sample phrases
    private static final String[] PHRASES = {
        "I miss my two cats",
        "Star Wars is the best movie",
        "Protect your online accounts",
        "A white barn is pretty"
    };

    // Special characters
    private static final String[] SPECIAL_CHARS = {"!", "@", "#", "$", "%", "&", "*"};

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Password Helper Mode =====");
        System.out.println("Select a phrase:");

        //Display phrases
        for (int i = 0; i < PHRASES.length; i++) {
            System.out.println((i + 1) + ". " + PHRASES[i]);
        }

        // Get phrase selection
        System.out.print("Enter choice (1-" + PHRASES.length + "): ");
        int phraseChoice = scanner.nextInt();
        scanner.nextLine(); // eat newline
        if (phraseChoice < 1 || phraseChoice > PHRASES.length)
        {
            System.out.println("Invalid choice. Exiting.");
            return;
        }
        String selectedPhrase = PHRASES[phraseChoice - 1].replaceAll("\\s", ""); //Remove spaces

        //get number selection
        System.out.print("Enter a number (0-9): ");
        int numberChoice = scanner.nextInt();
        scanner.nextLine();

        //Display special characters
        System.out.println("Select a special character:");
        for (int i = 0; i < SPECIAL_CHARS.length; i++) {
            System.out.println((i + 1) + ". " + SPECIAL_CHARS[i]);
        }
        System.out.print("Enter choice (1-" + SPECIAL_CHARS.length + "): ");
        int specialChoice = scanner.nextInt();
        scanner.nextLine();
        if (specialChoice < 1 || specialChoice > SPECIAL_CHARS.length)
        {
            System.out.println("Invalid choice. Exiting.");
            return;
        }
        String selectedSpecialChar = SPECIAL_CHARS[specialChoice - 1];

        //Generate password
        String generatedPassword = selectedPhrase + numberChoice + selectedSpecialChar;
        System.out.println("\nGenerated Password: " + generatedPassword);

        //Save to file (Phase 3 requirement)
        try (FileWriter writer = new FileWriter("pwd_output.txt", true))
        {
            writer.write(selectedPhrase + "," + numberChoice + "," + selectedSpecialChar + "\n");
            System.out.println("Password saved to file.");
        }
        catch (IOException e){
            System.out.println("Error saving password.");
        }

        scanner.close();
    }
}
