package test;

import moe.maple.miho.line.Line;
import moe.maple.miho.line.PackedLine;
import moe.maple.miho.point.PackedPoint;
import moe.maple.miho.point.Point;
import org.junit.jupiter.api.Test;

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
        var found = Point.of(2, 0);
        var line = Line.of(0, 0, 10, 0);
        assert (line.closest(above).equals(found));
        assert (line.closest(below).equals(found));
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

    @Test
    void packedSanity() {
        var line = PackedLine.of(0, 0, 10, 10);
        assert (PackedLine.x1(line) == 0);
        assert (PackedLine.y1(line) == 0);
        assert (PackedLine.x2(line) == 10);
        assert (PackedLine.y2(line) == 10);
        line = PackedLine.x1(line, 10);
        line = PackedLine.y1(line, 10);
        line = PackedLine.x2(line, 20);
        line = PackedLine.y2(line, 20);
        assert (PackedLine.x1(line) == 10);
        assert (PackedLine.y1(line) == 10);
        assert (PackedLine.x2(line) == 20);
        assert (PackedLine.y2(line) == 20);
    }

    @Test
    void packedAboveAndBelow() {
        var above = Point.of(2, 2);
        var below = Point.of(2, -2);
        var line = PackedLine.of(0, 0, 10, 0);
        assert (PackedLine.below(line, above));
        assert (PackedLine.above(line, below));
    }

    @Test
    void packedContains() {
        var contained = Point.of(1, 0);
        var notcontained = Point.of(1, 1);
        var line = PackedLine.of(0, 0, 10, 0);
        assert (PackedLine.contains(line, contained));
        assert (!PackedLine.contains(line, notcontained));
    }

    @Test
    void packedClosest() {
        var above = Point.of(2, 2);
        var below = Point.of(2, -2);
        var found = PackedPoint.of(2, 0);
        var line = PackedLine.of(0, 0, 10, 0);
        assert (PackedLine.closest(line, above) == found);
        assert (PackedLine.closest(line, below) == found);
    }

    @Test
    void packedCompareX() {
        var left = Point.of(-2, 0);
        var right = Point.of(12, 0);
        var on = Point.of(5, 0);
        var line = PackedLine.of(0, 0, 10, 0);
        assert (PackedLine.compareX(line, left) == -1);
        assert (PackedLine.compareX(line, right) == 1);
        assert (PackedLine.compareX(line, on) == 0);
    }

    @Test
    void packedCompareY() {
        var above = Point.of(0, 2);
        var below = Point.of(0, -2);
        var on = Point.of(0, 0);
        var line = PackedLine.of(0, 0, 10, 0);
        assert (PackedLine.compareY(line, above) == 1);
        assert (PackedLine.compareY(line, below) == -1);
        assert (PackedLine.compareY(line, on) == 0);
    }
}