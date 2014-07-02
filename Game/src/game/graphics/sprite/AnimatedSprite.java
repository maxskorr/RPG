package game.graphics.sprite;

import game.graphics.Animation;
import game.graphics.sprite.model.AbstractSprite;

import java.awt.*;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class AnimatedSprite extends AbstractSprite {

    final private Animation animation;

    public void play() {
        animation.setPaused(false);
    }

    public void stop() {
        animation.setPaused(true);
    }

    public AnimatedSprite(final Animation animation) {
        this.animation = animation;
    }

    protected AnimatedSprite() {
        this.animation = null;
    }

    @Override
    public void afterRender(final Graphics graphics, final int x, final int y) {

    }

    @Override
    public void onRender(final Graphics graphics, final int x, final int y) {
        graphics.drawImage( animation.next(), x, y, null);
    }

    public boolean isPaused() {
        return animation.isPaused();
    }

}