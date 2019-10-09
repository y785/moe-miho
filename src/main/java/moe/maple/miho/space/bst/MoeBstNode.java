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
import moe.maple.miho.line.Line;
import moe.maple.miho.point.Point;
import moe.maple.miho.rect.MutableRect;
import moe.maple.miho.rect.Rect;
import moe.maple.miho.tree.bst.AbstractBstNode;
import moe.maple.miho.tree.bst.BstNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MoeBstNode extends AbstractBstNode<Foothold> {

    protected MutableRect bounds;
    protected MutableRect rootBounds;

    protected MoeBstNode parent;
    protected MoeBstNode left, right;

    public MoeBstNode(MoeBstNode parent) {
        super(parent == null ? 0 : parent.level + 1);
        this.parent = parent;
    }

    @Override
    public Rect bounds() {
        return bounds;
    }

    @Override
    public Point low() {
        return Point.ofBottomLeft(rootBounds);
    }

    @Override
    public Point high() {
        return Point.ofTopRight(rootBounds);
    }

    @Override
    public BstNode<Foothold> parent() {
        return parent;
    }

    @Override
    public BstNode<Foothold> left() {
        return left;
    }

    @Override
    public BstNode<Foothold> right() {
        return right;
    }

    @Override
    public synchronized void insert(Foothold fh) {
        if (bounds == null || data == null || data.size() == 0) {
            if (data == null) data = new ArrayList<>();
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
    public void search(Consumer<Foothold> check, Predicate<BstNode<Foothold>> pathCheck, int x, int y) {
        if (left != null && pathCheck.test(left))
            left.search(check, pathCheck, x, y);
        if (right != null && pathCheck.test(right))
            right.search(check, pathCheck, x, y);
        data.forEach(check);
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
        if (bounds.compareX(x) == 0 && bounds.compareY(y) != 1) {
            data.forEach(check);
        }
    }

    private boolean isFull() {
        return data.size() == 5;
    }

    public MutableRect getRootBounds() {
        return rootBounds;
    }

    public void left(MoeBstNode left) {
        this.left = left;
    }

    public void right(MoeBstNode right) {
        this.right = right;
    }

    private boolean canJoin(Foothold fh) {
        if (!isFull()) return true;
        if (!bounds.contains(fh)) return false;

        // Find the line furthest from the center of this rect and move it out.
        var center = Point.ofCenter(bounds);
        var max = data.stream().max(Comparator.comparing(f -> f.distance(center))).orElseThrow();
        if (fh.distance(center) > max.distance(center))
            return false;

        var iter = data.iterator();
        while (iter.hasNext()) {
            var rm = iter.next();
            if (rm.id() == max.id()) {
                iter.remove();
                insertRaw(rm);
                var low = Line.min(data);
                var high = Line.max(data);
                this.bounds = MutableRect.of(low, high);
                return true; // Found and removed, we have free space now!
            }
        }

        return false;
    }

    private void resize(Foothold fh) {
        if (bounds == null) {
            this.bounds = MutableRect.of(fh);
            this.rootBounds = MutableRect.of(fh);
        } else this.bounds.union(fh);
        resizeBounds(fh);
    }

    private void resizeBounds(Foothold fh) {
        rootBounds.union(fh);
        if (parent != null)
            parent.resizeBounds(fh);
    }

    void insertRaw(Foothold fh) {
        var cen = bounds.cx();
        var cls = Point.x(fh.closest(bounds.cj()));
        if (cls <= cen) {
            if (left == null) left = new MoeBstNode(this);
            left.insert(fh);
        } else {
            if (right == null) right = new MoeBstNode(this);
            right.insert(fh);
        }
    }

    public Rect rect() {
        return bounds;
    }

}
