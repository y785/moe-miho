package benchmark;

import benchmark.odin.MapleFootholdTree;
import moe.maple.miho.point.Point;
import moe.maple.miho.space.PhysicalSpace2D;
import moe.maple.miho.space.array.MoeFootholdArray;
import moe.maple.miho.space.bst.MoeBstFootholdTree;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class PhysicalSpaceBenchmarks {

    private PhysicalSpace2D array;
    private PhysicalSpace2D bst;
    private PhysicalSpace2D quad;

    private MapleFootholdTree odintree;

    private Point[] points;

    @Setup
    public void setup() {
        array = new MoeFootholdArray(Ellinia.LOW, Ellinia.HIGH, Ellinia.FOOTHOLDS);
        bst = PhysicalSpace2D.ofBST(Ellinia.LOW, Ellinia.HIGH, Ellinia.FOOTHOLDS);
        quad = PhysicalSpace2D.ofQuad(Ellinia.LOW, Ellinia.HIGH, Ellinia.FOOTHOLDS);

        odintree = new MapleFootholdTree(Ellinia.LOW, Ellinia.HIGH, Ellinia.FOOTHOLDS);
        // 3 flat, 3 slope, 1 top left, 1 top right
        points = new Point[] { // Todo, stream the footholds to find a list of random points., bigger list.
                Point.of(428, -3530),
                Point.of(428, -3607),
                Point.of(738, -3063 - 90),
                Point.of(583, 144 - 90),
                Point.of(-344, -84 - 90),
                Point.of(541, -2973 - 90),
                Point.of(-969, -4320),
                Point.of(1281, -4320),
        };
    }

    @Benchmark
    @Fork(2)
    @Threads(5)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    public void benchmarkMihoLiterallyAnArray(Blackhole bh) {
        for (int i = 0; i < points.length; i++) {
            bh.consume(array.getFootholdUnderneath(points[i]));
        }
    }
//
//        @Benchmark
//        @Fork(2)
//        @Threads(5)
//        @Warmup(iterations = 2)
//        @Measurement(iterations = 10)
//        public void benchmarkMihoArrayMapped(Blackhole bh) {
//            for (int i = 0; i < points.length; i++) {
//                bh.consume(mapped.getFootholdUnderneath(points[i]));
//            }
//        }

    @Benchmark
    @Fork(2)
    @Threads(5)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    public void benchmarkMihoBst(Blackhole bh) {
        for (int i = 0; i < points.length; i++) {
            bh.consume(bst.getFootholdUnderneath(points[i]));
        }
    }

    @Benchmark
    @Fork(2)
    @Threads(5)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    public void benchmarkMihoQuad(Blackhole bh) {
        for (int i = 0; i < points.length; i++) {
            bh.consume(quad.getFootholdUnderneath(points[i]));
        }
    }

    @Benchmark
    @Fork(2)
    @Threads(5)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    public void benchmarkOdin(Blackhole bh) {
        for (int i = 0; i < points.length; i++) {
            bh.consume(odintree.findBelow(points[i]));
        }
    }
}
