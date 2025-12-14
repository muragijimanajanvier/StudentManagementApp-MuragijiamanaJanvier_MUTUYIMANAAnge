package model;

/**
 * Interface that marks objects as having a unique identifier.
 * Used by GenericDAO for CRUD operations that require an ID.
 */
public interface Identifiable {
    
    /**
     * Get the unique identifier of the object.
     * @return The object's ID
     */
    int getId();
    
    /**
     * Set the unique identifier of the object.
     * @param id The new ID to assign
     */
    void setId(int id);
}