package game.gameobject.skill.model;

import game.core.GameWorld;
import game.gameobject.model.GameObject;

/**
 * Created by Max on 6/27/2014.
 *
 * Описывает скилл и его действие на игровой объект.
 */
public abstract class Skill extends GameObject {

    public Skill(final Integer x, final Integer y, final String spriteFileName, final GameWorld gameWorld) {
        super(x, y, spriteFileName, gameWorld);
    }

    public abstract void act(final GameObject go);
}
