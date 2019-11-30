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

import moe.maple.miho.line.Line;
import moe.maple.miho.rect.Rect;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public interface Point extends Comparable<Point> {
    int x();

    int y();

    default boolean above(Line line) {
        return line.below(this);
    }

    default boolean below(Line line) {
        return line.above(this);
    }

    Point copy();

    default int distance(int x, int y) {
        return Point.distance(x(), y(), x, y);
    }

    default int distance(Point other) {
        return distance(other.x(), other.y());
    }

    float length();

    static float angle(int fromX, int fromY, int x, int y) {
        return (float) Math.toDegrees(Math.atan2(y - fromY, x - fromX));
    }

    static float angle(Point from, Point to) {
        return angle(from.x(), from.y(), to.x(), to.y());
    }

    static int distance(int x1, int y1, int x2, int y2) {
        var fix = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
        var sqrt = (int) Math.sqrt(fix);
        var hsqr = sqrt * sqrt + sqrt;
        return sqrt + (~~(hsqr - fix) >>> (Integer.SIZE - 1));
    }

    static int distance(Point a, Point b) {
        return distance(a.x(), a.y(), b.x(), b.y());
    }

    static int dot(int x1, int y1, int x2, int y2) {
        return (x1 * x2) + (y1 * y2);
    }

    static int dot(Point a, Point b) {
        return dot(a.x(), a.y(), b.x(), b.y());
    }

    static Point rand(int minX, int maxX, int minY, int maxY) {
        var rand = ThreadLocalRandom.current();
        var x = rand.nextInt(Math.min(minX, maxX), Math.max(minX, maxX));
        var y = rand.nextInt(Math.min(minY, maxY), Math.max(minY, maxY));
        return of(x, y);
    }

    static Point rand(Rect rect) {
        return rand(rect.x() + 1, rect.x() + rect.width() - 1,
                rect.y() + 1, rect.y() + rect.height() - 1);
    }

    static int squared(int x, int y) {
        return (x * x) + (y * y);
    }

    static int squared(Point point) {
        return squared(point.x(), point.y());
    }

    static Point of() {
        return new ImmutablePoint();
    }

    static Point of(Point other) {
        return new ImmutablePoint(other);
    }

    static Point of(int x, int y) {
        return new ImmutablePoint(x, y);
    }

    static Point ofCenter(Rect rect) {
        return of(rect.x() + rect.width() / 2, rect.y() + rect.height() / 2);
    }

    static Point ofTopLeft(Rect rect) {
        return of(rect.x(), rect.y() + rect.height());
    }

    static Point ofTopRight(Rect rect) {
        return of(rect.x() + rect.width(), rect.y() + rect.height());
    }

    static Point ofBottomLeft(Rect rect) {
        return of(rect.x(), rect.y());
    }

    static Point ofBottomRight(Rect rect) {
        return of(rect.x() + rect.width(), rect.y());
    }

    static Point max(Point... points) {
        if (points == null || points.length == 0)
            throw new InvalidParameterException("Points aren't set");
        var mx = points[0].x();
        var my = points[0].y();
        for (var p : points) {
            mx = Math.max(p.x(), mx);
            my = Math.max(p.y(), my);
        }
        return of(mx, my);
    }

    static Point max(Collection<Point> points) {
        if (points == null || points.size() == 0)
            throw new InvalidParameterException("Points aren't set");
        var mx = points.stream().mapToInt(Point::x).max().orElseThrow();
        var my = points.stream().mapToInt(Point::y).max().orElseThrow();
        return of(mx, my);
    }

    static Point min(Point... points) {
        if (points == null || points.length == 0)
            throw new InvalidParameterException("Points aren't set");
        var mx = points[0].x();
        var my = points[0].y();
        for (var p : points) {
            mx = Math.min(p.x(), mx);
            my = Math.min(p.y(), my);
        }
        return of(mx, my);
    }

    static Point min(Collection<Point> points) {
        if (points == null || points.size() == 0)
            throw new InvalidParameterException("Points aren't set");
        var mx = points.stream().mapToInt(Point::x).min().orElseThrow();
        var my = points.stream().mapToInt(Point::y).min().orElseThrow();
        return of(mx, my);
    }
}
