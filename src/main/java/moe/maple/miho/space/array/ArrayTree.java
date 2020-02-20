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

package moe.maple.miho.space.array;

import moe.maple.miho.foothold.Foothold;
import moe.maple.miho.line.Line;
import moe.maple.miho.point.Point;
import moe.maple.miho.rect.MutableRect;
import moe.maple.miho.rect.Rect;
import moe.maple.miho.tree.PointTree;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class ArrayTree implements PointTree<Foothold> {

    private final Foothold[] data;

    public ArrayTree(Point low, Point high, Foothold[] footholds) {
        this.data = footholds;
    }

    @Override
    public Rect bounds() {
        return MutableRect.of(Line.min(data), Line.max(data));
    }

    @Override
    public Point low() {
        return Line.min(data);
    }

    @Override
    public Point high() {
        return Line.max(data);
    }

    @Override
    public void insert(Foothold object) {
        throw new UnsupportedOperationException("Nope.");
    }

    @Override
    public void searchDown(Consumer<Foothold> check, int x, int y, int radius) {
        for (Foothold datum : data) {
            check.accept(datum);
        }
    }

    @Override
    public void searchDistance(Consumer<Foothold> check, int x, int y, int minDistance, int maxDistance) {
        for (Foothold datum : data) {
            check.accept(datum);
        }
    }

    @Override
    public Stream<Foothold> stream() {
        return Stream.of(data);
    }
}
