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

package moe.maple.miho.tree.quad;

import moe.maple.miho.point.Point;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class AbstractQuad<T> implements QuadTree<T> {

    protected int depth;
    protected int lx, ly;
    protected int hx, hy;
    protected int cx, cy;

    protected AbstractQuad<T>[] quadrants;
    protected Set<T> values;

    public AbstractQuad(int depth, Point low, Point high) {
        if (low.x() == high.x())
            throw new IllegalArgumentException("x is invalid: " + low + ", " + high);
        if (low.y() == high.y())
            throw new IllegalArgumentException("y is invalid: " + low + ", " + high);

        this.depth = depth;
        this.lx = low.x();
        this.ly = low.y();
        this.hx = high.x();
        this.hy = high.y();

        this.cx = (lx + hx) / 2;
        this.cy = (ly + hy) / 2;
        this.values = new HashSet<>();
    }

    @Override
    public int depth() {
        return depth;
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public Point low() {
        return Point.of(lx, ly);
    }

    @Override
    public Point high() {
        return Point.of(hx, hy);
    }

    @Override
    public Point center() {
        return Point.of(cx, cy);
    }

    @Override
    public int getQuadrant(int x, int y) {
        if (x <= cx && y <= cy) return QUAD_NW;
        else if (x > cx && y <= cy) return QUAD_NE;
        else if (x <= cx && y > cx) return QUAD_SW;
        else return QUAD_SE;
    }

    protected abstract boolean accept(T object);

    protected abstract boolean contains(int x, int y, int radius);

    protected abstract void split();

    @Override
    public void insert(T object) {
        if (accept(object)) {
            if (values == null) values = new HashSet<>();
            values.add(object);
        } else {
            if (quadrants == null) split();
            int idx = getQuadrant(object);
            quadrants[idx].insert(object);
        }
    }

    @Override
    public void searchDown(Consumer<T> action, int x, int y) {
        values.forEach(action);
        if (quadrants == null) return;
        var radius = depth * 150 << 1;
        if (quadrants[0].contains(x, y, radius))
            quadrants[0].searchDown(action, x, y);
        if (quadrants[1].contains(x, y, radius))
            quadrants[1].searchDown(action, x, y);
        if (quadrants[2].contains(x, y, radius))
            quadrants[2].searchDown(action, x, y);
        if (quadrants[3].contains(x, y, radius))
            quadrants[3].searchDown(action, x, y);
    }

    @Override
    public Stream<QuadTree<T>> streamQuads() {
        if (quadrants == null) return Stream.of(this);
        return Stream.concat(Stream.of(this), Arrays.stream(quadrants).flatMap(AbstractQuad::streamQuads));
    }

    @Override
    public Stream<T> stream() {
        if (values == null) return Stream.empty();
        return values.stream();
    }

    @Override
    public Iterator<T> iterator() {
        if (values == null) return Collections.emptyIterator();
        return values.iterator();
    }
}
