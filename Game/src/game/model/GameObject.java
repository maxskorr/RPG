package game.model;

import game.core.GameWorld;
import game.graphics.Sprite;

/**
 * Created by Max on 6/27/2014.
 */
public abstract class GameObject {

    private int x;
    private int y;
    private Sprite sprite;
    private GameWorld gameWorld;

    public GameObject(final Integer x, final Integer y, final Sprite sprite, final GameWorld gameWorld) {
        this.x = x;
        this.y = y;

        setSprite(sprite);
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

    public int getX() {
        return x;
    }

    public int getY() {
        return x;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public abstract void update();

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
