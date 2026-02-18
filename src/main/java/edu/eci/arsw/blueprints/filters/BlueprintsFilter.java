package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.entity.Blueprint;

public interface BlueprintsFilter {
    Blueprint apply(Blueprint bp);
}
