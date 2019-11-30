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

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractBstNode<T extends Foothold> implements BstNode<T> {

    protected static final int MAX_DATA_SIZE = 5;

    protected int level;
    protected List<T> data;

    protected AbstractBstNode(int level) {
        this.level = level;
        this.data = new ArrayList<T>();
    }

    @Override
    public void draw(Path filePath) throws IOException {
        throw new UnsupportedOperationException("Drawing a subquad isn't supported, yet");
    }

    @Override
    public Collection<T> data() {
        return data;
    }

    @Override
    public int level() {
        return level;
    }

    @Override
    public Stream<BstNode<T>> streamNodes() {
        var s1 = Stream.of(left(), right()).filter(Objects::nonNull);
        return Stream.concat(s1.flatMap(BstNode::streamNodes), Stream.of(this));
    }
}
