package game.core.model;

import game.util.Pool;

/**
 * Created by Semyon Danilov on 02.07.2014.
 */
public class Point implements Pool.Poolable<Point> {

    private static final Pool<Point> pool = new Pool<Point>(50, new Pool.Factory<Point>() {
        @Override
        public Point newInstance() {
            return new Point();
        }
    });

    private long x;
    private long y;

    public static Point newPoint(final long x, final long y) {
        Point p = pool.get();
        p.setX(x);
        p.setY(y);
        return p;
    }

    public void recycle() {
        pool.free(this);
    }

    public long getX() {
        return x;
    }

    private void setX(final long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    private void setY(final long y) {
        this.y = y;
    }

    public Point addX(final long x) {
        return newPoint(this.x + x, y);
    }

    public Point addY(final long y) {
        return newPoint(x, this.y + y);
    }

    public Point add(final long x, final long y) {
        return newPoint(this.x + x, this.y + y);
    }

    @Override
    public void clear() {

    }

}
