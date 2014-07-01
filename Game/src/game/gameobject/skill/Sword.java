package game.gameobject.skill;

import game.core.GameWorld;
import game.gameobject.model.GameObject;
import game.gameobject.skill.model.Skill;

/**
 * Created by Max on 6/27/2014.
 */
public class Sword extends Skill {
    public Sword(Integer x, Integer y, String spriteFileName, GameWorld gameWorld) {
        super(x, y, spriteFileName, gameWorld);
    }

    @Override
    public void act(final GameObject gameObject) {
        //
    }
}
