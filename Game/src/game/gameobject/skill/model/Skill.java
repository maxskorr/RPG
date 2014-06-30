package game.gameobject.skill.model;

import game.gameobject.model.GameObject;
import game.gameobject.unit.model.Unit;

/**
 * Created by Max on 6/27/2014.
 * <p/>
 * Описывает скилл и его действие на игровой объект.
 */
public abstract class Skill extends GameObject {

    public Skill(final String spriteFileName) {
        super(0, 0, spriteFileName, null);
    }

    @Override
    public void update(final long deltaTime) {
        //
    }

    public abstract void act(final Unit go);
}
