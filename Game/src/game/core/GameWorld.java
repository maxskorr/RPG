package game.core;

import game.graphics.Tile;
import game.model.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class GameWorld {

    private List<GameObject> gameObjects = null;

    private Tile[][] map;

    public GameWorld() {
        this.gameObjects = new ArrayList<GameObject>();
    }

    public void addGameObject(final GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void addGameObjects(final List<? extends GameObject> gameObjects) {
        this.gameObjects.addAll(gameObjects);
    }

    public Tile getTile(final int x, final int y) {
        return map[x][y];
    }

    public Tile[][] getMap() {
        return map;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void removeGameObject(final GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public boolean isOccupied(final int x, final int y) {
        for (final GameObject gameObject : gameObjects) {
            if (gameObject.getX() == x && gameObject.getY() == y) {
                return true;
            }
        }
        return !map[x][y].isVisitable();
    }

}
