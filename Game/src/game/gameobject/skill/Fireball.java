package game.gameobject.skill;

import game.core.GameWorld;
import game.gameobject.model.GameObject;
import game.gameobject.skill.model.Skill;
import game.gameobject.unit.model.Unit;
import game.util.ResourceManager;

import static game.util.GameOptions.DIRECTION;

/**
 * Created by Max on 6/27/2014.
 */
public class Fireball extends Skill {
    private static final int BASE_DAMAGE = 10;
    private static final int MAX_VARIABLE_DAMAGE = 10;
    private DIRECTION direction;

    public Fireball(final Integer x, final Integer y,
                    final String spriteFileName, final GameWorld gameWorld) {
        super(x, y, spriteFileName, gameWorld);
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    @Override
    public void updatePhysics() {
        int dx = 0;
        int dy = 0;

        switch (direction) {
            case UP:
                dy = -1;
                break;
            case RIGHT:
                dx = 1;
                break;
            case DOWN:
                dy = 1;
                break;
            case LEFT:
                dx = -1;
                break;
        }

        final int x = getX() + dx;
        final int y = getY() + dy;

        if (!getGameWorld().isOccupied(x, y)) {
            setXY(x, y);
        } else {
            act(getGameWorld().getCurrentLevel().getLevelMap().getTile(x, y).getLayers().peek());
        }
    }

    @Override
    public void act(final GameObject gameObject) {
        if (!(gameObject instanceof Unit))
            return;

        final Unit unit = (Unit) gameObject;

        final int damage = BASE_DAMAGE + (ResourceManager.random.nextInt() % MAX_VARIABLE_DAMAGE)
                - unit.getDef();

        unit.changeHp(damage);

        getGameWorld().removeGameObject(this);
    }
}
