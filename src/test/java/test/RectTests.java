package test;

import benchmark.Ellinia;
import moe.maple.miho.rect.MutableRect;
import moe.maple.miho.rect.Rect;
import org.junit.jupiter.api.Test;

public class RectTests {

    @Test
    void bounds() {
        var rect = MutableRect.of(0, 0, 2, 2);
        assert(rect.x() == 0 && rect.y() == 0 && rect.width() == 2 && rect.height() == 2);
        rect.bounds(2, 2, 2, 2);
        assert(rect.x() == 2);
        assert(rect.y() == 2);
        assert(rect.width() == 2);
        assert(rect.height() == 2);
    }

    @Test
    void center() {
        var rect = MutableRect.of(0, 0, 3, 3);
        assert(rect.x() == 0 && rect.y() == 0 && rect.width() == 3 && rect.height() == 3);
        rect.center(2, 2);
        assert(rect.x() == 1);
        assert(rect.y() == 1);
    }

    @Test
    void union() {
        var rect = MutableRect.of(0, 0, 3, 3);
        assert(rect.x() == 0 && rect.y() == 0 && rect.width() == 3 && rect.height() == 3);

        rect.union(Rect.of(-1, -1, 3, 3));
        assert(rect.x() == -1);
        assert(rect.y() == -1);
        assert(rect.width() == 4);
        assert(rect.height() == 4);

        rect.union(5, 5);
        assert(rect.width() == 6);
        assert(rect.height() == 6);

        rect.union(-152, -1416);

        for (var fh : Ellinia.FOOTHOLDS) {
            rect.union(fh);
            assert(rect.intersects(fh));
        }
    }

}
