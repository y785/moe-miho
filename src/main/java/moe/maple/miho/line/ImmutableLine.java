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

public class ImmutableLine implements Line {

    protected int x1, y1;
    protected int x2, y2;

    public ImmutableLine() {
    }

    public ImmutableLine(Point start, Point end) {
        this.x1 = start.x();
        this.y1 = start.y();
        this.x2 = end.x();
        this.y2 = end.x();
    }

    public ImmutableLine(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public int x1() {
        return x1;
    }

    @Override
    public int y1() {
        return y1;
    }

    @Override
    public int x2() {
        return x2;
    }

    @Override
    public int y2() {
        return y2;
    }

    @Override
    public long j1() {
        return Point.joined(x1, y1);
    }

    @Override
    public long j2() {
        return Point.joined(x2, y2);
    }

    @Override
    public Point start() {
        return Point.of(x1, y1);
    }

    @Override
    public Point end() {
        return Point.of(x2, y2);
    }

    @Override
    public boolean above(int x, int y) {
        return cross(x, y) > 0;
    }

    @Override
    public boolean below(int x, int y) {
        return cross(x, y) < 0;
    }

    @Override
    public boolean contains(int x, int y) {
        if (x1 == x) return x2 == x;
        if (y1 == y) return y2 == y;
        return (x1 - x) * (y1 - y) == (x - x2) * (y - y2);
    }

    @Override
    public long closest(int x, int y) {
        var pdx = x - x1;
        var pdy = y - y1;
        var dx = x2 - x1;
        var dy = y2 - y1;

        var sqr = (float) Point.squared(dx, dy);
        var dot = (float) Point.dot(pdx, pdy, dx, dy);
        var dst = dot / sqr;

        if (dst < 0) return Point.joined(x1, y1);
        if (dst > 1) return Point.joined(x2, y2);

        var rx = (int) (x1 + dx * dst);
        var ry = (int) (y1 + dy * dst);
        return Point.joined(rx, ry);
    }

    @Override
    public int compareX(int x) {
        return Line.compX(x1, x2, x);
    }

    @Override
    public int compareX(Line o) {
        if (x2 < o.x1()) return -1;
        if (x1 > o.x2()) return 1;
        return 0;
    }

    @Override
    public int compareY(int y) {
        return Line.compY(y1, y2, y);
    }

    @Override
    public int compareY(Line o) {
        if (y2 < o.y1()) return -1;
        if (y1 > o.y2()) return 1;
        return 0;
    }

    @Override
    public Line copy() {
        return new ImmutableLine(x1, y1, x2, y2);
    }

    @Override
    public int cross(int x, int y) {
        return (x - x1) * (y2 - y1) - (y - y1) * (x2 - x1);
    }

    @Override
    public int distance(int x, int y) {
        var clst = closest(x, y);
        var dstx = (int) (clst >> 32);
        var dsty = (int) clst;
        return Point.distance(x, y, dstx, dsty);
    }

    @Override
    public boolean intersects(Line line) {
        return false; // todo
    }

    @Override
    public void path(PointConsumer action) {
        Line.plot(x1, y1, x2, y2, action);
    }
}
