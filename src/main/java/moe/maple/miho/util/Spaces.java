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
import java.util.function.IntConsumer;

/**
 *
 */
public class Spaces {

    private static int randomX(int x1, int x2) {
        var min = Math.min(x1, x2);
        var max = Math.max(x1, x2);
        return ThreadLocalRandom.current().nextInt(min, min == max ? max + 1 : max);
    }

    private static int randomY(int y1, int y2) {
        var min = Math.min(y1, y2);
        var max = Math.max(y1, y2);
        return ThreadLocalRandom.current().nextInt(min, min == max ? max + 1 : max);
    }

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

    public static int spiral(Rect rect, double rotation, int distance, int prec, int iter) {
        var cx = rect.cx();
        var cy = rect.cy();
        var dst = PackedPoint.distance(PackedPoint.of(cx, cy), PackedPoint.of(rect.x(), rect.y()));

        final var thetaMax = prec * 2 * Math.PI;
        final var awayStep = dst / thetaMax;
        var curs = 0;
        for (double theta = distance / awayStep; theta <= thetaMax; ) {
            curs++;
            double away = awayStep * theta;
            double around = theta + rotation;

            double x = cx + Math.cos(around) * away;
            double y = cy + Math.sin(around) * away;

            if (curs == iter)
                return PackedPoint.of((int) Math.round(x), (int) Math.round(y));

            theta += distance / away;
        }
        return PackedPoint.of(cx, cy);
    }

    public static void spiral(Rect rect, double rotation, int distance, int prec, IntConsumer iter) {
        var cx = rect.cx();
        var cy = rect.cy();
        var dst = PackedPoint.distance(PackedPoint.of(cx, cy), PackedPoint.of(rect.x(), rect.y()));

        final var thetaMax = prec * 2 * Math.PI;
        final var awayStep = dst / thetaMax;
        for (double theta = distance / awayStep; theta <= thetaMax; ) {
            double away = awayStep * theta;
            double around = theta + rotation;

            double x = cx + Math.cos(around) * away;
            double y = cy + Math.sin(around) * away;

            iter.accept(PackedPoint.of((int) Math.round(x), (int) Math.round(y)));

            theta += distance / away;
        }
    }

}
