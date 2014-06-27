package game.graphics;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class AnimatedSprite extends AbstractSprite implements Drawable {

    final private Animation animation;

    public AnimatedSprite(final Animation animation) {
        this.animation = animation;
    }

    @Override
    public void beforeRender() {

    }

    @Override
    public void onRender(final Canvas canvas, final int x, final int y) {

    }

}