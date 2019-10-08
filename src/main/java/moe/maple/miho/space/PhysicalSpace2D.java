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

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public interface PhysicalSpace2D extends Iterable<Foothold> {
    int hx();
    int hy();
    int lx();
    int ly();
    int cx();
    int cy();

    long hj();
    long lj();
    long cj();

    Point high();

    Point low();

    Point center();

    Rect bounds();

    int width();

    int height();

    Foothold getFootholdUnderneath(int x, int y);

    default Foothold getFootholdUnderneath(Point point) {
        return getFootholdUnderneath(point.x(), point.y());
    }

    Foothold getFootholdUnderneath(int x, int y, Supplier<Foothold> fallback);

    Foothold getFootholdClosest(int x, int y, int pcx, int pcy, int ptHitx);

    Foothold getFootholdRandom(Rect rect);

    List<Foothold> getFootholdRandom(Rect rect, int max);

    boolean isInBounds(int x, int y);

    default boolean isInBounds(long joined) {
        return isInBounds(Point.x(joined), Point.y(joined));
    }

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
