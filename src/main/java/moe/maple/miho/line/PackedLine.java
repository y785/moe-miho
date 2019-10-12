/*
 * Copyright (C) 2019, y785, http://github.com/y785
 *
 * Permission is hereby granted, Free oF charge, to any person obtaining a copy
 * oF this soFtware and associated documentation Files (the "SoFtware"), to deal
 * in the SoFtware without restriction, including without limitation the rights
 * to use, copy, modiFy, merge, publish, distribute, sublicense, and/or sell
 * copies oF the SoFtware, and to permit persons to whom the SoFtware is
 * Furnished to do so, subject to the Following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions oF the SoFtware.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package moe.maple.miho.line;

import moe.maple.miho.point.PackedPoint;
import moe.maple.miho.point.Point;

/**
 * Represents a line (4 shorts) (two points {@link PackedPoint}) as a single long.
 */
public final class PackedLine {
    private PackedLine() {
    }

    public static long x1(long line, short x) {
        return (line & ~(0xFFFFL << 48)) | ((x & 0xFFFFL) << 48);
    }

    public static long x1(long line, int x) {
        return x1(line, (short) x);
    }

    public static long y1(long line, short y) {
        return (line & ~(0xFFFFL << 32)) | ((y & 0xFFFFL) << 32);
    }

    public static long y1(long line, int y) {
        return y1(line, (short) y);
    }

    public static long x2(long line, short x) {
        return (line & ~(0xFFFFL << 16)) | ((x & 0xFFFFL) << 16);
    }

    public static long x2(long line, int x) {
        return x2(line, (short) x);
    }

    public static long y2(long line, short y) {
        return (line & ~0xFFFFL) | (long) (y & 0xFFFF);
    }

    public static long y2(long line, int y) {
        return y2(line, (short) y);
    }

    public static short x1(long line) {
        return PackedPoint.x(start(line));
    }

    public static short y1(long line) {
        return PackedPoint.y(start(line));
    }

    public static short x2(long line) {
        return PackedPoint.x(end(line));
    }

    public static short y2(long line) {
        return PackedPoint.y(end(line));
    }

    public static int start(long line) {
        return (int) (line >> 32);
    }

    public static int end(long line) {
        return (int) (line & 0xFFFFFFFFL);
    }

    private static int cross(long line, int x, int y) {
        int x1 = x1(line);
        int x2 = x2(line);
        int y1 = y1(line);
        int y2 = y2(line);
        return (x - x1) * (y2 - y1) - (y - y1) * (x2 - x1);
    }

    public static boolean above(long line, int x, int y) {
        return cross(line, x, y) > 0;
    }

    public static boolean above(long line, Point point) {
        return above(line, point.x(), point.y());
    }

    public static boolean above(long line, int point) {
        return above(line, PackedPoint.x(point), PackedPoint.y(point));
    }

    public static boolean below(long line, int x, int y) {
        return cross(line, x, y) < 0;
    }

    public static boolean below(long line, Point point) {
        return below(line, point.x(), point.y());
    }

    public static boolean below(long line, int point) {
        return below(line, PackedPoint.x(point), PackedPoint.y(point));
    }

    public static int closest(long line, int x, int y) {
        var x1 = x1(line);
        var x2 = x2(line);
        var y1 = y1(line);
        var y2 = y2(line);
        var pdx = x - x1;
        var pdy = y - y1;
        var dx = x2 - x1;
        var dy = y2 - y1;

        var sqr = (float) Point.squared(dx, dy);
        var dot = (float) Point.dot(pdx, pdy, dx, dy);
        var dst = dot / sqr;

        if (dst < 0) return PackedPoint.of(x1, y1);
        if (dst > 1) return PackedPoint.of(x2, y2);

        var rx = (int) (x1 + dx * dst);
        var ry = (int) (y1 + dy * dst);
        return PackedPoint.of(rx, ry);
    }

    public static int closest(long line, int point) {
        return closest(line, PackedPoint.x(point), PackedPoint.y(point));
    }

    public static int closest(long line, Point point) {
        return closest(line, point.x(), point.y());
    }

    public static int compareX(long line, int x) {
        return Line.compX(x1(line), x2(line), x);
    }

    public static int compareX(long line, Point point) {
        return compareX(line, point.x());
    }


    public static int compareX(long line, long other) {
        if (x2(line) < x1(other)) return -1;
        if (x1(line) > x2(other)) return 1;
        return 0;
    }

    public static int compareY(long line, int y) {
        return Line.compY(y1(line), y2(line), y);
    }

    public static int compareY(long line, Point point) {
        return compareY(line, point.y());
    }

    public static int compareY(long line, long other) {
        if (y2(line) < y1(other)) return -1;
        if (y1(line) > y2(other)) return 1;
        return 0;
    }

    public static boolean contains(long line, int x, int y) {
        var x1 = x1(line);
        var x2 = x2(line);
        var y1 = y1(line);
        var y2 = y2(line);
        if (x1 == x) return x2 == x;
        if (y1 == y) return y2 == y;
        return (x1 - x) * (y1 - y) == (x - x2) * (y - y2);
    }

    public static boolean contains(long line, int point) {
        return contains(line, PackedPoint.x(point), PackedPoint.y(point));
    }

    public static boolean contains(long line, Point point) {
        return contains(line, point.x(), point.y());
    }

    public static long of(short x1, short y1, short x2, short y2) {
        return ((long) PackedPoint.of(x1, y1) << 32) | (PackedPoint.of(x2, y2) & 0xFFFFFFFFL);
    }

    public static long of(int x1, int y1, int x2, int y2) {
        return of((short) x1, (short) y1, (short) x2, (short) y2);
    }

    public static long of(Line line) {
        return of(line.x1(), line.y1(), line.x1(), line.y2());
    }

    public static long of(Point start, Point end) {
        return of(start.x(), start.y(), end.x(), end.y());
    }

    public static long of(int joinedStart, int joinedEnd) {
        return of(PackedPoint.x(joinedStart),
                PackedPoint.y(joinedStart),
                PackedPoint.x(joinedEnd),
                PackedPoint.y(joinedEnd));
    }
}
