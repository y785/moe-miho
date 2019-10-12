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

public interface MutableRect extends Rect {
    void x(int x);

    void y(int y);

    void width(int width);

    void height(int height);

    MutableRect copy();

    default void bounds(int x, int y, int w, int h) {
        if (w < 0)
            throw new IllegalArgumentException("Invalid rect width: " + w);
        if (h < 0)
            throw new IllegalArgumentException("Invalid rect height: " + h);

        x(x);
        y(y);
        width(w);
        height(h);
    }

    default void center(int x, int y) {
        bounds(x - width() / 2, y - height() / 2, width(), height());
    }

    default void center(Point point) {
        center(point.x(), point.y());
    }

    default void grow(int amountToGrow) {
        var cx = x() + width() / 2;
        var cy = y() + height() / 2;
        width(width() + amountToGrow);
        height(height() + amountToGrow);
        center(cx, cy);
    }

    default void union(Rect other) {
        var x1 = Math.min(x(), other.x());
        var x2 = Math.max(x() + width(), other.x() + other.width());
        var y1 = Math.min(y(), other.y());
        var y2 = Math.max(y() + height(), other.y() + other.height());
        bounds(x1, y1, x2 - x1, y2 - y1);
    }

    default void union(int x, int y) {
        var x1 = Math.min(x(), x);
        var x2 = Math.max(x() + width(), x);
        var y1 = Math.min(y(), y);
        var y2 = Math.max(y() + height(), y);
        bounds(x1, y1, x2 - x1, y2 - y1);
    }

    default void union(Point point) {
        union(point.x(), point.y());
    }

    default void union(Line line) {
        union(line.start());
        union(line.end());
    }

    static MutableRect of() {
        return new MoeRect(0, 0, 1, 1);
    }

    static MutableRect of(Rect other) {
        return of(other.x(), other.y(), other.width(), other.height());
    }

    static MutableRect of(int x, int y, int width, int height) {
        return new MoeRect(x, y, width, height);
    }

    static MutableRect of(Point a, Point b) {
        var min = Point.min(a, b);
        var max = Point.max(a, b);
        return of(min.x() - 1, min.y() - 1,
                Math.abs(min.x() - max.x()) + 2,
                Math.abs(min.y() - max.y()) + 2);
    }

    static MutableRect of(Line line) {
        return of(line.start(), line.end());
    }
}
