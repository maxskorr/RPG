package game.model;

import game.graphics.Tile;

/**
 * Created by Max on 6/27/2014.
 */
public abstract class GameObject {
    private int x;
    private int y;
    private Tile tile;

    public GameObject(Integer x, Integer y, Tile tile) {
        setXY(x, y);
        setTile(tile);
    }

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

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public abstract void update();
}
