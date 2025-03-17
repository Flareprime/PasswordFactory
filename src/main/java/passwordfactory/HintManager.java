/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: HintManager.java
 Author(s): Jordan Bassett & Lizbeth Garcia-Lopez
 Last Updated: March 16, 2025
 
 Description:
    Manages both dynamic and generic hints that provide feedback during password creation
    Dynamic hints are reset every round, while generic hints are always available

 Features:
   - Adds and retrieves dynamic hints during gameplay.
   - Provides a list of generic hints that offer basic password advice.
   - Clears dynamic hints between rounds.
   - Can return hints as formatted strings for display.

 Dependencies:
    - Uses: Hint.java
    - Called by: PasswordValidator.java and other password evaluation components.  */
package passwordfactory;
import java.util.ArrayList;
import java.util.List;

//ArrayLists, as we add hints sequentially, and don’t need to search/remove specific ones often
public class HintManager
{
    private final List<Hint> dynamicHints;
    private final List<Hint> genericHints;

    // Constructor for HintManager.
    public HintManager()
    {
        dynamicHints = new ArrayList<>();
        genericHints = new ArrayList<>();

        // Add generic hints at construction
        genericHints.add(new Hint("Try making it longer.", "Length"));
        genericHints.add(new Hint("Add an uppercase letter.", "Uppercase"));
        genericHints.add(new Hint("Add a lowercase letter.", "Lowercase"));
        genericHints.add(new Hint("Add a number.", "Numbers"));
        genericHints.add(new Hint("Add a special character.", "Special Characters"));
    }

    /**
     * Adds a dynamic hint during game
     * @param hint A hint generated based on the user's input.
     */
    public void addDynamicHint(Hint hint) {
        dynamicHints.add(hint);
    }

    /**
     * Returns the current list of dynamic hints
     * @return List of dynamic hints.
     */
    public List<Hint> getDynamicHints() {
        return dynamicHints;
    }

    /**
     * Returns the list of generic hints
     * @return List of generic hints
     */
    public List<Hint> getGenericHints() {
        return genericHints;
    }

    //clears all dynamic hints between rounds.
    public void clearDynamicHints() {
        dynamicHints.clear();
    }

    /**
     * Returns the current dynamic hints as formatted strings
     * @return List of dynamic hint messages.
     */
    public List<String> getDynamicHintMessages()
    {
        List<String> messages = new ArrayList<>();

        for (Hint hint : dynamicHints) {
            messages.add(hint.toString());
        }
        return messages;
    }

    /**
     * Returns all generic hints as formatted strings
     * @return List of generic hint messages
     */
    public List<String> getGenericHintMessages()
    {
        List<String> messages = new ArrayList<>();

        for (Hint hint : genericHints) {
            messages.add(hint.toString());
        }
        return messages;
    }
}
