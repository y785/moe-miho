package test;

import benchmark.maps.Ellinia;
import benchmark.odin.MapleFootholdTree;
import moe.maple.miho.line.Line;
import moe.maple.miho.point.Point;
import moe.maple.miho.space.PhysicalSpace2D;
import moe.maple.miho.space.bst.MoeBstFootholdTree;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SpaceElliniaTests {

    private static PhysicalSpace2D quadtree;
    private static PhysicalSpace2D bst;
    private static MapleFootholdTree mapletree;

    @BeforeAll
    public static void setup() {
        var low = Line.min(Ellinia.FOOTHOLDS);  // Bottom left
        var high = Line.max(Ellinia.FOOTHOLDS); // Top right

        quadtree = PhysicalSpace2D.ofQuad(Ellinia.FOOTHOLDS);
        mapletree = new MapleFootholdTree(low, high, Ellinia.FOOTHOLDS);
        mapletree.check(mapletree);

        bst = new MoeBstFootholdTree(low, high, Ellinia.FOOTHOLDS);
    }

    public static void fhCheck(PhysicalSpace2D space, Point point, int targetId) {
        var fh = space.getFootholdUnderneath(point);
        assert(fh != null);
        assert(fh.id() == targetId);
    }

    @Test
    public void testBSTClosest() {
        var res = bst.getFootholdClosest(738, -3063);
        assert (res != null);
        assert (res.id() == 725);

        res = bst.getFootholdClosest(767, 357);
        assert (res != null);
        assert (res.id() == 66);

        res = bst.getFootholdClosest(831, -4432);
        assert (res != null);
        assert (res.id() == 1104);
    }

    @Test
    public void testBSTRandom() {
        var res = bst.getFootholdRandom(100);
        assert (res != null);
        assert (res.size() == 100);

        var single = bst.getFootholdRandom();
        assert (single != null);
    }

    @Test
    public void testQuadHorizontal() {
        fhCheck(quadtree, Point.of(428, -3530), 1175);
        fhCheck(quadtree, Point.of(428, -3607), 1175);
        fhCheck(quadtree, Point.of(738, -3063 - 90), 725);
        fhCheck(quadtree, Point.of(-156, -1937 - 90), 1180);
        fhCheck(quadtree, Point.of(1492, 311 - 90), 868);

        // For benchmark sanity
        var fh = mapletree.findBelow(Point.of(428, -3530));
        assert (fh != null);
        assert (fh.id() == 1175);
    }

    @Test
    public void testBSTHorizontal() {
        fhCheck(bst, Point.of(428, -3530), 1175);
        fhCheck(bst, Point.of(428, -3607), 1175);
        fhCheck(bst, Point.of(738, -3063 - 90), 725);
        fhCheck(bst, Point.of(-156, -1937 - 90), 1180);
        fhCheck(bst, Point.of(1492, 311 - 90), 868);
    }

    @Test
    public void testQuadSlopes() {
        fhCheck(quadtree, Point.of(1064, 186 - 90), 979);
        fhCheck(quadtree, Point.of(583, 144 - 90), 190);
        fhCheck(quadtree, Point.of(-344, -84 - 90), 123);
        fhCheck(quadtree, Point.of(541, -2973 - 90), 688);
        // For benchmark sanity
        var fh = mapletree.findBelow(Point.of(1064, 186 - 90));
        assert (fh != null);
        assert (fh.id() == 979);
    }

    @Test
    public void testBSTSlopes() {
        fhCheck(bst, Point.of(1064, 186 - 90), 979);
        fhCheck(bst, Point.of(583, 144 - 90), 190);
        fhCheck(bst, Point.of(-344, -84 - 90), 123);
        fhCheck(bst, Point.of(541, -2973 - 90), 688);
    }
}
