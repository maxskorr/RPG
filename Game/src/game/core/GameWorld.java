package game.core;

import game.gameobject.model.GameObject;
import game.gameobject.unit.Player;
import game.graphics.Drawable;
import game.level.model.Level;
import game.map.model.LevelMap;
import game.util.GameOptions;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class GameWorld {

    private Set<GameObject> gameObjects = null;

    private Set<GameObject> scheduledForDeleteGameObjects = null;

    private Set<GameObject> scheduledForAddGameObjects;

        private Set<Drawable> drawables = null;

        private Set<Drawable> scheduledForDeleteDrawables = null;


        private Set<Drawable> scheduledForAddDrawables = null;

    private Level currentLevel;

    private Player player;

    private Player secondPlayer;

    public GameWorld() {
        this.gameObjects = new LinkedHashSet<>();
        this.scheduledForDeleteGameObjects = new LinkedHashSet<>();
        this.scheduledForAddGameObjects = new LinkedHashSet<>();

        this.drawables = new LinkedHashSet<>();
        this.scheduledForDeleteDrawables = new LinkedHashSet<>();
        this.scheduledForAddDrawables = new LinkedHashSet<>();
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(final Player secondPlayer) {
        this.secondPlayer = secondPlayer;
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

    public Set<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void removeGameObject(final GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public void scheduleDeleteGameObject(final GameObject gameObject) {
        scheduledForDeleteGameObjects.add(gameObject);
    }

    public Set<GameObject> getScheduledForDeleteGameObjects() {
        return scheduledForDeleteGameObjects;
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

    public Set<GameObject> getScheduledForAddGameObjects() {
        return scheduledForAddGameObjects;
    }

    public void scheduleAddGameObject(final GameObject gameObject) {
        if (scheduledForAddGameObjects == null)
            throw new NullPointerException();

        scheduledForAddGameObjects.add(gameObject);
    }

    public void scheduledForAddDrawable(final Drawable drawable) {
        if (scheduledForAddDrawables == null)
            throw new NullPointerException();

        scheduledForAddDrawables.add(drawable);
    }

    public void scheduledForDeleteDrawable(final Drawable drawable) {
        scheduledForDeleteDrawables.add(drawable);
    }

    public Set<Drawable> getScheduledForDeleteDrawables() {
        return scheduledForDeleteDrawables;
    }

    public void removeDrawable(final Drawable drawable) {
        if (drawables == null)
            throw new NullPointerException();

        drawables.remove(drawable);
    }

    public Set<Drawable> getDrawables() {
        return drawables;
    }

    public Set<Drawable> getScheduledForAddDrawables() {
        return scheduledForAddDrawables;
    }

    public void addDrawable(final Drawable drawable) {
        if (drawables == null)
            throw new NullPointerException();

        drawables.add(drawable);
    }
}
