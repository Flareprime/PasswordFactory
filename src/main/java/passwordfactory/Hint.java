/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: Hint.java
 Author(s): Lizbeth Garcia-Lopez & Jordan Bassett
 Last Updated: March 16, 2025
 
 Description:
    A simple Plain Old Java Object (POJO) to hold hint data
    Hint represents a suggestion or piece of feedback about a password

 Features:
    - Stores a feedback message and its category
    - Provides methods to access hint data and display it as a string.

 Dependencies:
   - Used by HintManager.java to manage and display hints. */
package passwordfactory;

//simple POJO (Plain Old Java Object) to hold hint data.
public class Hint
{
    //Message that explains the hint or suggestion
    private String message;

    //category for the hint (e.g., "Length", "Complexity")
    private String category;

    /**
     * Constructor for Hint.
     * @param message The feedback message.
     * @param category The category or type of hint.
     */
    public Hint(String message, String category)
    {
        this.message = message;
        this.category = category;
    }

    /**
     * Gets the hint message.
     * @return The feedback message.
     */
    public String getHint() {
        return message;
    }

    /**
     * Gets the hint category.
     * @return The category of the hint.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns a string of the hint.
     * @return The category and message combined.
     */
    @Override
    public String toString() {
        return "[" + category + "] " + message;
    }
}