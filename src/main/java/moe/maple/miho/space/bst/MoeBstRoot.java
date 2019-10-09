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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

public class MoeBstRoot extends MoeBstNode {

    private MoeBstRoot(MutableRect rect) {
        super(null);
        this.bounds = rect;
        this.rootBounds = MutableRect.of(rect);
    }

    MoeBstRoot(Point low, Point high) {
        this(MutableRect.of(low, high));
    }

    @Override
    public void searchDown(Consumer<Foothold> check, int x, int y, int radius) {
        if (rootBounds.contains(x, y)) {
            if (left != null) left.searchDown(check, x, y, radius);
            if (right != null) right.searchDown(check, x, y, radius);
        }
    }

    @Override
    public synchronized void insert(Foothold fh) {
        insertRaw(fh);
    }

    @Override
    public void draw(Path filePath) throws IOException {
        var img = new BufferedImage(bounds.width() + 2, bounds.height() + 2, BufferedImage.TYPE_INT_ARGB);
        var gfx = img.createGraphics();
        var tx = Math.abs(bounds.x());
        var ty = Math.abs(bounds.y());

        gfx.setStroke(new BasicStroke(1));
        streamNodes().map(n -> (MoeBstNode) n).forEach(node -> {
            var rect = node.rect();
            gfx.setColor(new Color(200, Math.max(100 - node.level() * 5, 0), Math.max(100 - node.level() * 5, 0), 255));
            gfx.drawRect(rect.x() + tx, rect.y() + ty, rect.width(), rect.height());
            gfx.setColor(Color.GREEN);
            node.data().forEach(fh -> {
                gfx.drawLine(fh.x1() + tx, fh.y1() + ty, fh.x2() + tx, fh.y2() + ty);
            });
        });

        ImageIO.write(img, "PNG", filePath.toFile());
    }
}
