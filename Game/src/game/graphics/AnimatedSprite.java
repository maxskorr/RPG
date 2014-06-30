package game.graphics;

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

    @Override
    public void beforeRender() {
    }

    @Override
    public void onRender(final Graphics graphics, final int x, final int y) {
        graphics.drawImage( animation.next(), x, y, null);
    }

    public boolean isPaused() {
        return animation.isPaused();
    }

}