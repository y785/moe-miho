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

    private MoeBstRoot(MutableRect rect) {
        super(null);
        this.bounds = rect;
        this.rootBounds = MutableRect.of(rect);
    }

    MoeBstRoot(Point low, Point high) {
        this(MutableRect.of(low, high));
    }

    @Override
    public void searchDown(Consumer<Foothold> check, int x, int y, int radius) {
        if (left != null) {
            var lb = left.getRootBounds();
            if (lb.compareX(x) == 0 && lb.compareY(y) != 1)
                left.searchDown(check, x, y, radius);
        }
        if (right != null) {
            var rb = right.getRootBounds();
            if (rb.compareX(x) == 0 && rb.compareY(y) != 1)
                right.searchDown(check, x, y, radius);
        }
    }

    @Override
    public synchronized void insert(Foothold fh) {
        insertRaw(fh);
    }

}
