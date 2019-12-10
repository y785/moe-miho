/*
 * Copyright (C) 2019, y785, http://github.com/y785
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package test;

import benchmark.maps.MuLung;
import benchmark.odin.MapleFootholdTree;
import moe.maple.miho.line.Line;
import moe.maple.miho.point.Point;
import moe.maple.miho.space.PhysicalSpace2D;
import moe.maple.miho.space.bst.MoeBstFootholdTree;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public final class SpaceMuLungTests {

    private static PhysicalSpace2D quadtree;
    private static PhysicalSpace2D bst;
    private static MapleFootholdTree mapletree;

    @BeforeAll
    public static void setup() {
        var low = Line.min(MuLung.FOOTHOLDS);  // Bottom left
        var high = Line.max(MuLung.FOOTHOLDS); // Top right

        quadtree = PhysicalSpace2D.ofQuad(MuLung.FOOTHOLDS);
        mapletree = new MapleFootholdTree(low, high, MuLung.FOOTHOLDS);
        mapletree.check(mapletree);

        bst = new MoeBstFootholdTree(low, high, MuLung.FOOTHOLDS);
    }

    public static void fhCheck(PhysicalSpace2D space, Point point, int targetId) {
        var fh = space.getFootholdUnderneath(point);
        assert (fh != null);
        assert (fh.id() == targetId);
    }

    @Test
    public void testBSTClosest() {
        var res = bst.getFootholdClosest(39, 51);
        assert (res != null);
        assert (res.id() == 3);
        res = bst.getFootholdClosest(39, 51 - 100);
        assert (res != null);
        assert (res.id() == 3);
        res = bst.getFootholdClosest(39, 51 - 100, 0, 200);
        assert (res != null);
        assert (res.id() == 3);
        res = bst.getFootholdClosest(39, 51 - 100, 0, 100);
        assert (res == null);

        res = bst.getFootholdClosest(542, 51);
        assert (res != null);
        assert (res.id() == 9);

        res = bst.getFootholdClosest(717, -81);
        assert (res != null);
        assert (res.id() == 19);
    }

    @Test
    public void testQuadHorizontal() {
        fhCheck(quadtree, Point.of(39, 51), 3);
        fhCheck(quadtree, Point.of(542, 51), 9);
    }

    @Test
    public void testBSTHorizontal() {
        fhCheck(bst, Point.of(39, 51), 3);
        fhCheck(bst, Point.of(542, 51), 9);

        fhCheck(bst, Point.of(717, -81), 19);
    }

    @Test
    public void testBSTVertical() {
        fhCheck(bst, Point.of(39, 51 - 100), 3);
        fhCheck(bst, Point.of(542, 51 - 100), 9);
        fhCheck(bst, Point.of(717, -81 - 100), 19);

        fhCheck(bst, Point.of(39, 51 - 500), 3);
        fhCheck(bst, Point.of(542, 51 - 500), 9);
        fhCheck(bst, Point.of(717, -81 - 500), 19);

        fhCheck(bst, Point.of(39, 51 - 1000), 3);
        fhCheck(bst, Point.of(542, 51 - 1000), 9);
        fhCheck(bst, Point.of(717, -81 - 1000), 19);
    }
}
