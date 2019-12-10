package test;

import benchmark.maps.Ellinia;
import moe.maple.miho.rect.MutableRect;
import moe.maple.miho.rect.PackedRect;
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

    @Test
    void packedCenter() {
        var rect = PackedRect.of(0, 0, 3, 3);
        assert (PackedRect.x(rect) == 0);
        assert (PackedRect.y(rect) == 0);
        assert (PackedRect.w(rect) == 3);
        assert (PackedRect.h(rect) == 3);
        rect = PackedRect.center(rect, 2, 2);
        assert (PackedRect.x(rect) == 1);
        assert (PackedRect.y(rect) == 1);
    }

    @Test
    void packedUnion() {
        var rect = PackedRect.of(0, 0, 3, 3);
        assert (PackedRect.x(rect) == 0 && PackedRect.y(rect) == 0 && PackedRect.w(rect) == 3 && PackedRect.h(rect) == 3);

        rect = PackedRect.unionR(rect, PackedRect.of(-1, -1, 3, 3));
        assert (PackedRect.x(rect) == -1);
        assert (PackedRect.y(rect) == -1);
        assert (PackedRect.w(rect) == 4);
        assert (PackedRect.h(rect) == 4);

        rect = PackedRect.union(rect, 5, 5);
        assert (PackedRect.w(rect) == 6);
        assert (PackedRect.h(rect) == 6);

        rect = PackedRect.union(rect, -152, -1416);

        for (var fh : Ellinia.FOOTHOLDS) {
            rect = PackedRect.union(rect, fh);
            assert (PackedRect.intersects(rect, fh));
        }
    }

}
