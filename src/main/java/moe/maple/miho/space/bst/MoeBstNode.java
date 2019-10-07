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
import moe.maple.miho.tree.bst.AbstractBstNode;

import java.util.Comparator;
import java.util.function.Consumer;

public class MoeBstNode extends AbstractBstNode<Foothold> {

    protected MoeBstNode parent;
    protected MutableRect rect;

    public MoeBstNode(MoeBstNode parent) {
        super(parent == null ? 0 : parent.level + 1);
        this.parent = parent;
    }

    @Override
    public Foothold[] data() {
        return data.toArray(Foothold[]::new);
    }

    @Override
    public synchronized void insert(Foothold fh) {
        if (rect == null || data == null || data.size() == 0) {
            data.add(fh);
            resize(fh);
        } else if (canJoin(fh)) {
            data.add(fh);
            resize(fh);
        } else {
            insertRaw(fh);
        }
    }

    @Override
    public void searchDown(Consumer<Foothold> check, int x, int y) {
        // todo optimize
        if (left != null) {
            var lb = left.bounds();
            if (lb.compareX(x) == 0 && lb.compareY(y) != 1)
                left.searchDown(check, x, y);
        }
        if (right != null) {
            var rb = right.bounds();
            if (rb.compareX(x) == 0 && rb.compareY(y) != 1)
                right.searchDown(check, x, y);
        }
        if (rect.compareX(x) == 0 && rect.compareY(y) != 1) {
            data.forEach(check);
        }
    }

    private boolean isFull() {
        return data.size() == 5;
    }

    private boolean canJoin(Foothold fh) {
        if (!isFull()) return true;
        if (!rect.contains(fh)) return false;

        var center = Point.ofCenter(rect);
        var max = data.stream().max(Comparator.comparing(f -> f.distance(center))).orElseThrow();
        if (fh.distance(center) > max.distance(center))
            return false;

        var iter = data.iterator();
        while (iter.hasNext()) {
            var rm = iter.next();
            if (rm.id() == max.id()) {
                iter.remove();
                insertRaw(rm);
                return true;
            }
        }

        return false;
    }

    private void resize(Foothold fh) {
        if (rect == null) {
            rect = MutableRect.of(fh);
            bounds = MutableRect.of(fh);
        } else rect.union(fh);
        resizeBounds(fh);
    }

    private void resizeBounds(Foothold fh) {
        bounds.union(fh);
        if (parent != null)
            parent.resizeBounds(fh);
    }

    protected void insertRaw(Foothold fh) {
        // todo this is bad
        var angle = Point.angle(Point.ofCenterJoined(rect), fh.closest(Point.ofCenterJoined(rect)));
        if (angle < 0) angle += 180;
        if (angle >= 90) {
            if (left == null) left = new MoeBstNode(this);
            left.insert(fh);
        } else {
            if (right == null) right = new MoeBstNode(this);
            right.insert(fh);
        }
    }

}
