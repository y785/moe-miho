package test;

import moe.maple.miho.line.Line;
import moe.maple.miho.point.Point;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LineTests {

    @Test
    void aboveAndBelow() {
        var above = Point.of(2, 2);
        var below = Point.of(2, -2);
        var line = Line.of(0, 0, 10, 0);
        assert(above.above(line) && line.below(above));
        assert(below.below(line) && line.above(below));
    }

    @Test
    void contains() {
        var contained = Point.of(1, 0);
        var notcontained = Point.of(1, 1);
        var line = Line.of(0, 0, 10, 0);
        assert(line.contains(contained));
        assert(!line.contains(notcontained));
    }

    @Test
    void closest() {
        var above = Point.of(2, 2);
        var below = Point.of(2, -2);
        var found = Point.of(2, 0).joined();
        var line = Line.of(0, 0, 10, 0);
        assert(line.closest(above) == found);
        assert(line.closest(below) == found);
    }

    @Test
    void compareX() {
        var left = Point.of(-2, 0);
        var right = Point.of(12, 0);
        var on = Point.of(5, 0);
        var line = Line.of(0, 0, 10, 0);
        assert(line.compareX(left) == -1);
        assert(line.compareX(right) == 1);
        assert(line.compareX(on) == 0);
    }

    @Test
    void compareY() {
        var above = Point.of(0, 2);
        var below = Point.of(0, -2);
        var on = Point.of(0, 0);
        var line = Line.of(0, 0, 10, 0);
        assert(line.compareY(above) == 1);
        assert(line.compareY(below) == -1);
        assert(line.compareY(on) == 0);
    }
}