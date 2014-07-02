package game.core.model;

/**
 * Created by Semyon Danilov on 02.07.2014.
 */
public class Point {

    public final Long x;
    public final Long y;

    public Point(final Long x, final Long y) {
        this.x = x;
        this.y = y;
    }

    public Point addX(final Long x) {
        return new Point(this.x + x, y);
    }

    public Point addY(final Long y) {
        return new Point(x, this.y + y);
    }

    public Point add(final Long x, final Long y) {
        return new Point(this.x + x, this.y + y);
    }

}
