package game.model;

import game.core.GameWorld;
import game.graphics.AbstractSprite;
import game.graphics.Sprite;
import game.util.GameOptions;
import game.util.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max & Edik on 6/27/2014.
 */
public abstract class GameObject {
    private int x;
    private int y;
    private List<AbstractSprite> sprites;
    private GameWorld gameWorld;

    public GameObject(final Integer x, final Integer y, final String title, final GameWorld gameWorld) {
        this.x = x;
        this.y = y;
        sprites = new ArrayList<>();

        final int totalAnimations = ResourceManager.getAnimationsNumberByTitle(title);

        for (int i = 0; i < totalAnimations; i++) {
            putSprite( ResourceManager.getSprite(title, i) );
        }

        setGameWorld(gameWorld);
    }

    public void setXY(Integer x, Integer y) {
        if (!x.equals(null))
            this.x = x;
        if (!y.equals(null))
            this.y = y;

        gameWorld.getTile(getX(), getY()).trigger(this);
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
