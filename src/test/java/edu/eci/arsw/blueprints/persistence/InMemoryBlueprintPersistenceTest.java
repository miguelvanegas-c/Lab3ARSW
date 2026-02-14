package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.blueprint.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintPersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryBlueprintPersistenceTest {

    private InMemoryBlueprintPersistence persistence;

    @BeforeEach
    void setUp() {
        persistence = new InMemoryBlueprintPersistence();
    }

    @Test
    void testSaveBlueprint_Success() throws BlueprintPersistenceException {
        Blueprint bp = new Blueprint("author1", "design1", List.of(new Point(1, 1)));
        
        assertDoesNotThrow(() -> persistence.saveBlueprint(bp));
    }

    @Test
    void testSaveBlueprint_AlreadyExists() {
        Blueprint bp = new Blueprint("john", "house", List.of(new Point(0, 0)));
        
        assertThrows(BlueprintPersistenceException.class, 
                () -> persistence.saveBlueprint(bp));
    }

    @Test
    void testGetBlueprint_Success() throws BlueprintNotFoundException {
        Blueprint bp = persistence.getBlueprint("john", "house");
        
        assertNotNull(bp);
        assertEquals("john", bp.getAuthor());
        assertEquals("house", bp.getName());
    }

    @Test
    void testGetBlueprint_NotFound() {
        assertThrows(BlueprintNotFoundException.class, 
                () -> persistence.getBlueprint("unknown", "unknown"));
    }

    @Test
    void testGetBlueprintsByAuthor_Success() throws BlueprintNotFoundException {
        Set<Blueprint> blueprints = persistence.getBlueprintsByAuthor("john");
        
        assertNotNull(blueprints);
        assertEquals(2, blueprints.size());
        assertTrue(blueprints.stream().allMatch(bp -> bp.getAuthor().equals("john")));
    }

    @Test
    void testGetBlueprintsByAuthor_NotFound() {
        assertThrows(BlueprintNotFoundException.class, 
                () -> persistence.getBlueprintsByAuthor("nonexistent"));
    }

    @Test
    void testGetAllBlueprints() {
        Set<Blueprint> all = persistence.getAllBlueprints();
        
        assertNotNull(all);
        assertTrue(all.size() >= 3);
    }

    @Test
    void testAddPoint_Success() throws BlueprintNotFoundException {
        int initialSize = persistence.getBlueprint("john", "house").getPoints().size();
        
        persistence.addPoint("john", "house", 100, 100);
        
        Blueprint updated = persistence.getBlueprint("john", "house");
        assertEquals(initialSize + 1, updated.getPoints().size());
    }

    @Test
    void testAddPoint_BlueprintNotFound() {
        assertThrows(BlueprintNotFoundException.class,
                () -> persistence.addPoint("unknown", "unknown", 10, 10));
    }

    @Test
    void testSaveMultipleBlueprints() throws BlueprintPersistenceException {
        Blueprint bp1 = new Blueprint("alice", "design1", List.of(new Point(1, 1)));
        Blueprint bp2 = new Blueprint("alice", "design2", List.of(new Point(2, 2)));
        
        persistence.saveBlueprint(bp1);
        persistence.saveBlueprint(bp2);
        
        Set<Blueprint> all = persistence.getAllBlueprints();
        assertTrue(all.size() >= 5);
    }

    @Test
    void testGetBlueprintReturnsCorrectPoints() throws BlueprintNotFoundException {
        Blueprint bp = persistence.getBlueprint("john", "house");
        
        assertNotNull(bp.getPoints());
        assertEquals(4, bp.getPoints().size());
    }
}
