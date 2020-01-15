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

import benchmark.maps.HHG2;
import moe.maple.miho.line.Line;
import moe.maple.miho.point.Point;
import moe.maple.miho.space.PhysicalSpace2D;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SpaceHHG2Tests {

    private static PhysicalSpace2D bst;

    @BeforeAll
    public static void setup() {
        var low = Line.min(HHG2.FOOTHOLDS);
        var high = Line.max(HHG2.FOOTHOLDS);

        bst = PhysicalSpace2D.ofBST(low, high, HHG2.FOOTHOLDS);
    }

    public static void fhCheckNotNull(PhysicalSpace2D space, Point point) {
        var fh = space.getFootholdUnderneath(point);
        assert (fh != null);
    }

    @Test
    public void testHorizontal() {
        fhCheckNotNull(bst, Point.of(621, -1177));
        fhCheckNotNull(bst, Point.of(891, -1065));
        fhCheckNotNull(bst, Point.of(802, -1065));
        fhCheckNotNull(bst, Point.of(713, -1065));
        fhCheckNotNull(bst, Point.of(652, -1065));
        fhCheckNotNull(bst, Point.of(739, -1065));
    }
}
