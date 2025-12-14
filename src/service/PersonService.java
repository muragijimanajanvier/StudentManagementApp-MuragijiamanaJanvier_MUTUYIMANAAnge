package service;

import model.Person;
import java.util.List;

/**
 * Service layer interface for Person entity operations.
 * Acts as a bridge between UI and data access layers.
 * 
 * @param <T> Type extending Person (e.g., Student, Teacher)
 */
public interface PersonService<T extends Person> {
    
    /**
     * Add a new person record to the system.
     * @param person The person object to add
     * @throws Exception If operation fails (e.g., database error)
     */
    void addPerson(T person) throws Exception;
    
    /**
     * Retrieve a person by their unique ID.
     * @param id The ID of the person to retrieve
     * @return The person object, or null if not found
     * @throws Exception If operation fails
     */
    T getPerson(int id) throws Exception;
    
    /**
     * Update an existing person's information.
     * @param person The person object with updated data
     * @throws Exception If operation fails
     */
    void updatePerson(T person) throws Exception;
    
    /**
     * Delete a person record by ID.
     * @param id The ID of the person to delete
     * @throws Exception If operation fails
     */
    void deletePerson(int id) throws Exception;
    
    /**
     * Get all person records from the system.
     * @return List of all persons
     * @throws Exception If operation fails
     */
    List<T> getAllPersons() throws Exception;
}