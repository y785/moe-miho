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

package moe.maple.miho.line;

import moe.maple.miho.point.Point;
import moe.maple.miho.point.PointConsumer;
import moe.maple.miho.rect.Rect;

public interface Line {
    int x1();

    int y1();

    int x2();

    int y2();

    long j1();

    long j2();

    Point start();

    Point end();

    boolean above(int x, int y);

    default boolean above(Point point) {
        return above(point.x(), point.y());
    }

    boolean below(int x, int y);

    default boolean below(Point point) {
        return below(point.x(), point.y());
    }

    boolean contains(int x, int y);

    default boolean contains(Point point) {
        return contains(point.x(), point.y());
    }

    long closest(int x, int y);

    default long closest(Point point) {
        return closest(point.x(), point.y());
    }

    default long closest(long joined) {
        return closest(Point.x(joined), Point.y(joined));
    }

    default Point closestP(Point point) {
        return Point.of(closest(point));
    }

    int compareX(int x);

    default int compareX(Point o) {
        return compareX(o.x());
    }

    default int compareX(long joined) {
        return compareX(Point.x(joined));
    }

    int compareX(Line o);

    int compareY(int y);

    default int compareY(Point o) {
        return compareY(o.y());
    }

    default int compareY(long joined) {
        return compareY(Point.y(joined));
    }

    int compareY(Line o);

    int cross(int x, int y);

    default int cross(Point p) {
        return cross(p.x(), p.y());
    }

    int distance(int x, int y);

    default int distance(Point point) {
        return distance(point.x(), point.y());
    }

    boolean intersects(Line line);

    default boolean intersects(Rect rect) {
        return rect.intersects(this);
    }

    void path(PointConsumer action);

    static int compX(int x1, int x2, int x) {
        if (x1 > x && x2 > x) return -1;
        if (x1 < x && x2 < x) return 1;
        return 0;
    }

    static int compY(int y1, int y2, int y) {
        if (y1 > y && y2 > y) return -1;
        if (y1 < y && y2 < y) return 1;
        return 0;
    }


    static void plot(int x1, int y1, int x2, int y2, PointConsumer action) {
        var dx = x2 - x1;
        var dy = y2 - y1;
        var dst = (float) Math.sqrt(dx * dx + dy * dy);
        var ix = (float) (dx * 1.0 / (dst - 1));
        var iy = (float) (dy * 1.0 / (dst - 1));
        float tx = x1;
        float ty = y1;
        for (int i = 1; i <= dst; i++) {
            action.accept(Math.round(tx), Math.round(ty));
            tx = x1 + (ix * i);
            ty = y1 + (iy * i);
        }
    }

    static Line of() {
        return new ImmutableLine();
    }

    static Line of(Line other) {
        return of(other.start(), other.end());
    }

    static Line of(Point start, Point end) {
        return new ImmutableLine(start, end);
    }

    static Line of(int x1, int y1, int x2, int y2) {
        return new ImmutableLine(x1, y1, x2, y2);
    }

    static Line ofTop(Rect rect) {
        var x = rect.x();
        var y = rect.y();
        var h = rect.height();
        return of(x, y + h, x + rect.width(), y + h);
    }

    static Line ofBottom(Rect rect) {
        var x = rect.x();
        var y = rect.y();
        return of(x, y, x + rect.width(), y);
    }

    static Line ofLeft(Rect rect) {
        var x = rect.x();
        var y = rect.y();
        return of(x, y, x, y + rect.height());
    }

    static Line ofRight(Rect rect) {
        var x = rect.x();
        var y = rect.y();
        var w = rect.width();
        return of(x + w, y, x + w, y + rect.height());
    }
}
