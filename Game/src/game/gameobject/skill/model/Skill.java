package game.gameobject.skill.model;

import game.core.GameWorld;
import game.gameobject.model.GameObject;
import game.gameobject.unit.model.Unit;

/**
 * Created by Max on 6/27/2014.
 *
 * Описывает скилл и его действие на игровой объект.
 */
public abstract class Skill extends GameObject {

    public Skill(final Integer x, final Integer y, final String spriteFileName, final GameWorld gameWorld) {
        super(x, y, spriteFileName, gameWorld);
    }

    @Override
    public void update(final long deltaTime) {
        super.update(deltaTime);
    }

    public abstract void act(final Unit go);
}