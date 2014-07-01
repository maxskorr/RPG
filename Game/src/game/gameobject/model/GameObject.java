package game.gameobject.model;

import game.core.GameWorld;
import game.graphics.Drawable;
import game.graphics.sprite.model.AbstractSprite;
import game.util.GameOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max & Edik on 6/27/2014.
 */
public abstract class GameObject {

    private int x;
    private int y;
    private List<AbstractSprite> sprites;
    private AbstractSprite currentSprite;
    private GameWorld gameWorld;
    private long timeBuffer = 0; // Буфер времени, позволяющий определить количество итераций физики

    public GameObject(final Integer x, final Integer y, final String spriteFileName, final GameWorld gameWorld) {
        this.x = x;
        this.y = y;
        sprites = new ArrayList<>();

//        final int totalAnimations = ResourceManager.getAnimationsNumberByTitle(spriteFileName);
//
//        for (int i = 0; i < totalAnimations; i++) {
//            putSprite(ResourceManager.getSprite(spriteFileName, i));
//        }
//
//        setCurrentSprite(sprites.get(0));

        setGameWorld(gameWorld);
    }

    public void setCurrentSprite(final AbstractSprite currentSprite) {
        this.currentSprite = currentSprite;
    }

    public void setXY(Integer x, Integer y) {
        if (x != null) {
            this.x = x;
        }
        if (y != null) {
            this.y = y;
        }

        if (gameWorld != null)
            gameWorld.getCurrentLevel().getLevelMap().getTile(getX(), getY()).trigger(this);
    }

    public void removeSelf() {
        if (gameWorld != null) {
            gameWorld.scheduleDelete(this);
        }
    }

    public Drawable getDrawable() {
        return currentSprite;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<AbstractSprite> getSprites() {
        return sprites;
    }

    public void update(final long deltaTime) {
        timeBuffer += deltaTime;

        if (GameOptions.PHYSICS_ITERATION <= timeBuffer) {
            updatePhysics();
            timeBuffer -= GameOptions.PHYSICS_ITERATION;
        }
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void putSprite(AbstractSprite sprite) {
        sprites.add(sprite);
    }

    /**
     * Обработка физики объекта
     */
    public void updatePhysics() {};
}
