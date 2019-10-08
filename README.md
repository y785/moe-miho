# Miho
Basic 2d spacial data for maplusitoree. Also includes a cute QuadTree &amp; Binary Search Tree.
I recommend using the Binary Search Tree for footholds as it usually has better results and is more memory efficient.

### Why?
Odin's quadtree is quite bad. Really bad. In fact, just simply iterating an array shows higher benchmark scores. This has plagued every source for a long time now.
```
 public Foothold findBelow(Point p){
        List<Foothold> relevants = getRelevants(p);
        List<Foothold> xMatches = new LinkedList<Foothold>();
        for(Foothold fh : relevants){
            if(fh.x1() <= p.x() && fh.x2() >= p.x()){
                xMatches.add(fh);
            }
        }
        ....
```

Miho's quadtree and bst are better. Example:
```
public Foothold getFootholdUnderneath(int x, int y) {
        var result = Result.of((Foothold) null);

        root.searchDown(match -> {
            if (!match.isWall() && match.below(x, y))
                result.setIf(res -> res.compareY(match) == 1, match);
        }, x, y);

        return result.get();
}
```

### Benchmarks
The benchmarks are by no means perfect, but they show some promising results. These were run with a mix of regular points and sloped points using ellinia's footholds.
```
Benchmark              Mode  Cnt       Score       Error  Units
MihoQuad              thrpt   20  735644.743 ±  8855.393  ops/s
MihoBst               thrpt   20  716753.216 ± 39950.476  ops/s
LiterallyAnArray      thrpt   20  239952.250 ± 11306.816  ops/s
Odin                  thrpt   20   22399.357 ±   424.996  ops/s
```

### Plans Going forward
>Points

Points in maplu are shorts, so we can store x/y as a single int. This will benefit us, as this means Lines and Rects can be stored as a single long. This will be useful for movement processing and packet reading.
