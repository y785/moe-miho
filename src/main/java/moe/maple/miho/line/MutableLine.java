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
import moe.maple.miho.rect.Rect;

public interface MutableLine extends Line {
    void x1(int x1);

    void y1(int y1);

    void x2(int x2);

    void y2(int y2);

    static MutableLine of() {
        return new MoeLine();
    }

    static MutableLine of(Line other) {
        return of(other.start(), other.end());
    }

    static MutableLine of(Point start, Point end) {
        return new MoeLine(start, end);
    }

    static MutableLine of(int x1, int y1, int x2, int y2) {
        return new MoeLine(x1, y1, x2, y2);
    }

    static MutableLine ofTop(Rect rect) {
        var x = rect.x();
        var y = rect.y();
        var h = rect.height();
        return of(x, y + h, x + rect.width(), y + h);
    }

    static MutableLine ofBottom(Rect rect) {
        var x = rect.x();
        var y = rect.y();
        return of(x, y, x + rect.width(), y);
    }

    static MutableLine ofLeft(Rect rect) {
        var x = rect.x();
        var y = rect.y();
        return of(x, y, x, y + rect.height());
    }

    static MutableLine ofRight(Rect rect) {
        var x = rect.x();
        var y = rect.y();
        var w = rect.width();
        return of(x + w, y, x + w, y + rect.height());
    }
}
