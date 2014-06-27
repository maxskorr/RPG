package game.model;

import game.core.GameWorld;
import game.graphics.Sprite;

/**
 * Created by Max on 6/27/2014.
 *
 * Описывает всё что касается внешнего вида скилла.
 */
public class SkillObject extends GameObject {

    public SkillObject(final Integer x, final Integer y, final Sprite sprite, final GameWorld gameWorld) {
        super(x, y, sprite, gameWorld);
    }

    @Override
    public void update() {
        //
    }
}
