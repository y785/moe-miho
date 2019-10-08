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

package moe.maple.miho.space;

import moe.maple.miho.foothold.Foothold;
import moe.maple.miho.point.Point;
import moe.maple.miho.rect.Rect;

import java.util.function.Supplier;

public abstract class AbstractPhysicalSpace2D implements PhysicalSpace2D {

    private final Point low, high, center;
    private final int width, height;
    private final Rect bounds;

    protected AbstractPhysicalSpace2D(Point low, Point high) {
        this.low = low;
        this.high = high;

        this.width = high.x() - low.x();
        this.height = high.y() - low.y();

        this.bounds = Rect.of(low.x() + height, low.y() + height, width, height);
        this.center = Point.ofCenter(bounds);
    }

    @Override
    public Point high() {
        return high;
    }

    @Override
    public Point low() {
        return low;
    }

    @Override
    public Point center() {
        return center;
    }

    @Override
    public Rect bounds() {
        return bounds;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public boolean isInBounds(int x, int y) {
        return bounds.contains(x, y);
    }

    @Override
    public Foothold getFootholdUnderneath(int x, int y, Supplier<Foothold> fallback) {
        var res = getFootholdUnderneath(x, y);
        if (res == null) res = fallback.get();
        return res;
    }
}
