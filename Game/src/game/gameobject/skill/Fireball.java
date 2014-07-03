package game.gameobject.skill;

import game.core.GameWorld;
import game.gameobject.model.GameObject;
import game.gameobject.skill.model.Skill;
import game.gameobject.unit.model.Unit;
import game.graphics.sprite.hud.DamageTaken;
import game.graphics.sprite.model.AbstractSprite;
import game.util.Logger;
import game.util.ResourceManager;

import static game.util.GameOptions.DIRECTION;

/**
 * Created by Max on 6/27/2014.
 */
public class Fireball extends Skill {
    private static final int BASE_DAMAGE = 10;
    private static final int MAX_VARIABLE_DAMAGE = 10;
    private static final int SPEED = 7;
    private DIRECTION direction;

    public Fireball(final Integer x, final Integer y,
                    final String spriteFileName, final GameWorld gameWorld) {
        super(x, y, spriteFileName, gameWorld);

        final AbstractSprite sprite = ResourceManager.getSprite(spriteFileName, 0);
        putSprite(sprite);
        setCurrentSprite(sprite);
    }
    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    @Override
    public void updatePhysics() {
        int dx = direction.getX() * SPEED;
        int dy = direction.getY() * SPEED;
        final int x = getRealX() + dx;
        final int y = getRealY() + dy;

        if (!getGameWorld().isOccupiedByRealPos(this, x, y)) {
            changeX(dx);
            changeY(dy);
        } else {
            final GameObject go = getGameWorld().getGameObjectByRealPos(x, y);

            if (go != this && go != null)
                cast(go);

            removeSelf();
        }
    }

    @Override
    public void cast(final GameObject gameObject) {
        if (!(gameObject instanceof Unit))
            return;

        final Unit unit = (Unit) gameObject;

        final int damage = BASE_DAMAGE + Math.abs(ResourceManager.random.nextInt() % MAX_VARIABLE_DAMAGE)
                - unit.getDef();

        Logger.getLogger(this.getClass()).log("unitHp: " + unit.getHp() + "; damage: " + damage);
        unit.changeHp(-damage);

        getGameWorld().scheduledForAddDrawable(new DamageTaken(getRealX(), getRealY(), -damage, getGameWorld()));
    }
}