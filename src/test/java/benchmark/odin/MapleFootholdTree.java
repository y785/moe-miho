package benchmark.odin;

import moe.maple.miho.foothold.Foothold;
import moe.maple.miho.point.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Matze
 */
public class MapleFootholdTree{

    private MapleFootholdTree nw = null;
    private MapleFootholdTree ne = null;
    private MapleFootholdTree sw = null;
    private MapleFootholdTree se = null;
    private List<Foothold> footholds = new LinkedList<Foothold>();
    private Point low;
    private Point high;
    private Point center;
    private int depth = 0;
    private static int maxDepth = 8;
    private int maxDropX;
    private int minDropX;

    public MapleFootholdTree(){
        super();
    }

    public MapleFootholdTree(Point low, Point high, Foothold[] footholds){
        this.low = low;
        this.high = high;
        center = Point.of((high.x() - low.x()) / 2, (high.y() - low.y()) / 2);

        for (var foothold : footholds) {
            this.insert(foothold);
        }
    }

    public MapleFootholdTree(Point low, Point high, int depth){
        this.low = low;
        this.high = high;
        this.depth = depth;
        center = Point.of((high.x() - low.x()) / 2, (high.y() - low.y()) / 2);
    }

    public void check(MapleFootholdTree tree) {
        if (tree.ne != null) {
            check(tree.ne);
            check(tree.nw);
            check(tree.se);
            check(tree.sw);
        }
    }

    public Foothold getFootholdByID(int id){
        for(Foothold fh : footholds){
            if(fh.id() == id) return fh;
        }
        return null;
    }

    public void insert(Foothold fh){
        if(depth == 0){
            if(fh.x1() > maxDropX){
                maxDropX = fh.x1();
            }
            if(fh.x1() < minDropX){
                minDropX = fh.x1();
            }
            if(fh.x2() > maxDropX){
                maxDropX = fh.x2();
            }
            if(fh.x2() < minDropX){
                minDropX = fh.x2();
            }
        }
        if(depth == maxDepth ||
                (fh.x1() >= low.x() &&
                        fh.x2() <= high.y() &&
                        fh.y1() >= low.y() &&
                        fh.y2() <= high.y())){
            footholds.add(fh);
        }else{
            if(nw == null){
                nw = new MapleFootholdTree(low, center, depth + 1);
                ne = new MapleFootholdTree(Point.of(center.x(), low.y()), Point.of(high.x(), center.y()), depth + 1);
                sw = new MapleFootholdTree(Point.of(low.x(), center.y()), Point.of(center.x(), high.y()), depth + 1);
                se = new MapleFootholdTree(center, high, depth + 1);
            }
            if(fh.x2() <= center.x() && fh.y2() <= center.y()){
                nw.insert(fh);
            }else if(fh.x1() > center.x() && fh.y2() <= center.y()){
                ne.insert(fh);
            }else if(fh.x2() <= center.x() && fh.y1() > center.y()){
                sw.insert(fh);
            }else{
                se.insert(fh);
            }
        }
    }

    public final List<Foothold> getAllRelevants(){
        return getAllRelevants(new LinkedList<Foothold>());
    }

    private List<Foothold> getAllRelevants(final List<Foothold> list){
        for(Foothold fh : footholds){
            if(!list.contains(fh)) list.add(fh);
        }
        if(nw != null){
            nw.getAllRelevants(list);
            ne.getAllRelevants(list);
            sw.getAllRelevants(list);
            se.getAllRelevants(list);
        }
        return list;
    }

    public List<Foothold> getRelevants(Point p){
        return getRelevants(p, new LinkedList<Foothold>());
    }

    public List<Foothold> getRelevants(Point p, List<Foothold> list){
        list.addAll(footholds);
        if(nw != null){
            if(p.x() <= center.x() && p.y() <= center.y()){
                nw.getRelevants(p, list);
            }else if(p.x() > center.x() && p.y() <= center.y()){
                ne.getRelevants(p, list);
            }else if(p.x() <= center.x() && p.y() > center.y()){
                sw.getRelevants(p, list);
            }else{
                se.getRelevants(p, list);
            }
        }
        return list;
    }

    private Foothold findWallR(Point low, Point high){
        Foothold ret;
        for(Foothold f : footholds){
            if(f.isWall() && f.x1() >= low.x() && f.x1() <= high.x() && f.y1() >= low.y() && f.y2() <= low.y()) return f;
        }
        if(nw != null){
            if(low.x() <= center.x() && low.y() <= center.y()){
                ret = nw.findWallR(low, high);
                if(ret != null) return ret;
            }
            if((low.x() > center.x() || high.x() > center.x()) && low.y() <= center.y()){
                ret = ne.findWallR(low, high);
                if(ret != null) return ret;
            }
            if(low.x() <= center.x() && low.y() > center.y()){
                ret = sw.findWallR(low, high);
                if(ret != null) return ret;
            }
            if((low.x() > center.x() || high.x() > center.x()) && low.y() > center.y()){
                ret = se.findWallR(low, high);
                if(ret != null) return ret;
            }
        }
        return null;
    }

    public Foothold findWall(Point low, Point high){
        if(low.y() != high.y()){ throw new IllegalArgumentException(); }
        return findWallR(low, high);
    }

    public Foothold findBelow(Point p){
        List<Foothold> relevants = getRelevants(p);
        List<Foothold> xMatches = new LinkedList<Foothold>();
        for(Foothold fh : relevants){
            if(fh.x1() <= p.x() && fh.x2() >= p.x()){
                xMatches.add(fh);
            }
        }
        Collections.sort(xMatches, (a, b) -> {
            if (a.y2() < b.y1())
                return -1;
            if (a.y1() > b.y2())
                return 1;
            return 0;
        });
        for(Foothold fh : xMatches){
            if(!fh.isWall() && fh.y1() != fh.y2()) {
                int calcY;
                double s1 = Math.abs(fh.y2() - fh.y1());
                double s2 = Math.abs(fh.x2() - fh.x1());
                double s4 = Math.abs(p.x() - fh.x1());
                double alpha = Math.atan(s2 / s1);
                double beta = Math.atan(s1 / s2);
                double s5 = Math.cos(alpha) * (s4 / Math.cos(beta));
                if(fh.y2() < fh.y1()) {
                    calcY = fh.y1() - (int) s5;
                }else{
                    calcY = fh.y1() + (int) s5;
                }
                if(calcY >= p.y()){
                    relevants = null;
                    xMatches = null;
                    return fh;
                }
            }else if(!fh.isWall()){
                if(fh.y1() >= p.y()){
                    relevants = null;
                    xMatches = null;
                    return fh;
                }
            }
        }
        relevants = null;
        xMatches = null;
        return null;
    }

    public Point getPoint1(){
        return low;
    }

    public Point getPoint2(){
        return high;
    }

    public int x1(){
        return low.x();
    }

    public int x2(){
        return high.x();
    }

    public int y1(){
        return low.y();
    }

    public int y2(){
        return high.y();
    }

    public int getMaxDropX(){
        return maxDropX;
    }

    public int getMinDropX(){
        return minDropX;
    }
}

