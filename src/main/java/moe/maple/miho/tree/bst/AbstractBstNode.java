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

package moe.maple.miho.tree.bst;

import moe.maple.miho.foothold.Foothold;
import moe.maple.miho.rect.MutableRect;
import moe.maple.miho.rect.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class AbstractBstNode<T extends Foothold> implements BstNode<T> {

    protected static final int MAX_DATA_SIZE = 5;

    protected MutableRect bounds;
    protected int level;
    protected BstNode<T> left, right;
    protected List<T> data;

    protected AbstractBstNode(int level) {
        this.level = level;
        this.data = new ArrayList<T>();
    }

    @Override
    public Rect bounds() {
        return bounds;
    }

    @Override
    public abstract T[] data();

    @Override
    public int level() {
        return level;
    }

    @Override
    public BstNode<T> left() {
        return left;
    }

    @Override
    public BstNode<T> right() {
        return right;
    }

    @Override
    public void left(BstNode<T> left) {
        this.left = left;
    }

    @Override
    public void right(BstNode<T> right) {
        this.right = right;
    }

    @Override
    public void search(Consumer<T> check, Predicate<BstNode<T>> pathCheck, int x, int y) {
        if (left != null && pathCheck.test(left))
            left.searchDown(check, x, y);
        if (right != null && pathCheck.test(right))
            right.searchDown(check, x, y);
        data.forEach(check);
    }

    @Override
    public Stream<BstNode<T>> stream() {
        if (left == null && right == null)
            return Stream.of(this);
        if (left == null)
            return Stream.concat(Stream.of(this), Stream.of(right).flatMap(BstNode::stream));
        return Stream.concat(Stream.of(this), Stream.of(left, right)
                .filter(Objects::nonNull)
                .flatMap(BstNode::stream));
    }
}
