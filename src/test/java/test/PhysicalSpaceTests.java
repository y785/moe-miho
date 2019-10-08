package test;

import benchmark.Ellinia;
import benchmark.odin.MapleFootholdTree;
import moe.maple.miho.point.Point;
import moe.maple.miho.space.PhysicalSpace2D;
import moe.maple.miho.space.bst.MoeBstFootholdTree;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PhysicalSpaceTests {

    private static PhysicalSpace2D quadtree;
    private static PhysicalSpace2D bst;
    private static MapleFootholdTree mapletree;

    @BeforeAll
    public static void setup() {
        int lx, ly, hx, hy;
        lx = ly = hx = hy = 0;
        for (var object : Ellinia.FOOTHOLDS) {
            if (lx > object.x1()) lx = object.x1();
            if (hx < object.x1()) hx = object.x1();
            if (ly > object.y1()) ly = object.y1();
            if (hy < object.y1()) hy = object.y1();

            if (lx > object.x2()) lx = object.x2();
            if (hx < object.x2()) hx = object.x2();
            if (ly > object.y2()) ly = object.y2();
            if (hy < object.y2()) hy = object.y2();
        }
        var low = Point.of(lx, ly);  // Bottom left
        var high = Point.of(hx, hy); // Top right

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
    public void testHorizontal() {
        fhCheck(quadtree, Point.of(428, -3530), 1175);
        fhCheck(quadtree, Point.of(428, -3607), 1175);
        fhCheck(quadtree, Point.of(738, -3063 - 90), 725);
        fhCheck(quadtree, Point.of(-156, -1937 - 90), 1180);
        fhCheck(quadtree, Point.of(1492, 311 - 90), 868);

        fhCheck(bst, Point.of(428, -3530), 1175);
        fhCheck(bst, Point.of(428, -3607), 1175);
        fhCheck(bst, Point.of(738, -3063 - 90), 725);
        fhCheck(bst, Point.of(-156, -1937 - 90), 1180);
        fhCheck(bst, Point.of(1492, 311 - 90), 868);

        // For benchmark sanity
        var fh = mapletree.findBelow(Point.of(428, -3530));
        assert(fh != null);
        assert(fh.id() == 1175);
    }

    @Test
    public void testSlopes() {
        fhCheck(quadtree, Point.of(1064, 186 - 90), 979);
        fhCheck(quadtree, Point.of(583, 144 - 90), 190);
        fhCheck(quadtree, Point.of(-344, -84 - 90), 123);
        fhCheck(quadtree, Point.of(541, -2973 - 90), 688);

        fhCheck(bst, Point.of(1064, 186 - 90), 979);
        fhCheck(bst, Point.of(583, 144 - 90), 190);
        fhCheck(bst, Point.of(-344, -84 - 90), 123);
        fhCheck(bst, Point.of(541, -2973 - 90), 688);

        // For benchmark sanity
        var fh = mapletree.findBelow(Point.of(1064, 186 - 90));
        assert(fh != null);
        assert(fh.id() == 979);
    }
}
