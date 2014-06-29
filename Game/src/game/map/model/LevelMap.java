package game.map.model;

import java.util.ArrayList;

/**
 * Created by Max on 6/28/2014.
 */
public class LevelMap {
    private final int HEIGHT;
    private final int WIDTH;
    private final ArrayList<ArrayList<Tile>> tiles;

    public LevelMap(ArrayList<ArrayList<Tile>> tiles) {
        HEIGHT = tiles.size();
        WIDTH = tiles.get(0).size();
        this.tiles = tiles;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    public Tile getTile(final int x, final int y) {
        // TODO: invent diff. coordinate systems for tiles and gameobjects(?)
        final int nx = Math.abs(x % WIDTH);
        final int ny = Math.abs(y % HEIGHT);

        if (tiles.size() <= ny || tiles.size() <= 0) {
            throw new NullPointerException("x: " + x + "; y: " + y);
        }

        if (tiles.get(0).size() <= nx || tiles.size() <= 0) {
            throw new NullPointerException("x: " + x + "; y: " + y);
        }

        return tiles.get(ny).get(nx);
    }
}
