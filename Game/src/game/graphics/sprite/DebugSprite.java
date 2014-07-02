package game.graphics.sprite;

import game.graphics.sprite.model.AbstractSprite;
import game.util.GameOptions;

import java.awt.*;

/**
 * Created by Semyon Danilov on 02.07.2014.
 */
public class DebugSprite extends AnimatedSprite {

    private AbstractSprite sprite;

    public DebugSprite(final AbstractSprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void play() {
        ((AnimatedSprite) sprite).play();
    }

    @Override
    public void stop() {
        ((AnimatedSprite) sprite).stop();
    }

    @Override
    public boolean isPaused() {
        return ((AnimatedSprite) sprite).isPaused();
    }

    @Override
    public void afterRender(final Graphics graphics, final int x, final int y) {
        graphics.setColor(Color.blue);
        graphics.drawRect(x, y, GameOptions.TILE_SIZE, GameOptions.TILE_SIZE);
    }

    @Override
    public void onRender(final Graphics graphics, final int x, final int y) {
        sprite.onRender(graphics, x, y);
    }

}
