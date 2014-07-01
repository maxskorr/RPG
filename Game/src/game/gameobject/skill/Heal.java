package game.gameobject.skill;

import game.gameobject.skill.model.Skill;
import game.gameobject.unit.model.Unit;
import game.graphics.AnimatedSprite;
import game.util.GameOptions;

/**
 * Created by Semyon Danilov on 01.07.2014.
 */
public class Heal extends Skill {

    private static final int MAX_TIME = 1000;
    private static final int MAX_ITERATIONS = 6;
    private static final int ITERATION_TIME = MAX_TIME / MAX_ITERATIONS;
    private int curTime = 0;
    private int curIteration = 1;

    private Unit unit;

    public Heal() {
        super(GameOptions.TILE_TYPE.SKILL_HEAL.getFilename());
    }

    @Override
    public void update(final long deltaTime) {
        super.update(deltaTime);
        if (curIteration >= MAX_ITERATIONS) {
            removeSelf();
        }
        if (curTime >= ITERATION_TIME * curIteration) {
            unit.setHp(unit.getHp() + (10 * curIteration));
            curIteration++;
        }
        if (curTime >= MAX_TIME && curIteration >= MAX_ITERATIONS) {
            removeSelf();
        }
        curTime += deltaTime;
        ((AnimatedSprite) getDrawable()).play();
    }

    @Override
    public void act(final Unit go) {
        this.unit = go;
    }

}
