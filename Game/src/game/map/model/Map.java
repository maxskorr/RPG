package game.map.model;

/**
 * Created by Max on 6/28/2014.
 */
public class Map {
    private final int HEIGHT;
    private final int WIDTH;

    public Map(final int height, final int width) {
        HEIGHT = height;
        WIDTH = width;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }
}
