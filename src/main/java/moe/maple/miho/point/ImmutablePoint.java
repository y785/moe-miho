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

public class ImmutablePoint implements Point {

    protected int x, y;

    public ImmutablePoint(Point other) {
        this.x = other.x();
        this.y = other.y();
    }

    public ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ImmutablePoint() {
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
    public float length() {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    @Override
    public int compareTo(Point o) {
        var ox = o.x();
        var oy = o.y();
        if (x < ox || y < oy)
            return -1;
        if (x > ox || y > oy)
            return 1;
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!ImmutablePoint.class.isAssignableFrom(obj.getClass())) return false;
        final ImmutablePoint vec = (ImmutablePoint) obj;
        if (this.x != vec.x) return false;
        if (this.y != vec.y) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (x + y) * ((x + y) + 1) / 2 + x; // Cantor
    }

    @Override
    public String toString() {
        return String.format("{ \"x\": %d, \"y\": %d }", x, y);
    }
}
