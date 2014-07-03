package game.core;

import game.gameobject.model.GameObject;
import game.gameobject.unit.Player;
import game.level.model.Level;
import game.map.model.LevelMap;
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
    private List<GameObject> scheduledForAdd;

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public GameWorld() {
        this.gameObjects = new ArrayList<>();
        this.scheduledForDelete = new ArrayList<>();
        this.scheduledForAdd = new ArrayList<>();
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

    public boolean isOccupiedByTilePos(final int x, final int y) {
        for (final GameObject gameObject : gameObjects) {
            if ((gameObject.getTileX() == x && gameObject.getTileY() == y)) {
                return true;
            }
        }

        return !getCurrentLevel().getLevelMap().getTileByTilePos(x, y).isVisitable();
    }

    public boolean isOccupiedByRealPos(final GameObject go, final int x, final int y) {
        for (final GameObject gameObject : gameObjects) {
            if (go == gameObject)
                continue;

            final int OLX = x;
            final int OTY = y;
            final int ORX = OLX + GameOptions.TILE_SIZE;
            final int OBY = OTY + GameOptions.TILE_SIZE;
            final int GLX = gameObject.getRealX();
            final int GTY = gameObject.getRealY();
            final int GRX = GLX + GameOptions.TILE_SIZE;
            final int GBY = GTY + GameOptions.TILE_SIZE;

            boolean xCollision = false;
            boolean yCollision = false;

            if ((ORX > GLX) && (OLX < GRX)) {
                xCollision = true;
            }

            if ((OBY > GTY) && (OTY < GBY)) {
                yCollision = true;
            }

            if (xCollision && yCollision) {
                return true;
            }
        }

        final LevelMap levelMap = getCurrentLevel().getLevelMap();

        return !levelMap.getTileByRealPos(x, y).isVisitable() ||
               !levelMap.getTileByRealPos(x + GameOptions.TILE_SIZE - 1, y + GameOptions.TILE_SIZE - 1).isVisitable() ||
               !levelMap.getTileByRealPos(x, y + GameOptions.TILE_SIZE - 1).isVisitable() ||
               !levelMap.getTileByRealPos(x + GameOptions.TILE_SIZE - 1, y).isVisitable();
        // -1 - магическое число, может измениться если переписать обработку физики юнита
    }


    public GameObject getGameObjectByTilePos(final int x, final int y) {
        for (final GameObject go: gameObjects) {
            if ((go.getTileX() <= x && (go.getTileX() + 1) >= x)
                    && ((go.getTileY() <= y && (go.getTileY() + 1) >= y))) {
                return go;
            }
        }

        return null;
    }

    public GameObject getGameObjectByRealPos(final int x, final int y) {
        for (final GameObject go: gameObjects) {
            if ((go.getRealX() <= x && (go.getRealX() + GameOptions.TILE_SIZE) >= x)
                    && ((go.getRealY() <= y && (go.getRealY() + GameOptions.TILE_SIZE) >= y))) {
                return go;
            }
        }

        return null;
    }

    public List<GameObject> getScheduledForAdd() {
        return scheduledForAdd;
    }

    public void scheduleAdd(final GameObject gameObject) {
        if (scheduledForAdd == null)
            throw new NullPointerException();

        scheduledForAdd.add(gameObject);
    }
}