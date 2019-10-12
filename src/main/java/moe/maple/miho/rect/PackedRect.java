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

package moe.maple.miho.rect;

import moe.maple.miho.line.Line;
import moe.maple.miho.line.PackedLine;
import moe.maple.miho.point.PackedPoint;
import moe.maple.miho.point.Point;

/**
 * Represents a rect (4 shorts) as a single long.
 */
public final class PackedRect {
    private PackedRect() {
    }

    public static long x(long rect, short x) {
        return (rect & ~(0xFFFFL << 48)) | ((long) (x & 0xFFFF) << 48);
    }

    public static long x(long rect, int x) {
        return x(rect, (short) x);
    }

    public static long y(long rect, short y) {
        return (rect & ~(0xFFFFL << 32)) | ((long) (y & 0xFFFF) << 32);
    }

    public static long y(long rect, int y) {
        return y(rect, (short) y);
    }

    public static long w(long rect, short width) {
        return (rect & ~(0xFFFFL << 16)) | ((long) (width & 0xFFFF) << 16);
    }

    public static long w(long rect, int width) {
        return w(rect, (short) width);
    }

    public static long h(long rect, short height) {
        return (rect & ~0xFFFFL) | (long) (height & 0xFFFF);
    }

    public static long h(long rect, int height) {
        return h(rect, (short) height);
    }

    public static short x(long rect) {
        return PackedPoint.x(xy(rect));
    }

    public static short y(long rect) {
        return PackedPoint.y(xy(rect));
    }

    public static short w(long rect) {
        return PackedPoint.x(wh(rect));
    }

    public static short h(long rect) {
        return PackedPoint.y(wh(rect));
    }

    public static int xy(long rect) {
        return (int) (rect >> 32);
    }

    public static int wh(long rect) {
        return (int) (rect & 0xFFFFFFFFL);
    }

    public static float angle(long rect, int x, int y) {
        var center = center(rect);
        return Point.angle(
                PackedPoint.x(center),
                PackedPoint.y(center),
                x, y);
    }

    public static int center(long rect) {
        return PackedPoint.of(x(rect) + w(rect) / 2, y(rect) + h(rect) / 2);
    }

    public static long center(long rect, int x, int y) {
        var w = w(rect);
        var h = h(rect);
        return y(x(rect, x - w / 2), y - h / 2);
    }

    public static long center(long rect, Point point) {
        return center(rect, point.x(), point.y());
    }

    public static long center(long rect, int point) {
        return center(rect, PackedPoint.x(point), PackedPoint.y(point));
    }

    public static boolean contains(long rect, int x, int y) {
        var rx = x(rect);
        var ry = y(rect);
        return x >= rx && y >= ry && x < rx + w(rect) && y < ry + h(rect);
    }

    public static boolean contains(long rect, int point) {
        return contains(rect, PackedPoint.x(point), PackedPoint.y(point));
    }

    public static boolean contains(long rect, Point point) {
        return contains(rect, point.x(), point.y());
    }

    public static boolean containsR(long rect, long other) {
        return contains(rect, x(other), y(other)) &&
                contains(rect, x(other), y(other) + h(other)) &&
                contains(rect, x(other) + w(other), y(other) + h(other)) &&
                contains(rect, x(other) + w(other), y(other));
    }

    public static boolean contains(long rect, Rect other) {
        return containsR(rect, of(other));
    }

    public static boolean containsL(long rect, long line) {
        return contains(rect, PackedLine.start(line)) &&
                contains(rect, PackedLine.end(line));
    }

    public static boolean contains(long rect, Line line) {
        return contains(rect, line.start()) && contains(rect, line.end());
    }

    public static boolean intersects(long rect, int x1, int y1, int x2, int y2) {
        return Rect.intersects(x(rect), y(rect), w(rect), h(rect), x1, y1, x2, y2);
    }

    public static boolean intersects(long rect, Line line) {
        return intersects(rect, line.x1(), line.y1(), line.x2(), line.y2());
    }

    public static boolean intersectsL(long rect, long line) {
        return intersects(rect,
                PackedLine.x1(line), PackedLine.y1(line),
                PackedLine.x2(line), PackedLine.y2(line));
    }

    public static long unionR(long rect, long other) {
        var x1 = Math.min(x(rect), x(other));
        var x2 = Math.max(x(rect) + w(rect), x(other) + w(other));
        var y1 = Math.min(y(rect), y(other));
        var y2 = Math.max(y(rect) + h(rect), y(other) + h(other));
        return of(x1, y1, x2 - x1, y2 - y1);
    }

    public static long unionL(long rect, long line) {
        return union(union(rect, PackedLine.start(line)), PackedLine.end(line));
    }

    public static long union(long rect, Rect other) {
        return unionR(rect, of(other));
    }

    public static long union(long rect, Line line) {
        return unionL(rect, PackedLine.of(line));
    }

    public static long union(long rect, int x, int y) {
        var x1 = Math.min(x(rect), x);
        var y1 = Math.min(y(rect), y);
        var x2 = Math.max(x(rect) + w(rect), x);
        var y2 = Math.max(y(rect) + h(rect), y);
        return of(x1, y1, x2 - x1, y2 - y1);
    }

    public static long union(long rect, int point) {
        return union(rect, PackedPoint.x(point), PackedPoint.y(point));
    }

    public static long union(long rect, Point point) {
        return union(rect, point.x(), point.y());
    }

    public static long of(short x, short y, short width, short height) {
        return ((long) PackedPoint.of(x, y) << 32) | (PackedPoint.of(width, height) & 0xFFFFFFFFL);
    }

    public static long of(int x, int y, int width, int height) {
        return of((short) x, (short) y, (short) width, (short) height);
    }

    public static long of(Rect rect) {
        return of(rect.x(), rect.y(), rect.width(), rect.height());
    }

    public static long of(Point a, Point b) {
        var min = Point.min(a, b);
        var max = Point.max(a, b);
        return of(min.x() - 1, min.y() - 1,
                Math.abs(min.x() - max.x()) + 2,
                Math.abs(min.y() - max.y()) + 2);
    }

    public static long of(int joinedPointA, int joinedPointB) {
        var min = PackedPoint.min(joinedPointA, joinedPointB);
        var max = PackedPoint.max(joinedPointA, joinedPointB);
        var mnx = PackedPoint.x(min);
        var mny = PackedPoint.y(min);
        var mxx = PackedPoint.x(max);
        var mxy = PackedPoint.y(max);
        return of(mnx - 1, mnx - 1,
                Math.abs(mnx - mxx) + 2,
                Math.abs(mny - mxy) + 2);
    }
}
