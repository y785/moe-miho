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

import moe.maple.miho.line.MutableLine;
import moe.maple.miho.point.Point;

public interface Foothold extends MutableLine {

    int id();

    int layer();

    int group();

    int prev();

    int next();

    boolean isSlope();

    boolean isWall();

    static Foothold of() {
        return new MoeFoothold(0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    static Foothold of(Foothold other) {
        return new MoeFoothold(
                other.id(), other.layer(), other.group(),
                other.prev(), other.next(),
                other.x1(), other.y1(),
                other.x2(), other.y2());
    }

    static Foothold of(int id, int layer, int group,
                       int prev, int next,
                       int x1, int y1, int x2, int y2) {
        return new MoeFoothold(id, layer, group, prev, next, x1, y1, x2, y2);
    }

    static Foothold of(int id, int layer, int group,
                       int prev, int next,
                       Point start, Point end) {
        return new MoeFoothold(id, layer, group, prev, next, start, end);
    }

}
