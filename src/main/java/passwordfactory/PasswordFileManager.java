/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: PasswordFileManager.java
 Author(s): Lizbeth Garcia-Lopez & Jordan Bassett
 Last Updated: March 16, 2025
 
 Description:
    Manages all file operations for Password Factory. This includes loading and saving helpful phrases,
    common words, and password logs. Provides methods for reading from and writing to text files
    used in the game and Password Lab.

 Features:
    - Loads and saves helpful phrases (phrases.txt)
    - Loads and saves common words (common_words.txt)
    - Logs submitted passwords and their strength ratings to a log file (password_log.txt).
    - Validates and adds new phrases to the existing phrase file

 Dependencies:
    - Used by PasswordGame.java, PasswordLab.java, and PasswordValidator.java
 */

package passwordfactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Manages file operations for Password Factory.
 // Loads and saves phrases, common words, and logs password submissions.
public class PasswordFileManager
{
    private final String phrasesFile;
    private final String passwordLogFile;
    private PrintWriter logWriter;

    /**
     * Constructor for PasswordFileManager.
     * @param phrasesFile Name of the helpful phrases file
     * @param passwordLogFile Name of the password log file
     */
    public PasswordFileManager(String phrasesFile, String passwordLogFile)
    {
        this.phrasesFile = phrasesFile;
        this.passwordLogFile = passwordLogFile;
    }

    /**
     * Loads helpful phrases from the specified file
     * @return List of helpful phrases
     */
    public List<String> loadPhrases()
    {
        List<String> phrases = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(phrasesFile)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.trim().isEmpty()) {
                    phrases.add(line.trim());
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error loading phrases: " + e.getMessage());
        }

        return phrases;
    }

    /**
     * Saves the provided phrases list to the specified file.
     * @param phrases List of helpful phrases.
     * @param filename File to save phrases to.
     */
    public void savePhrases(List<String> phrases, String filename)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename)))
        {
            for (String phrase : phrases) {
                writer.println(phrase);
            }
        }
        catch (IOException e) {
            System.out.println("Error saving phrases: " + e.getMessage());
        }
    }

    /**
     * Loads common words from the specified file.
     * Prevents duplicate entries manually.
     * @param filename Name of the common words file.
     * @return List of common words.
     */
    public List<String> loadCommonWords(String filename)
    {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String word = line.trim().toLowerCase();

                if (!word.isEmpty() && !words.contains(word)) // prevent duplicates
                {
                    words.add(word);
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error loading common words: " + e.getMessage());
        }
        return words;
    }

    /**
     * Saves the provided common words list to the specified file
    * @param words List of common words
     * @param filename File to save common words to
     */
    public void saveCommonWords(List<String> words, String filename)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename)))
        {
            for (String word : words) {
                writer.println(word);
            }
        }
        catch (IOException e) {
            System.out.println("Error saving common words: " + e.getMessage());
        }
    }

    /**
     * Initializes the password log file writer.
     * Opens the log file for appending password records.
     */
    public void initPasswordLog()
    {
        try {
            logWriter = new PrintWriter(new FileWriter(passwordLogFile, true)); // append mode
        }
        catch (IOException e) {
            System.out.println("Error initializing password log: " + e.getMessage());
        }
    }

    /**
     * Saves a generated password and its strength rating to the password log file.
     * Format: [password] - [strength]
     * Example: dragon123! - Strong
     * @param password The password submitted by the user.
     * @param strength The evaluated strength rating (Weak/Moderate/Strong).
     */
    public void savePassword(String password, String strength)
    {
        if (logWriter != null)
        {
            logWriter.println(password + " - " + strength);
            logWriter.flush();
        }
        else {
            System.out.println("Log writer not initialized.");
        }
    }

    //closes the password log writer
     
    public void closeLog()
    {
        if (logWriter != null){
            logWriter.close();
        }
    }

    /**
     * Adds a new phrase to the phrase file if it's valid and not already present
     * @param phrase The phrase to add
     * @return True if the phrase was added successfully, false otherwise
     */
    public boolean addPhrase(String phrase)
    {
        if (phrase == null || phrase.trim().isEmpty()) {
            return false;
        }

        List<String> phrases = loadPhrases();
        if (phrases.contains(phrase.trim())) {
            return false; //already exists
        }

        phrases.add(phrase.trim());
        savePhrases(phrases, phrasesFile);
        return true;
    }
}
