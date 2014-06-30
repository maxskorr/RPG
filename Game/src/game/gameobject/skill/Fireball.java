package game.gameobject.skill;

import game.gameobject.skill.model.Skill;
import game.gameobject.unit.model.Unit;
import game.util.GameOptions;

/**
 * Created by Max on 6/27/2014.
 */
public class Fireball extends Skill {
    public Fireball() {
        super(GameOptions.TILE_TYPE.SKILL_FIREBALL.getFilename());
    }

    @Override
    public void act(final Unit go) {
        //
    }
}
