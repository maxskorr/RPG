package game.gameobject.model;

import game.core.GameWorld;
import game.graphics.Drawable;
import game.graphics.sprite.model.AbstractSprite;
import game.util.GameOptions;
import game.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max & Edik on 6/27/2014.
 */
public abstract class GameObject {

    private int x; // Реальная координата X, в пикселах
    private int y; // Реальная координата Y, в пикселах
    private List<AbstractSprite> sprites;
    private AbstractSprite currentSprite;
    private GameWorld gameWorld;
    private long timeBuffer = 0; // Буфер времени, позволяющий определить количество итераций физики

    public GameObject(final Integer x, final Integer y, final String spriteFileName, final GameWorld gameWorld) {
        this.x = x;
        this.y = y;
        sprites = new ArrayList<>();

        setGameWorld(gameWorld);
    }

    /**
     * Смещение в пикселях по X относительно начала клетки при рендере
     * @return
     */
    public int getDeltaRenderX() {
        return x - (x / GameOptions.TILE_SIZE) * GameOptions.TILE_SIZE;
    }

    /**
     * Смещение в пикселях по Y относительно начала клетки при рендере
     * @return
     */
    public int getDeltaRenderY() {
       return y - (y / GameOptions.TILE_SIZE) * GameOptions.TILE_SIZE;
    }

    public void setCurrentSprite(final AbstractSprite currentSprite) {
        this.currentSprite = currentSprite;
    }

    /**
     * Установка GameObject'а в начало какой-либо клетки
     * @param x Тайл-координата X
     * @param y Тайл-координата Y
     */
    @Deprecated
    public void setXY(Integer x, Integer y) {
        if (x != null) {
            this.x = x * GameOptions.TILE_SIZE;
        }

        if (y != null) {
            this.y = y * GameOptions.TILE_SIZE;
        }

        if (gameWorld != null) {
            gameWorld.getCurrentLevel().getLevelMap().getTile(getTileX(), getTileY()).trigger(this);
        }
    }

    /**
     * Координата X в пикселах
     * @return
     */
    public int getRealX() {
       return x;
    }

    /**
     * Координата Y в пикселах
     * @return
     */
    public int getRealY() {
        return y;
    }

    /**
     * Изменить координату Y на смещение dy
     * @param dy Смещение по X
     */
    public void changeY(final Integer dy) {
        if (dy != null) {
            this.y += dy;
        }
    }

    /**
     * Изменить координату X на смещение dx
     * @param dx Смещение по X
     */
    public void changeX(final Integer dx) {
        if (dx != null) {
            this.x += dx;
        }
    }

    public void removeSelf() {
        if (gameWorld != null) {
            gameWorld.scheduleDelete(this);
        }
    }

    public Drawable getDrawable() {
        return currentSprite;
    }

    /**
     * Позиция X тайла.
     * @return Координата X
     */
    public int getTileX(){
        return Utils.floorDiv(x, GameOptions.TILE_SIZE);
    }

    /**
     * Позиция Y тайла.
     * @return Координата Y
     */
    public int getTileY() {
        return Utils.floorDiv(y, GameOptions.TILE_SIZE);
    }

    public List<AbstractSprite> getSprites() {
        return sprites;
    }

    public void update(final long deltaTime) {
        timeBuffer += deltaTime;

        if (GameOptions.PHYSICS_ITERATION <= timeBuffer) {
            updatePhysics();
            updateAttributes();
            timeBuffer -= GameOptions.PHYSICS_ITERATION;
        }
    }


    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void putSprite(final AbstractSprite sprite) {
        sprites.add(sprite);

        if (currentSprite == null)
            currentSprite = sprite;
    }

    /**
     * Обработка физики объекта.
     */
    public void updatePhysics() {}

    /**
     * Обновление характеристик объекта (здоровье, мана)
     */
    public void updateAttributes() {}
}
