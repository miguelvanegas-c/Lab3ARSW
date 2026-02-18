package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filters.BlueprintsFilter;
import edu.eci.arsw.blueprints.mapper.BlueprintMapper;
import edu.eci.arsw.blueprints.mapper.PointMapper;
import edu.eci.arsw.blueprints.model.dto.PointDTO;
import edu.eci.arsw.blueprints.model.entity.Blueprint;
import edu.eci.arsw.blueprints.model.dto.BlueprintDTO;
import edu.eci.arsw.blueprints.model.entity.Point;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.blueprint.BlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintPersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BlueprintsServices {

    private final BlueprintPersistence persistence;
    private final BlueprintsFilter filter;
    private final BlueprintMapper blueprintMapper;
    private final PointMapper pointMapper;


    public void addNewBlueprint(BlueprintDTO bp) throws BlueprintPersistenceException, BlueprintNotFoundException {
        Blueprint blueprint = blueprintMapper.toEntity(bp);
        persistence.saveBlueprint(blueprint);
        String author = blueprint.getAuthor();
        String name = blueprint.getName();
        for (PointDTO pointDTO : bp.getPoints()) {
            addPoint(author, name, pointDTO);
        }
    }

    public Set<BlueprintDTO> getAllBlueprints() {
        Set<Blueprint> blueprints = persistence.getAllBlueprints();
        return blueprints.stream()
                .map(blueprintMapper::toDto)
                .collect(Collectors.toSet());
    }

    public Set<BlueprintDTO> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprints = persistence.getBlueprintsByAuthor(author);
        return blueprints.stream()
                .map(blueprintMapper::toDto)
                .collect(Collectors.toSet());
    }

    public BlueprintDTO getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint blueprint = filter.apply(persistence.getBlueprint(author, name));
        return blueprintMapper.toDto(blueprint);
    }

    public void addPoint(String author, String name, PointDTO dto) throws BlueprintNotFoundException {
        Point point = pointMapper.toEntity(dto);
        persistence.addPoint(author, name, point.getX(), point.getY());
    }
}
