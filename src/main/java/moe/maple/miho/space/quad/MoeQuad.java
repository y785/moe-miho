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

package moe.maple.miho.space.quad;

import moe.maple.miho.foothold.Foothold;
import moe.maple.miho.point.Point;
import moe.maple.miho.tree.quad.AbstractQuad;

import java.util.HashSet;

/* More memory, faster resolves. */
public class MoeQuad extends AbstractQuad<Foothold> {

    private final static int MAX_DEPTH = 3;

    private int flx, fly, fhx, fhy;

    public MoeQuad(int depth, Point low, Point high) {
        super(depth, low, high);
        flx = low.x();
        fly = low.y();
        fhx = high.x();
        fhy = high.y();
    }

    @Override
    public int getQuadrant(Foothold foothold) {
        var x1 = foothold.x1();
        var x2 = foothold.x2();
        var y1 = foothold.y1();
        var y2 = foothold.y2();
        if (x2 <= cx && y2 <= cy) return QUAD_NW;
        if (x1 > cx && y2 <= cy) return QUAD_NE;
        if (x2 <= cx && y1 >= cy) return QUAD_SW;
        return QUAD_SE;
    }

    @Override
    protected boolean accept(Foothold fh) {
        return depth == MAX_DEPTH || (
                fh.x1() >= lx &&
                        fh.x2() <= hy &&
                        fh.y1() >= ly &&
                        fh.y2() <= hy
        );
    }

    @Override
    protected boolean contains(int x, int y, int radius) {
        return flx - radius <= x
                && fly - radius <= y
                && fhx + radius >= x
                && fhy + radius >= y;
    }

    @Override
    public void insert(Foothold object) {
        if (flx > object.x1()) flx = object.x1();
        if (fhx < object.x1()) fhx = object.x1();
        if (fly > object.y1()) fly = object.y1();
        if (fhy < object.y1()) fhy = object.y1();

        if (flx > object.x2()) flx = object.x2();
        if (fhx < object.x2()) fhx = object.x2();
        if (fly > object.y2()) fly = object.y2();
        if (fhy < object.y2()) fhy = object.y2();

        if (depth == MAX_DEPTH || (Math.abs(hx - lx) <= 1 || Math.abs(ly - hy) <= 1)) {
            if (values == null) values = new HashSet<>();
            values.add(object);
        } else {
            if (quadrants == null) split();
            boolean[] maybes = new boolean[4];
            object.path((x, y) -> maybes[getQuadrant(x, y)] = true);
            if (maybes[0]) quadrants[0].insert(object);
            if (maybes[1]) quadrants[1].insert(object);
            if (maybes[2]) quadrants[2].insert(object);
            if (maybes[3]) quadrants[3].insert(object);
        }
    }

    @Override
    protected void split() {
        var center = center();
        quadrants = new MoeQuad[4];
        quadrants[QUAD_NW] = new MoeQuad(depth + 1, low(), center);
        quadrants[QUAD_NE] = new MoeQuad(depth + 1, Point.of(cx, ly), Point.of(hx, cy));
        quadrants[QUAD_SW] = new MoeQuad(depth + 1, Point.of(lx, cy), Point.of(cx, hy));
        quadrants[QUAD_SE] = new MoeQuad(depth + 1, center, high());
    }
}
