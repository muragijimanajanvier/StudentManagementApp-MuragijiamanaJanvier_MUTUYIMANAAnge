package db;

import model.Identifiable;
import java.util.List;

/**
 * Generic Data Access Object (DAO) interface for CRUD operations.
 * 
 * @param <T> The entity type that extends Identifiable (must have an ID)
 */
public interface GenericDAO<T extends Identifiable> {
    
    /**
     * Create - Insert a new entity into the database
     * @param entity The entity to be created
     * @throws Exception If database operation fails
     */
    void create(T entity) throws Exception;
    
    /**
     * Read - Retrieve an entity by its ID
     * @param id The ID of the entity to retrieve
     * @return The entity with the given ID, or null if not found
     * @throws Exception If database operation fails
     */
    T read(int id) throws Exception;
    
    /**
     * Update - Modify an existing entity in the database
     * @param entity The entity with updated values (must have valid ID)
     * @throws Exception If database operation fails
     */
    void update(T entity) throws Exception;
    
    /**
     * Delete - Remove an entity from the database by ID
     * @param id The ID of the entity to delete
     * @throws Exception If database operation fails
     */
    void delete(int id) throws Exception;
    
    /**
     * Find All - Retrieve all entities from the database
     * @return List of all entities
     * @throws Exception If database operation fails
     */
    List<T> findAll() throws Exception;
}