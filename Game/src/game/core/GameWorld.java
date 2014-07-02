package game.core;

import game.gameobject.model.GameObject;
import game.gameobject.unit.Player;
import game.level.model.Level;
import game.util.GameOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class GameWorld {

    private List<GameObject> gameObjects = null;

    private List<GameObject> scheduledForDelete = null;

    private Level currentLevel;

    private Player player;

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public GameWorld() {
        this.gameObjects = new ArrayList<>();
        this.scheduledForDelete = new ArrayList<>();
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void addGameObject(final GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void addGameObjects(final List<? extends GameObject> gameObjects) {
        this.gameObjects.addAll(gameObjects);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void removeGameObject(final GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public void scheduleDelete(final GameObject gameObject) {
        scheduledForDelete.add(gameObject);
    }

    public List<GameObject> getScheduledForDelete() {
        return scheduledForDelete;
    }

    public boolean isOccupied(final int x, final int y) {
        for (final GameObject gameObject : gameObjects) {
            if (gameObject.getTileX() == x && gameObject.getTileY() == y) {
                return true;
            }
        }

        return !getCurrentLevel().getLevelMap().getTile(x, y).isVisitable();
    }


    public GameObject getGameObjectByPos(final int x, final int y) {
        for (final GameObject go: gameObjects) {
            if ((go.getTileX() <= x && (go.getTileX() + GameOptions.TILE_SIZE) >= x)
                    && ((go.getTileY() <= y && (go.getTileY() + GameOptions.TILE_SIZE) >= y))) {
                return go;
            }
        }

        return null;
    }
}
