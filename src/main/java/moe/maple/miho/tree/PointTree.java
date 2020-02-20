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

package moe.maple.miho.tree;

import moe.maple.miho.point.Point;
import moe.maple.miho.rect.Rect;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface PointTree<T> {

    Rect bounds();

    Point low();

    Point high();

    void insert(T object);

    @SuppressWarnings("unchecked")
    default void insert(T... objects) {
        for (var obj : objects) insert(obj);
    }

    default void insert(Collection<T> objects) {
        objects.forEach(this::insert);
    }

    void searchDown(Consumer<T> check, int x, int y, int radius);

    void searchDistance(Consumer<T> check, int x, int y, int minDistance, int maxDistance);

    Stream<T> stream();
}
