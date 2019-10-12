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
    public int cx() {
        return x + w / 2;
    }

    @Override
    public int cy() {
        return y + h / 2;
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
    public Rect copy() {
        return new ImmutableRect(x, y, w, h);
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

    @Override
    public String toString() {
        return String.format("{ \"x\": %d, \"y\": %d, \"h\": %d, \"w\": %d }", x, y, h, w);
    }
}
