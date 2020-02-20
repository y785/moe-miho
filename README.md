# Miho
Basic 2d spacial data for maplusitoree. Also includes a cute QuadTree &amp; Binary Search Tree.
I recommend using the Binary Search Tree for footholds as it usually has better results and is more memory efficient.

### Maven
[![CircleCI](https://circleci.com/gh/y785/moe-miho.svg?style=svg)](https://circleci.com/gh/y785/moe-miho)
[![Download](https://api.bintray.com/packages/moe/maple/miho/images/download.svg) ](https://bintray.com/moe/maple/miho/_latestVersion)
```
<dependency>
  <groupId>moe.maple</groupId>
  <artifactId>miho</artifactId>
  <version>1.2.0</version>
</dependency>
```

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
The benchmarks are by no means perfect, but they show some promising results. These were run with a mix of regular points and sloped points using ellinia's footholds. BST is the clear winner and is the 2nd most fault tolerant. The first obviously being the array iteration.
```
Benchmark          Mode  Cnt        Score       Error  Units
Bst               thrpt   20  2153434.103 ± 36622.564  ops/s
Quad              thrpt   20   748475.970 ± 21468.748  ops/s
LiterallyAnArray  thrpt   20   167335.920 ± 59497.594  ops/s
Odin              thrpt   20    22285.210 ±   768.409  ops/s
```

### Images
[Quad/BST Album for Ellinia](https://imgur.com/a/RB26Gw1)
<p align="center">
<img src="https://i.imgur.com/SF6JgQ0.png" tag="BST image of Ellinia" width="200" height="200">
</p>
