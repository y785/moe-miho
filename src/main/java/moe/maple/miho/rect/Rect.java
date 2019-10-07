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

package moe.maple.miho.rect;

import moe.maple.miho.line.Line;
import moe.maple.miho.point.Point;

/**
 * Modelled after {@link java.awt.geom.Rectangle2D}
 */
public interface Rect {

    int x();

    int y();

    long j();

    int cx();

    int cy();

    long cj();

    int width();

    int height();

    float angle(int x, int y);

    default float angle(Point point) {
        return angle(point.x(), point.y());
    }

    int compareX(int x);

    default int compareX(Point o) {
        return compareX(o.x());
    }

    default int compareX(long joined) {
        return compareX(Point.x(joined));
    }

    int compareY(int y);

    default int compareY(Point o) {
        return compareY(o.y());
    }

    default int compareY(long joined) {
        return compareY(Point.y(joined));
    }

    boolean contains(int x, int y);

    boolean contains(int x, int y, int radius);

    default boolean contains(long joined) {
        return contains(Point.x(joined), Point.y(joined));
    }

    default boolean contains(Point point) {
        return contains(point.x(), point.y());
    }

    default boolean contains(Line line) {
        return contains(line.start()) && contains(line.end());
    }

    boolean intersects(Rect rect);

    boolean intersects(int x1, int y1, int x2, int y2);

    default boolean intersects(Line line) {
        return intersects(line.x1(), line.y1(), line.x2(), line.y2());
    }

    static Rect of() {
        return new ImmutableRect(0, 0, 1, 1);
    }

    static Rect of(Rect other) {
        return of(other.x(), other.y(), other.width(), other.height());
    }

    static Rect of(int x, int y, int width, int height) {
        return new ImmutableRect(x, y, width, height);
    }
}
