/* Password Factory Project
 CS236 - Advanced OOP (Java 2)
 Class: SystemInfo.java
 Author(s): Provided by NetBeans | Integrated by Jordan Bassett & Lizbeth Garcia-Lopez
 Last Updated: March 16, 2025
 
 Description:
    Utility class to retrieve system properties for Java and JavaFX versions.
    Used by AboutScreen to display system information.

 Features:
    - Retrieves the Java runtime version.
    - Retrieves the JavaFX runtime version.
 
 Dependencies:
    - Used by: AboutScreen.java
 */

package passwordfactory;
public class SystemInfo
{
    /**
     * Returns the current Java version.
     * @return Java version as a String.
     */
    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Returns the current JavaFX version.
     * @return JavaFX version as a String.
     */
    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }
}
