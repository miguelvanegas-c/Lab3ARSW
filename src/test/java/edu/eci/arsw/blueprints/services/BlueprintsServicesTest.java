package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filters.BlueprintsFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.blueprint.BlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintPersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlueprintsServicesTest {

    @Mock
    private BlueprintPersistence blueprintPersistence;

    @Mock
    private BlueprintsFilter filter;

    @InjectMocks
    private BlueprintsServices blueprintsServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddNewBlueprint_Success() throws BlueprintPersistenceException {
        Blueprint blueprint = new Blueprint("john", "house", List.of(new Point(0, 0)));
        doNothing().when(blueprintPersistence).saveBlueprint(blueprint);

        assertDoesNotThrow(() -> blueprintsServices.addNewBlueprint(blueprint));
        verify(blueprintPersistence, times(1)).saveBlueprint(blueprint);
    }

    @Test
    void testAddNewBlueprint_ThrowsException() throws BlueprintPersistenceException {
        Blueprint blueprint = new Blueprint("john", "house", List.of(new Point(0, 0)));
        doThrow(new BlueprintPersistenceException("Already exists"))
                .when(blueprintPersistence).saveBlueprint(blueprint);

        assertThrows(BlueprintPersistenceException.class, 
                () -> blueprintsServices.addNewBlueprint(blueprint));
    }

    @Test
    void testGetBlueprint_Success() throws BlueprintNotFoundException {
        Blueprint blueprint = new Blueprint("john", "house", List.of(new Point(0, 0)));
        when(blueprintPersistence.getBlueprint("john", "house")).thenReturn(blueprint);
        when(filter.apply(blueprint)).thenReturn(blueprint);

        Blueprint result = blueprintsServices.getBlueprint("john", "house");

        assertNotNull(result);
        assertEquals("john", result.getAuthor());
        assertEquals("house", result.getName());
        verify(filter, times(1)).apply(blueprint);
    }

    @Test
    void testGetBlueprint_NotFound() throws BlueprintNotFoundException {
        when(blueprintPersistence.getBlueprint("unknown", "unknown"))
                .thenThrow(new BlueprintNotFoundException("Not found"));

        assertThrows(BlueprintNotFoundException.class, 
                () -> blueprintsServices.getBlueprint("unknown", "unknown"));
    }

    @Test
    void testGetBlueprintsByAuthor_Success() throws BlueprintNotFoundException {
        Blueprint bp1 = new Blueprint("john", "house", List.of(new Point(0, 0)));
        Blueprint bp2 = new Blueprint("john", "garage", List.of(new Point(5, 5)));
        Set&lt;Blueprint&gt; blueprints = new HashSet&lt;&gt;(List.of(bp1, bp2));

        when(blueprintPersistence.getBlueprintsByAuthor("john")).thenReturn(blueprints);

        Set&lt;Blueprint&gt; result = blueprintsServices.getBlueprintsByAuthor("john");

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllBlueprints() {
        Blueprint bp1 = new Blueprint("john", "house", List.of(new Point(0, 0)));
        Blueprint bp2 = new Blueprint("jane", "garden", List.of(new Point(2, 2)));
        Set&lt;Blueprint&gt; blueprints = new HashSet&lt;&gt;(List.of(bp1, bp2));

        when(blueprintPersistence.getAllBlueprints()).thenReturn(blueprints);

        Set&lt;Blueprint&gt; result = blueprintsServices.getAllBlueprints();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testAddPoint_Success() throws BlueprintNotFoundException {
        doNothing().when(blueprintPersistence).addPoint("john", "house", 10, 10);

        assertDoesNotThrow(() -> blueprintsServices.addPoint("john", "house", 10, 10));
        verify(blueprintPersistence, times(1)).addPoint("john", "house", 10, 10);
    }
}