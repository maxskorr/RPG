package game.model;

import game.core.GameWorld;
import game.graphics.AbstractSprite;
import game.graphics.Drawable;
import game.util.ResourceManager;

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

    public GameObject(final Integer x, final Integer y, final String spriteFileName, final GameWorld gameWorld) {
        this.x = x;
        this.y = y;
        sprites = new ArrayList<>();

        final int totalAnimations = ResourceManager.getAnimationsNumberByTitle(spriteFileName);

        for (int i = 0; i < totalAnimations; i++) {
            putSprite(ResourceManager.getSprite(spriteFileName, i));
        }

        currentSprite = sprites.get(0);

        setGameWorld(gameWorld);
    }

    public void setXY(Integer x, Integer y) {
        if (x != null) {
            this.x = x;
        }
        if (y != null) {
            this.y = y;
        }
        gameWorld.getTile(getX(), getY()).trigger(this);
    }

    public Drawable getDrawable() {
        return currentSprite;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return x;
    }

    public List<AbstractSprite> getSprites() {
        return sprites;
    }

    public abstract void update();

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void putSprite(AbstractSprite sprite) {
        sprites.add(sprite);
    }
}
