package game.map.model;

import game.graphics.Tile;

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
        if (tiles.size() <= y || tiles.size() <= 0) {
            throw new NullPointerException();
        }

        if (tiles.get(0).size() <= x || tiles.size() <= 0) {
            throw new NullPointerException();
        }

        return tiles.get(y).get(x);
    }
}
