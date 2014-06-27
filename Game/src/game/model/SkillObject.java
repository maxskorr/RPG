package game.model;

import game.core.GameWorld;

/**
 * Created by Max on 6/27/2014.
 *
 * Описывает всё что касается внешнего вида скилла.
 */
public class SkillObject extends GameObject {

    public SkillObject(final Integer x, final Integer y, final String spriteFileName, final GameWorld gameWorld) {
        super(x, y, spriteFileName, gameWorld);
    }

    @Override
    public void update() {
        //
    }
}
