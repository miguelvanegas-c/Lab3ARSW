package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UndersamplingFilterTest {

    private final UndersamplingFilter filter = new UndersamplingFilter();

    @Test
    void testFilterKeepsEvenIndexedPoints() {
        Blueprint bp = new Blueprint("john", "test", List.of(
                new Point(0, 0),   // index 0 - keep
                new Point(1, 1),   // index 1 - remove
                new Point(2, 2),   // index 2 - keep
                new Point(3, 3),   // index 3 - remove
                new Point(4, 4)    // index 4 - keep
        ));

        Blueprint filtered = filter.apply(bp);

        assertEquals(3, filtered.getPoints().size());
        assertEquals(0, filtered.getPoints().get(0).getX());
        assertEquals(2, filtered.getPoints().get(1).getX());
        assertEquals(4, filtered.getPoints().get(2).getX());
    }

    @Test
    void testFilterWithTwoPoints() {
        Blueprint bp = new Blueprint("jane", "small", List.of(
                new Point(10, 10),
                new Point(20, 20)
        ));

        Blueprint filtered = filter.apply(bp);

        // With 2 or fewer points, returns unchanged
        assertEquals(2, filtered.getPoints().size());
    }

    @Test
    void testFilterWithOnePoint() {
        Blueprint bp = new Blueprint("alice", "single", List.of(
                new Point(5, 5)
        ));

        Blueprint filtered = filter.apply(bp);

        assertEquals(1, filtered.getPoints().size());
        assertEquals(5, filtered.getPoints().get(0).getX());
    }

    @Test
    void testFilterWithEmptyPoints() {
        Blueprint bp = new Blueprint("empty", "test", List.of());

        Blueprint filtered = filter.apply(bp);

        assertTrue(filtered.getPoints().isEmpty());
    }

    @Test
    void testFilterWithOddNumberOfPoints() {
        Blueprint bp = new Blueprint("bob", "odd", List.of(
                new Point(1, 1),   // index 0 - keep
                new Point(2, 2),   // index 1 - remove
                new Point(3, 3),   // index 2 - keep
                new Point(4, 4),   // index 3 - remove
                new Point(5, 5),   // index 4 - keep
                new Point(6, 6),   // index 5 - remove
                new Point(7, 7)    // index 6 - keep
        ));

        Blueprint filtered = filter.apply(bp);

        assertEquals(4, filtered.getPoints().size());
        assertEquals(1, filtered.getPoints().get(0).getX());
        assertEquals(3, filtered.getPoints().get(1).getX());
        assertEquals(5, filtered.getPoints().get(2).getX());
        assertEquals(7, filtered.getPoints().get(3).getX());
    }

    @Test
    void testFilterWithEvenNumberOfPoints() {
        Blueprint bp = new Blueprint("charlie", "even", List.of(
                new Point(10, 10),  // index 0 - keep
                new Point(20, 20),  // index 1 - remove
                new Point(30, 30),  // index 2 - keep
                new Point(40, 40),  // index 3 - remove
                new Point(50, 50),  // index 4 - keep
                new Point(60, 60)   // index 5 - remove
        ));

        Blueprint filtered = filter.apply(bp);

        assertEquals(3, filtered.getPoints().size());
        assertEquals(10, filtered.getPoints().get(0).getX());
        assertEquals(30, filtered.getPoints().get(1).getX());
        assertEquals(50, filtered.getPoints().get(2).getX());
    }

    @Test
    void testFilterPreservesAuthorAndName() {
        Blueprint bp = new Blueprint("testauthor", "testname", List.of(
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3)
        ));

        Blueprint filtered = filter.apply(bp);

        assertEquals("testauthor", filtered.getAuthor());
        assertEquals("testname", filtered.getName());
    }

    @Test
    void testFilterReducesPointsByHalf() {
        Blueprint bp = new Blueprint("test", "reduction", List.of(
                new Point(0, 0), new Point(1, 1),
                new Point(2, 2), new Point(3, 3),
                new Point(4, 4), new Point(5, 5),
                new Point(6, 6), new Point(7, 7),
                new Point(8, 8), new Point(9, 9)
        ));

        Blueprint filtered = filter.apply(bp);

        // 10 points -> 5 points (indices 0, 2, 4, 6, 8)
        assertEquals(5, filtered.getPoints().size());
    }
}