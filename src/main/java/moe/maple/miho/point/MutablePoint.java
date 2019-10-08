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

package moe.maple.miho.point;

public interface MutablePoint extends Point {
    void x(int x);

    void y(int y);

    MutablePoint copy();

    default void plus(int x, int y) {
        x(x() + x);
        y(y() + y);
    }

    default void plus(Point p) {
        plus(p.x(), p.y());
    }

    default void minus(int x, int y) {
        x(x() - x);
        y(y() - y);
    }

    default void minus(Point p) {
        minus(p.x(), p.y());
    }

    default void multiply(int x, int y) {
        x(x() * x);
        y(y() * y);
    }

    default void multiply(Point p) {
        multiply(p.x(), p.y());
    }


    default void divide(int x, int y) {
        x(x() / x);
        y(y() / y);
    }

    default void divide(Point p) {
        divide(p.x(), p.y());
    }

    default void joined(long p) {
        x((int) (p >> 32));
        y((int) p);
    }

    static MutablePoint of() {
        return new MoePoint();
    }

    static MutablePoint of(Point other) {
        return new MoePoint(other);
    }

    static MutablePoint of(int x, int y) {
        return new MoePoint(x, y);
    }
}
