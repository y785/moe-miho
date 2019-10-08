# Miho
Basic 2d spacial data for maplusitoree. Also includes a cute QuadTree &amp; Binary Search Tree.


Odin's quadtree is quite bad. Really bad. In fact, it's performance is less than just simply iterating an array from my baseline benchmarks. This has plagued every source for a long time now.
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

Miho's quadtree,and binary search tree, while not perfect, are slightly better. Example:
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

## Benchmarks
```
Benchmark              Mode  Cnt       Score       Error  Units
MihoQuad              thrpt   20  735644.743 ±  8855.393  ops/s
MihoBst               thrpt   20  716753.216 ± 39950.476  ops/s
LiterallyAnArray      thrpt   20  239952.250 ± 11306.816  ops/s
Odin                  thrpt   20   22399.357 ±   424.996  ops/s
```
