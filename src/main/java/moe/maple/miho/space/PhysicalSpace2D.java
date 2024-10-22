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

package moe.maple.miho.space;

import moe.maple.miho.foothold.Foothold;
import moe.maple.miho.line.Line;
import moe.maple.miho.point.Point;
import moe.maple.miho.rect.Rect;
import moe.maple.miho.space.bst.MoeBstFootholdTree;
import moe.maple.miho.space.quad.MoeFootholdQuadTree;
import moe.maple.miho.tree.PointTree;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public interface PhysicalSpace2D extends Iterable<Foothold> {

    PointTree tree();

    Foothold getFootholdUnderneath(int x, int y);

    default Foothold getFootholdUnderneath(Point point) {
        return getFootholdUnderneath(point.x(), point.y());
    }

    default Foothold getFootholdUnderneath(int x, int y, Supplier<Foothold> fallback) {
        var res = getFootholdUnderneath(x, y);
        if (res == null) return fallback.get();
        return res;
    }

    default Foothold getFootholdUnderneath(Point point, Supplier<Foothold> fallback) {
        var res = getFootholdUnderneath(point);
        if (res == null) return fallback.get();
        return res;
    }

    Foothold getFootholdClosest(int x, int y, int minDistance, int maxDistance);

    default Foothold getFootholdClosest(Point point, int minDistance, int maxDistance) {
        return getFootholdClosest(point.x(), point.y(), minDistance, maxDistance);
    }

    default Foothold getFootholdClosest(int x, int y) {
        return getFootholdClosest(x, y, 0, Integer.MAX_VALUE);
    }

    default Foothold getFootholdClosest(Point point) {
        return getFootholdClosest(point.x(), point.y());
    }

    Foothold getFootholdRandom(Rect rect);

    default Foothold getFootholdRandom() {
        return getFootholdRandom(tree().bounds());
    }

    List<Foothold> getFootholdRandom(Rect rect, int max);

    default List<Foothold> getFootholdRandom(int max) {
        return getFootholdRandom(tree().bounds(), max);
    }

    boolean isInBounds(int x, int y);

    default boolean isInBounds(Point point) {
        return isInBounds(point.x(), point.y());
    }

    static PhysicalSpace2D ofBST(Foothold[] footholds) {
        return ofBST(Line.min(footholds), Line.max(footholds), footholds);
    }

    static PhysicalSpace2D ofBST(Collection<Foothold> footholds) {
        return ofBST(Line.min(footholds), Line.max(footholds), footholds);
    }

    static PhysicalSpace2D ofBST(Point low, Point high, Foothold[] footholds) {
        return new MoeBstFootholdTree(low, high, footholds);
    }

    static PhysicalSpace2D ofBST(Point low, Point high, Collection<Foothold> footholds) {
        return new MoeBstFootholdTree(low, high, footholds.toArray(Foothold[]::new));
    }

    static PhysicalSpace2D ofQuad(Point low, Point high, Foothold[] footholds) {
        return new MoeFootholdQuadTree(low, high, footholds);
    }

    static PhysicalSpace2D ofQuad(Point low, Point high, Collection<Foothold> footholds) {
        return ofQuad(low, high, footholds.toArray(Foothold[]::new));
    }

    static PhysicalSpace2D ofQuad(Foothold[] footholds) {
        return ofQuad(Line.min(footholds), Line.max(footholds), footholds);
    }

    static PhysicalSpace2D ofQuad(Collection<Foothold> footholds) {
        return ofQuad(Line.min(footholds), Line.max(footholds), footholds);
    }
}
