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

package moe.maple.miho.line;

import moe.maple.miho.point.Point;

public class MoeLine extends ImmutableLine implements MutableLine {

    public MoeLine() {
        super();
    }

    public MoeLine(Point start, Point end) {
        super(start, end);
    }

    public MoeLine(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void x1(int x1) {
        this.x1 = x1;
    }

    @Override
    public void y1(int y1) {
        this.y1 = y1;
    }

    @Override
    public void x2(int x2) {
        this.x2 = x2;
    }

    @Override
    public void y2(int y2) {
        this.y2 = y2;
    }

    @Override
    public MutableLine copy() {
        return new MoeLine(x1, y1, x2, y2);
    }

    @Override
    public String toString() {
        return String.format("{ \"x1\": %d, \"y1\": %d, \"x2\": %d, \"y2\": %d }", x1, y1, x2, y2);
    }
}
