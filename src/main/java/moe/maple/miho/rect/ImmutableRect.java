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

public class ImmutableRect implements Rect {

    private static final int INSIDE = 0;
    private static final int OUT_LEFT = 1;
    private static final int OUT_TOP = 2;
    private static final int OUT_RIGHT = 4;
    private static final int OUT_BOTTOM = 8;

    protected int x, y;
    protected int w, h;

    public ImmutableRect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public long j() {
        return Point.joined(x, y);
    }

    @Override
    public int cx() {
        return x + w / 2;
    }

    @Override
    public int cy() {
        return y + h / 2;
    }

    @Override
    public long cj() {
        return Point.joined(cx(), cy());
    }

    @Override
    public int width() {
        return w;
    }

    @Override
    public int height() {
        return h;
    }

    @Override
    public float angle(int x, int y) {
        return Point.angle(x + w / 2, y + h / 2, x, y);
    }

    @Override
    public int compareX(int x) {
        return Line.compX(this.x, this.x + w, x);
    }

    @Override
    public int compareY(int y) {
        return Line.compY(this.y, this.y + h, y);
    }

    @Override
    public boolean contains(int tx, int ty, int radius) {
        // todo fix
        return (tx >= x || tx - radius >= x || tx + radius >= x)
                && (ty >= y || ty - radius >= y || ty + radius >= y)
                && (tx < x + w || tx - radius < x + w || tx + radius < x + w)
                && (ty < y + h || ty - radius < y + h || ty + radius < y + h);
    }

    @Override
    public boolean contains(int tx, int ty) {
        return tx >= x
                && ty >= y
                && tx < x + w
                && ty < y + h;
    }

    @Override
    public boolean intersects(Rect rect) {
        if (w <= 0 || h <= 0)
            return false;
        var tx = rect.x();
        var ty = rect.y();
        var tw = rect.width();
        var th = rect.height();
        return tx + tw > x && ty + th > y && tx < x + w && y < ty + h;
    }

    /**
     * Line clipping: Cohen–Sutherland
     */
    @Override
    public boolean intersects(int x1, int y1, int x2, int y2) {
        var ymin = y;
        var ymax = ymin + h;
        var xmin = x;
        var xmax = xmin + w;
        var outcode0 = outcode(x1, y1);
        var outcode1 = outcode(x2, y2);

        while (true) {
            if ((outcode0 | outcode1) == 0) {
                return true;
            } else if ((outcode0 & outcode1) != 0) {
                return false;
            } else {
                int x, y;
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
                    outcode0 = outcode(x1, y2);
                } else {
                    x2 = x;
                    y2 = y;
                    outcode1 = outcode(x2, y2);
                }
            }
        }
    }

    protected int outcode(int x, int y) {
        var rx = x();
        var ry = y();
        var w = width();
        var h = height();
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

    @Override
    public String toString() {
        return String.format("{ \"x\": %d, \"y\": %d, \"h\": %d, \"w\": %d }", x, y, h, w);
    }
}
