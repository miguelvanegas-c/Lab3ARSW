package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IdentityFilterTest {

    private final IdentityFilter filter = new IdentityFilter();

    @Test
    void testFilterReturnsUnchangedBlueprint() {
        Blueprint bp = new Blueprint("john", "test", List.of(
                new Point(0, 0),
                new Point(10, 10),
                new Point(20, 20)
        ));

        Blueprint filtered = filter.apply(bp);

        assertNotNull(filtered);
        assertEquals(bp.getAuthor(), filtered.getAuthor());
        assertEquals(bp.getName(), filtered.getName());
        assertEquals(bp.getPoints().size(), filtered.getPoints().size());
    }

    @Test
    void testFilterWithEmptyPoints() {
        Blueprint bp = new Blueprint("jane", "empty", List.of());

        Blueprint filtered = filter.apply(bp);

        assertNotNull(filtered);
        assertTrue(filtered.getPoints().isEmpty());
    }

    @Test
    void testFilterWithSinglePoint() {
        Blueprint bp = new Blueprint("alice", "single", List.of(new Point(5, 5)));

        Blueprint filtered = filter.apply(bp);

        assertNotNull(filtered);
        assertEquals(1, filtered.getPoints().size());
        assertEquals(5, filtered.getPoints().get(0).getX());
        assertEquals(5, filtered.getPoints().get(0).getY());
    }

    @Test
    void testFilterWithDuplicatePoints() {
        Blueprint bp = new Blueprint("bob", "duplicates", List.of(
                new Point(1, 1),
                new Point(1, 1),
                new Point(2, 2)
        ));

        Blueprint filtered = filter.apply(bp);

        // Identity filter should not remove duplicates
        assertNotNull(filtered);
        assertEquals(3, filtered.getPoints().size());
    }

    @Test
    void testFilterReturnsSameInstance() {
        Blueprint bp = new Blueprint("test", "blueprint", List.of(new Point(0, 0)));

        Blueprint filtered = filter.apply(bp);

        // Identity filter returns the same blueprint instance
        assertSame(bp, filtered);
    }
}