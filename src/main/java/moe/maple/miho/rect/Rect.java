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

    int INSIDE = 0;
    int OUT_LEFT = 1;
    int OUT_TOP = 2;
    int OUT_RIGHT = 4;
    int OUT_BOTTOM = 8;

    int x();

    int y();

    int cx();

    int cy();

    int width();

    int height();

    Rect copy();

    float angle(int x, int y);

    default float angle(Point point) {
        return angle(point.x(), point.y());
    }

    /**
     * Does the param x lie within the x bounds of this rect?
     */
    int compareX(int x);

    default int compareX(Point o) {
        return compareX(o.x());
    }

    /**
     * Does the param y lie within the y bounds of this rect?
     */
    int compareY(int y);

    default int compareY(Point o) {
        return compareY(o.y());
    }

    boolean contains(int x, int y);

    boolean contains(int x, int y, int radius);

    default boolean contains(Point point) {
        return contains(point.x(), point.y());
    }

    default boolean contains(Line line) {
        return contains(line.start()) && contains(line.end());
    }

    boolean intersects(Rect rect);

    default boolean intersects(int x1, int y1, int x2, int y2) {
        return intersects(x(), y(), width(), height(), x1, y1, x2, y2);
    }

    default boolean intersects(Line line) {
        return intersects(line.x1(), line.y1(), line.x2(), line.y2());
    }

    /**
     * Line clipping: Cohen–Sutherland
     */
    static boolean intersects(int x, int y, int w, int h, int x1, int y1, int x2, int y2) {
        var ymin = y;
        var ymax = ymin + h;
        var xmin = x;
        var xmax = xmin + w;
        var outcode0 = outcode(xmin, ymin, w, h, x1, y1);
        var outcode1 = outcode(xmin, ymin, w, h, x2, y2);

        while (true) {
            if ((outcode0 | outcode1) == 0) {
                return true;
            } else if ((outcode0 & outcode1) != 0) {
                return false;
            } else {
                x = y = 0;
                var out = outcode0 == INSIDE ? outcode1 : outcode0;
                if ((out & OUT_TOP) != 0) {
                    x = x1 + (x2 - x1) * (ymax - y1) / (y2 - y1);
                    y = ymax;
                } else if ((out & OUT_BOTTOM) != 0) {
                    x = x1 + (x2 - x1) * (ymin - y1) / (y2 - y1);
                    y = ymin;
                } else if ((out & OUT_RIGHT) != 0) {
                    y = y1 + (y2 - y1) * (xmax - x1) / (x2 - x1);
                    x = xmax;
                } else if ((out & OUT_LEFT) != 0) {
                    y = y1 + (y2 - y1) * (xmin - x1) / (x2 - x1);
                    x = xmax;
                }
                if (out == outcode0) {
                    x1 = x;
                    y1 = y;
                    outcode0 = outcode(xmin, ymin, w, h, x1, y2);
                } else {
                    x2 = x;
                    y2 = y;
                    outcode1 = outcode(xmin, ymin, w, h, x2, y2);
                }
            }
        }
    }

    static int outcode(int rx, int ry, int w, int h, int x, int y) {
        var out = INSIDE;
        if (x < rx)
            out |= OUT_LEFT;
        else if (x > rx + w)
            out |= OUT_RIGHT;
        else if (w <= 0)
            out |= OUT_LEFT | OUT_RIGHT;
        if (y < ry)
            out |= OUT_BOTTOM;
        else if (y > ry + h)
            out |= OUT_TOP;
        else if (h <= 0)
            out |= OUT_TOP | OUT_BOTTOM;
        return out;
    }

    static Rect of() {
        return new ImmutableRect(0, 0, 1, 1);
    }

    static Rect of(Rect other) {
        return of(other.x(), other.y(), other.width(), other.height());
    }

    static Rect of(Point a, Point b) {
        var min = Point.min(a, b);
        var max = Point.max(a, b);
        return of(min.x() - 1, min.y() - 1,
                Math.abs(min.x() - max.x()) + 2,
                Math.abs(min.y() - max.y()) + 2);
    }

    static Rect of(int x, int y, int width, int height) {
        return new ImmutableRect(x, y, width, height);
    }
}
