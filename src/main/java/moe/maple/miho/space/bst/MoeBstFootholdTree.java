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
import moe.maple.miho.rect.Rect;
import moe.maple.miho.space.AbstractPhysicalSpace2D;
import moe.maple.miho.tree.Result;

import java.util.Iterator;
import java.util.List;

public class MoeBstFootholdTree extends AbstractPhysicalSpace2D {

    private MoeBstRoot root;

    public MoeBstFootholdTree(Point low, Point high, Foothold[] footholds) {
        super(low, high);
        this.root = new MoeBstRoot(low, high);
        for (var fh : footholds) root.insert(fh);
    }

    @Override
    public Foothold getFootholdUnderneath(int x, int y) {
        var result = Result.of((Foothold) null);
        root.searchDown(match -> {
            if (!match.isWall() && match.below(x, y))
                result.setIf(res -> res.compareY(match) == 1, match);
        }, x, y, 0);
        return result.get();
    }

    @Override
    public Foothold getFootholdClosest(int x, int y, int pcx, int pcy, int ptHitx) {
        return null;
    }

    @Override
    public Foothold getFootholdRandom(Rect rect) {
        return null;
    }

    @Override
    public List<Foothold> getFootholdRandom(Rect rect, int max) {
        return null;
    }

    @Override
    public Iterator<Foothold> iterator() {
        return null;
    }
}
