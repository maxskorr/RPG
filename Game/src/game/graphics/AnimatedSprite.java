package game.graphics;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class AnimatedSprite implements Drawable {

    private Map<Integer, Animation> animations = new HashMap<>();

    @Override
    public void beforeRender() {

    }

    @Override
    public void onRender(final Canvas canvas, final int x, final int y) {

    }

}