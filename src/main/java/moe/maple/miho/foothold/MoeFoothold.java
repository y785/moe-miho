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

package moe.maple.miho.foothold;

import moe.maple.miho.line.MoeLine;
import moe.maple.miho.point.Point;

import java.util.Objects;

public class MoeFoothold extends MoeLine implements Foothold {

    private final int id, layer, group, prev, next;

    public MoeFoothold(int id, int layer, int group, int prev, int next, int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
        this.id = id;
        this.layer = layer;
        this.group = group;
        this.prev = prev;
        this.next = next;
    }

    public MoeFoothold(int id, int layer, int group, int prev, int next, Point start, Point end) {
        this(id, layer, group, prev, next, start.x(), start.y(), end.x(), end.y());
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public int layer() {
        return layer;
    }

    @Override
    public int group() {
        return group;
    }

    @Override
    public int prev() {
        return prev;
    }

    @Override
    public int next() {
        return next;
    }

    @Override
    public boolean isSlope() {
        return y1 != y2;
    }

    @Override
    public boolean isWall() {
        return x1 == x2;
    }

    @Override
    public boolean above(int x, int y) {
        if (compareX(x) != 0)
            return false;
        return y1 != y2 && !super.above(x, y) || (y1 < y && y2 < y);
    }

    @Override
    public boolean below(int x, int y) {
        if (compareX(x) != 0)
            return false;
        return !(y1 != y2 && !super.above(x, y) || (y1 < y && y2 < y));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof MoeFoothold)) return false;
        MoeFoothold that = (MoeFoothold) object;
        return id == that.id &&
                layer == that.layer &&
                group == that.group &&
                prev == that.prev &&
                next == that.next;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, layer, group, prev, next);
    }

    @Override
    public String toString() {
        return String.format("{ \"id\": %d, \"layer\": %d, \"group\": %d, \"prev\": %d, \"next\": %d, " + super.toString() + "}",
                id, layer, group, prev, next);
    }
}
