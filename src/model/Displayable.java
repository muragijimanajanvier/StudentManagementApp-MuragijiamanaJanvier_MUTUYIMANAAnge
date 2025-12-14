package model;

/**
 * The Displayable interface defines a standard way for objects
 * to provide a text representation of themselves.
 * <p>
 * Classes implementing this interface can be displayed in UI components
 * (like lists, tables, or labels) without needing to know their specific types.
 * This promotes polymorphism and separation of concerns.
 * </p>
 */
public interface Displayable {
    
    /**
     * Returns a formatted string representation of the object.
     * <p>
     * This method should provide human-readable text that clearly identifies
     * or summarizes the object's key information. Useful for display in:
     * - ComboBox items
     * - ListView entries
     * - Table cells
     * - Log messages
     * - User interface labels
     * </p>
     * 
     * @return a non-null String containing the display text for this object.
     *         Should be formatted appropriately for end-user viewing.
     * 
     * @implNote Implementing classes should ensure the returned string
     *           is concise yet informative. Avoid returning null to prevent
     *           NullPointerExceptions in UI components.
     * 
     * @example For a Student class, this might return:
     *          "John Doe (ID: S12345) - Computer Science"
     */
    String getDisplayText();
}