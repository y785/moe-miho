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

package moe.maple.miho.space.bst;

import moe.maple.miho.foothold.Foothold;
import moe.maple.miho.point.Point;
import moe.maple.miho.rect.MutableRect;

import java.util.function.Consumer;

public class MoeBstRoot extends MoeBstNode {

    public MoeBstRoot(MutableRect rect) {
        super(null);
        this.rect = rect;
        this.bounds = MutableRect.of(rect);
    }

    public MoeBstRoot(Point low, Point high) {
        this(MutableRect.of(low, high));
    }

    @Override
    public void searchDown(Consumer<Foothold> check, int x, int y) {
        if (bounds.contains(x, y)) {
            if (left != null) left.searchDown(check, x, y);
            if (right != null) right.searchDown(check, x, y);
        }
    }

    @Override
    public synchronized void insert(Foothold fh) {
        insertRaw(fh);
    }
}