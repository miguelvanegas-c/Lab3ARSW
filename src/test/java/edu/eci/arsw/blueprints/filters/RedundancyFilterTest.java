package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.entity.Blueprint;
import edu.eci.arsw.blueprints.model.entity.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RedundancyFilterTest {

    private final RedundancyFilter filter = new RedundancyFilter();

    @Test
    void testFilterRemovesDuplicateConsecutivePoints() {
        Blueprint bp = new Blueprint("john", "test", List.of(
                new Point(0, 0),
                new Point(0, 0),
                new Point(10, 10),
                new Point(10, 10),
                new Point(20, 20)
        ));

        Blueprint filtered = filter.apply(bp);

        assertEquals(3, filtered.getPoints().size());
        assertEquals(0, filtered.getPoints().get(0).getX());
        assertEquals(10, filtered.getPoints().get(1).getX());
        assertEquals(20, filtered.getPoints().get(2).getX());
    }

    @Test
    void testFilterWithNoDuplicates() {
        Blueprint bp = new Blueprint("jane", "unique", List.of(
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3)
        ));

        Blueprint filtered = filter.apply(bp);

        assertEquals(3, filtered.getPoints().size());
    }

    @Test
    void testFilterWithEmptyPoints() {
        Blueprint bp = new Blueprint("empty", "test", List.of());

        Blueprint filtered = filter.apply(bp);

        assertTrue(filtered.getPoints().isEmpty());
    }

    @Test
    void testFilterWithAllDuplicates() {
        Blueprint bp = new Blueprint("duplicates", "test", List.of(
                new Point(5, 5),
                new Point(5, 5),
                new Point(5, 5),
                new Point(5, 5)
        ));

        Blueprint filtered = filter.apply(bp);

        assertEquals(1, filtered.getPoints().size());
        assertEquals(5, filtered.getPoints().get(0).getX());
        assertEquals(5, filtered.getPoints().get(0).getY());
    }

    @Test
    void testFilterPreservesNonConsecutiveDuplicates() {
        Blueprint bp = new Blueprint("nonconsecutive", "test", List.of(
                new Point(1, 1),
                new Point(2, 2),
                new Point(1, 1)
        ));

        Blueprint filtered = filter.apply(bp);

        assertEquals(3, filtered.getPoints().size());
    }
}