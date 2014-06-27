package game.model;

/**
 * Created by Max on 6/27/2014.
 */
public class GameObject {
    private int x;
    private int y;
    private 

    public void setXY(Integer x, Integer y) {
        if (!x.equals(null))
            this.x = x;
        if (!y.equals(null))
            this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return x;
    }
}
