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

package benchmark.maps;

import moe.maple.miho.foothold.Foothold;

public final class MuLung {

    private MuLung() {
    }

    public static final Foothold[] FOOTHOLDS = new Foothold[]{
            Foothold.of(2, 1, 0, 3, 5, 90, 51, 180, 51),
            Foothold.of(3, 1, 0, 1, 2, 0, 51, 90, 51),
            Foothold.of(4, 1, 0, 10, 1, -180, 51, -90, 51),
            Foothold.of(5, 1, 0, 2, 6, 180, 51, 270, 51),
            Foothold.of(6, 1, 0, 5, 7, 270, 51, 360, 51),
            Foothold.of(7, 1, 0, 6, 8, 360, 51, 450, 51),
            Foothold.of(8, 1, 0, 7, 9, 450, 51, 540, 51),
            Foothold.of(9, 1, 0, 8, 19, 540, 51, 630, 51),
            Foothold.of(10, 1, 0, 14, 4, -270, 51, -180, 51),
            Foothold.of(11, 1, 0, 12, 13, -540, 51, -450, 51),
            Foothold.of(12, 1, 0, 17, 11, -630, 51, -540, 51),
            Foothold.of(13, 1, 0, 11, 14, -450, 51, -360, 51),
            Foothold.of(14, 1, 0, 13, 10, -360, 51, -270, 51),
            Foothold.of(15, 1, 0, 22, 0, 900, 51, 990, 51),
            Foothold.of(16, 1, 0, 20, 17, -810, 51, -720, 51),
            Foothold.of(17, 1, 0, 16, 12, -720, 51, -630, 51),
            Foothold.of(18, 1, 0, 0, 23, -1080, 51, -990, 51),
            Foothold.of(19, 1, 0, 9, 21, 630, 51, 720, 51),
            Foothold.of(20, 1, 0, 23, 16, -900, 51, -810, 51),
            Foothold.of(21, 1, 0, 19, 22, 720, 51, 810, 51),
            Foothold.of(22, 1, 0, 21, 15, 810, 51, 900, 51),
            Foothold.of(23, 1, 0, 18, 20, -990, 51, -900, 51),
    };
}
