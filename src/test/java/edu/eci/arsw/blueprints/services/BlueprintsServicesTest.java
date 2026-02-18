package edu.eci.arsw.blueprints.services;


import edu.eci.arsw.blueprints.filters.BlueprintsFilter;

import edu.eci.arsw.blueprints.mapper.BlueprintMapper;
import edu.eci.arsw.blueprints.mapper.PointMapper;
import edu.eci.arsw.blueprints.model.dto.BlueprintDTO;
import edu.eci.arsw.blueprints.model.dto.PointDTO;
import edu.eci.arsw.blueprints.model.entity.Blueprint;
import edu.eci.arsw.blueprints.model.entity.Point;
import edu.eci.arsw.blueprints.persistence.blueprint.BlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintPersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlueprintsServicesTest {

    @Mock
    private BlueprintMapper blueprintMapper;
    @Mock
    private PointMapper pointMapper;

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
        blueprint.setId(1L);
        when(blueprintMapper.toDto(blueprint)).thenReturn(new BlueprintDTO("john", "house", List.of()));
        doNothing().when(blueprintPersistence).saveBlueprint(blueprint);
        BlueprintDTO blueprintDTO = new BlueprintDTO("john", "house", List.of(new PointDTO(0, 0)));
        assertDoesNotThrow(() -> blueprintsServices.addNewBlueprint(blueprintDTO));
        verify(blueprintPersistence, times(1)).saveBlueprint(blueprint);
    }

    @Test
    void testAddNewBlueprint_ThrowsException() throws BlueprintPersistenceException {
        Blueprint blueprint = new Blueprint("john", "house", List.of(new Point(0, 0)));
        blueprint.setId(1L);
        when(blueprintMapper.toDto(blueprint)).thenReturn(new BlueprintDTO("john", "house", List.of()));
        doThrow(new BlueprintPersistenceException("Already exists"))
                .when(blueprintPersistence).saveBlueprint(blueprint);
        BlueprintDTO blueprintDTO = new BlueprintDTO("john", "house", List.of(new PointDTO(0, 0)));
        assertThrows(BlueprintPersistenceException.class,
                () -> blueprintsServices.addNewBlueprint(blueprintDTO));
    }

    @Test
    void testGetBlueprint_Success() throws BlueprintNotFoundException {
        Blueprint blueprint = new Blueprint("john", "house", List.of(new Point(0, 0)));
        blueprint.setId(1L);
        when(blueprintMapper.toDto(blueprint)).thenReturn(new BlueprintDTO("john", "house", List.of()));
        when(blueprintPersistence.getBlueprint("john", "house")).thenReturn(blueprint);
        when(filter.apply(blueprint)).thenReturn(blueprint);

        BlueprintDTO result = blueprintsServices.getBlueprint("john", "house");

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
    void testAddPoint_Success() throws BlueprintNotFoundException, BlueprintPersistenceException {
        doNothing().when(blueprintPersistence).addPoint("john", "house", 10, 10);
        doNothing().when(blueprintPersistence).saveBlueprint(new Blueprint("john", "house"));
        Point point = new Point(10, 10);
        PointDTO pointDTO = new PointDTO(10, 10);
        when(pointMapper.toDto(point)).thenReturn(pointDTO);
        assertDoesNotThrow(() -> blueprintsServices.addPoint("john", "house", pointDTO));
        verify(blueprintPersistence, times(1)).addPoint("john", "house", 10, 10);
    }
}



