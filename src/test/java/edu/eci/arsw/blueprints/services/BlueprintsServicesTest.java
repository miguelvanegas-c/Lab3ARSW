package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filters.BlueprintsFilter;
import edu.eci.arsw.blueprints.persistence.blueprint.BlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

//    @Test
//    void testAddNewBlueprint_Success() throws BlueprintPersistenceException {
//        Blueprint blueprint = new Blueprint("john", "house", List.of(new Point(0, 0)));
//        doNothing().when(blueprintPersistence).saveBlueprint(blueprint);
//
//        assertDoesNotThrow(() -> blueprintsServices.addNewBlueprint(blueprint));
//        verify(blueprintPersistence, times(1)).saveBlueprint(blueprint);
//    }
//
//    @Test
//    void testAddNewBlueprint_ThrowsException() throws BlueprintPersistenceException {
//        Blueprint blueprint = new Blueprint("john", "house", List.of(new Point(0, 0)));
//        doThrow(new BlueprintPersistenceException("Already exists"))
//                .when(blueprintPersistence).saveBlueprint(blueprint);
//
//        assertThrows(BlueprintPersistenceException.class,
//                () -> blueprintsServices.addNewBlueprint(blueprint));
//    }

//    @Test
//    void testGetBlueprint_Success() throws BlueprintNotFoundException {
//        Blueprint blueprint = new Blueprint("john", "house", List.of(new Point(0, 0)));
//        when(blueprintPersistence.getBlueprint("john", "house")).thenReturn(blueprint);
//        when(filter.apply(blueprint)).thenReturn(blueprint);
//
//        Blueprint result = blueprintsServices.getBlueprint("john", "house");
//
//        assertNotNull(result);
//        assertEquals("john", result.getAuthor());
//        assertEquals("house", result.getName());
//        verify(filter, times(1)).apply(blueprint);
//    }

//    @Test
//    void testGetBlueprint_NotFound() throws BlueprintNotFoundException {
//        when(blueprintPersistence.getBlueprint("unknown", "unknown"))
//                .thenThrow(new BlueprintNotFoundException("Not found"));
//
//        assertThrows(BlueprintNotFoundException.class,
//                () -> blueprintsServices.getBlueprint("unknown", "unknown"));
//    }
//
//
//    @Test
//    void testAddPoint_Success() throws BlueprintNotFoundException {
//        doNothing().when(blueprintPersistence).addPoint("john", "house", 10, 10);
//
//        assertDoesNotThrow(() -> blueprintsServices.addPoint("john", "house", 10, 10));
//        verify(blueprintPersistence, times(1)).addPoint("john", "house", 10, 10);
//    }
}