package game.core;

import game.model.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class GameWorld {

    private List<GameObject> gameObjects = null;

    public GameWorld() {
        this.gameObjects = new ArrayList<GameObject>();
    }

    public void addObject(final GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    private <T extends Enum<T>> void check(final Class<T> ars) {

    }

    public void addObjects(final List<? extends GameObject> gameObjects) {
        this.gameObjects.addAll(gameObjects);
    }

}
