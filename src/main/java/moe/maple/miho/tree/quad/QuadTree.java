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

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface QuadTree<T> extends Iterable<T> {
    int QUAD_NW = 0;
    int QUAD_NE = 1;
    int QUAD_SW = 2;
    int QUAD_SE = 3;

    int depth();

    int size();

    Point low();

    Point high();

    Point center();

    int getQuadrant(T object);

    int getQuadrant(int x, int y);

    void insert(T object);

    void searchDown(Consumer<T> check, int x, int y);
//    void search(Consumer<T> check, int x, int y, int radius);

    Stream<QuadTree<T>> streamQuads();

    Stream<T> stream();
}
