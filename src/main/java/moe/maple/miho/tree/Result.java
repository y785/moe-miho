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

package moe.maple.miho.tree;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Result<E> {
    E value;

    public Result(E value) {
        this.value = value;
    }

    public void ifPresent(Consumer<E> action) {
        if (value != null) action.accept(value);
    }

    public boolean isNull() {
        return value == null;
    }

    public E get() {
        return value;
    }

    public void set(E value) {
        this.value = value;
    }

    public void setIf(Predicate<E> check, E value) {
        if (this.value == null)
            this.value = value;
        else if (check.test(this.value))
            this.value = value;
    }

    public void map(Function<E, E> func, E other) {
        this.value = value == null ? other : func.apply(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (value == null) return super.equals(obj);
        return value.equals(obj);
    }

    @Override
    public String toString() {
        if (value == null) return super.toString();
        return value.toString();
    }

    public static <E> Result<E> of(E value) {
        return new Result<E>(value);
    }
}
