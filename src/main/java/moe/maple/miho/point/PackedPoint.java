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

import java.security.InvalidParameterException;
import java.util.Collection;

/**
 * Represents two shorts as a single int.
 */
public final class PackedPoint {

    private PackedPoint() {
    }

    public static short x(int point) {
        return (short) (point >> 16);
    }

    public static short y(int point) {
        return (short) (point & 0xFFFF);
    }

    public static float angle(int point, int x, int y) {
        return Point.angle(x(point), y(point), x, y);
    }

    public static float angle(int point, int other) {
        return angle(point, x(other), y(other));
    }

    public static float angle(int point, Point other) {
        return angle(point, other.x(), other.y());
    }

    public static int distance(int point, int x, int y) {
        return Point.distance(x(point), y(point), x, y);
    }

    public static int distance(int point, int other) {
        return distance(point, x(other), y(other));
    }

    public static int distance(int point, Point other) {
        return distance(point, other.x(), other.y());
    }

    public static int dot(int point, int x2, int y2) {
        var x1 = x(point);
        var y1 = y(point);
        return Point.dot(x1, y1, x2, y2);
    }

    public static int dot(int point, int other) {
        return dot(point, x(other), y(other));
    }

    public static int dot(int point, Point p) {
        return dot(point, p.x(), p.y());
    }

    public static int squared(int point) {
        return Point.squared(x(point), y(point));
    }

    public static int of(short x, short y) {
        return (x << 16) | y & 0xFFFF;
    }

    public static int of(int x, int y) {
        return of((short) x, (short) y);
    }

    public static int of(Point point) {
        return of(point.x(), point.y());
    }

    public static int max(int... points) {
        if (points == null || points.length == 0)
            throw new InvalidParameterException("Points aren't set");
        var mx = x(points[0]);
        var my = y(points[0]);
        for (var p : points) {
            mx = (short) Math.max(mx, x(p));
            my = (short) Math.max(my, y(p));
        }
        return of(mx, my);
    }

    public static int max(Collection<Integer> points) {
        if (points == null || points.size() == 0)
            throw new InvalidParameterException("Points aren't set");
        var mx = points.stream().mapToInt(PackedPoint::x).max().orElseThrow();
        var my = points.stream().mapToInt(PackedPoint::y).max().orElseThrow();
        return of(mx, my);
    }

    public static int min(int... points) {
        if (points == null || points.length == 0)
            throw new InvalidParameterException("Points aren't set");
        var mx = x(points[0]);
        var my = y(points[0]);
        for (var p : points) {
            mx = (short) Math.min(mx, x(p));
            my = (short) Math.min(my, y(p));
        }
        return of(mx, my);
    }

    public static int min(Collection<Integer> points) {
        if (points == null || points.size() == 0)
            throw new InvalidParameterException("Points aren't set");
        var mx = points.stream().mapToInt(PackedPoint::x).min().orElseThrow();
        var my = points.stream().mapToInt(PackedPoint::y).min().orElseThrow();
        return of(mx, my);
    }
}
