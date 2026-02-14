package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresBlueprintPersistence extends JpaRepository<Blueprint,Long>{
}
