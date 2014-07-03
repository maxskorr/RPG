package game.map.model;

import game.core.GameWorld;
import game.gameobject.model.GameObject;
import game.gameobject.skill.Fireball;
import game.util.GameObjectFactory;
import game.util.GameOptions;

/**
 * Created by Edik on 7/03/2014.
 */
public class TileGun extends Tile{
    public TileGun(final GameObject resident, final boolean visitable, final GameWorld gameWorld, final GameOptions.TILE_TYPE type) {
        super(resident,  visitable,gameWorld,  type);
    }

    /**
     * Будет переопределён в классах-наследниках типа ловушек/телепортов..
     * @param gameObject Инициатор вызова триггера
     */
    public void trigger(final GameObject gameObject) {
        GameObject unit = gameObject;

        int x = unit.getRealX();
        int y = unit.getRealY()+GameOptions.TILE_SIZE;
        Fireball fireball = (Fireball) GameObjectFactory.make(x, y, GameOptions.TILE_TYPE.SKILL_FIREBALL, unit.getGameWorld());
        fireball.setDirection(GameOptions.DIRECTION.DOWN);
        unit.getGameWorld().scheduleAddGameObject(fireball);

        x = unit.getRealX();
        y = unit.getRealY()-GameOptions.TILE_SIZE;
        fireball = (Fireball) GameObjectFactory.make(x, y, GameOptions.TILE_TYPE.SKILL_FIREBALL, unit.getGameWorld());
        fireball.setDirection(GameOptions.DIRECTION.UP);
        unit.getGameWorld().scheduleAddGameObject(fireball);

        x = unit.getRealX()-GameOptions.TILE_SIZE;
        y = unit.getRealY();
        fireball = (Fireball) GameObjectFactory.make(x, y, GameOptions.TILE_TYPE.SKILL_FIREBALL, unit.getGameWorld());
        fireball.setDirection(GameOptions.DIRECTION.LEFT);
        unit.getGameWorld().scheduleAddGameObject(fireball);

        x = unit.getRealX()+GameOptions.TILE_SIZE;
        y = unit.getRealY();
        fireball = (Fireball) GameObjectFactory.make(x, y, GameOptions.TILE_TYPE.SKILL_FIREBALL, unit.getGameWorld());
        fireball.setDirection(GameOptions.DIRECTION.RIGHT);
        unit.getGameWorld().scheduleAddGameObject(fireball);
    }
}
