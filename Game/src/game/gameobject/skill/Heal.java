package game.gameobject.skill;

import game.core.GameWorld;
import game.gameobject.model.GameObject;
import game.gameobject.skill.model.Skill;
import game.gameobject.unit.model.Unit;
import game.graphics.sprite.AnimatedSprite;
import game.graphics.sprite.model.AbstractSprite;
import game.util.ResourceManager;

/**
 * Created by Semyon Danilov on 01.07.2014.
 */
public class Heal extends Skill {

    private static final int MAX_TIME = 1000;
    private static final int MAX_ITERATIONS = 2;
    private static final int ITERATION_TIME = MAX_TIME / MAX_ITERATIONS;
    private int curTime = 0;
    private int curIteration = 1;

    private Unit unit;

    public Heal(final Integer x, final Integer y,
                final String spriteFileName, final GameWorld gameWorld) {
        super(x, y, spriteFileName, gameWorld);

        final AbstractSprite sprite  = ResourceManager.getSprite(spriteFileName, 0);
        putSprite(sprite);
        setCurrentSprite(sprite);
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
    public void act(final GameObject go) {
        if (!(go instanceof Unit)) {
            return;
        }

        this.unit = (Unit) go;
    }

}
