package game.graphics.sprite.hud.model;

import game.core.GameWorld;
import game.graphics.sprite.model.AbstractSprite;

/**
 * Представляет отображаемую на дисплее информацию.
 * Created by max on 7/4/14.
 */
public abstract class Hud extends AbstractSprite {
    protected final int x; // Место, в котором создаётся HUD
    protected final int y; // <--
    protected GameWorld gameWorld;

    public Hud(final int y, final int x, final GameWorld gameWorld) {
        this.y = y;
        this.x = x;
        this.gameWorld = gameWorld;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
