package edu.eci.arsw.blueprints.persistence.blueprint;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintPersistenceException;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Primary
public interface PostgresBlueprintPersistence extends JpaRepository<Blueprint,Long>, BlueprintPersistence {
    @Override
    default void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException{
        Optional<Blueprint> blueprint = findByAuthorAndName(bp.getAuthor(), bp.getName());
        if(blueprint.isPresent()){
            this.save(bp);
        }else{
            throw new BlueprintPersistenceException("The blueprint already exist");
        }
    }

    default Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return findByAuthorAndName(author,name)
                .orElseThrow(()-> new BlueprintNotFoundException("Blueprint not found: %s/%s".formatted(author, name)));
    }

    default Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        Set<Blueprint> blueprints = findAllByAuthor(author);
        if(blueprints.isEmpty()){
            throw new BlueprintNotFoundException("No blueprints for author: " + author);
        }
        return blueprints;
    }

    default Set<Blueprint> getAllBlueprints() {
        List<Blueprint> blueprints = findAll();
        return new HashSet<>(blueprints);
    }

    default void addPoint(String author, String name, int x, int y) throws BlueprintNotFoundException{
        Blueprint blueprint = getBlueprint(author,name);
        Point point = new Point(x,y);
        blueprint.addPoint(point);
        save(blueprint);
    }
    Set<Blueprint> findAllByAuthor(String author);
    Optional<Blueprint> findByAuthorAndName(String author, String name);
}
