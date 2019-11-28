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

package moe.maple.miho.util;

import moe.maple.miho.point.PackedPoint;
import moe.maple.miho.rect.Rect;
import moe.maple.miho.space.PhysicalSpace2D;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class Spaces {

    private static int randomX(int x1, int x2) {
        var min = Math.min(x1, x2);
        var max = Math.max(x1, x2);
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private static int randomY(int y1, int y2) {
        var min = Math.min(y1, y2);
        var max = Math.max(y1, y2);
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * @return {@link PackedPoint}
     */
    public static int explosion(PhysicalSpace2D space, int fromX, int fromY, int iter, int spacing) {
        var low = space.tree().low();
        var high = space.tree().high();

        var left = iter % 2 == 0;
        var x = left ?
                fromX - (spacing * iter) :
                fromX + (spacing * iter);

        if (x > high.x() || x < low.x()) x = randomX(x, fromX);

        var fh = space.getFootholdUnderneath(x, fromY, () -> space.getFootholdUnderneath(fromX, fromY));
        if (fh == null)
            return PackedPoint.of(fromX, fromY);

        return fh.closest(x, fromY);
    }

    public static int spiral(PhysicalSpace2D space, Rect rect, int angle, int arc, int depth, int iter, int prec) {
        if (iter > prec) throw new IllegalArgumentException("Iteration can't be higher than: " + prec);

        var width = rect.width();
        var height = rect.height();
        var x = rect.cx();
        var y = rect.cy();

        for (int i = 0; i < prec; i++) {
            if (i == iter) return PackedPoint.of(x, y);
            if (i % 2 == 0) {
                y = y - depth;
                width = width + 2 * depth;
                height = height + 2 * depth;
            } else {
                x = x - 2 * depth;
                y = y - depth;
                width = width + 2 * depth;
                height = height + 2 * depth;
            }
        }
        return PackedPoint.of(x, y);
    }

}
