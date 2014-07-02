package game.core.model;

/**
 * Created by Semyon Danilov on 02.07.2014.
 */
public class Point {

    public final long x;
    public final long y;

    public Point(final long x, final long y) {
        this.x = x;
        this.y = y;
    }

    public Point addX(final long x) {
        return new Point(this.x + x, y);
    }

    public Point addY(final long y) {
        return new Point(x, this.y + y);
    }

    public Point add(final long x, final long y) {
        return new Point(this.x + x, this.y + y);
    }

}
