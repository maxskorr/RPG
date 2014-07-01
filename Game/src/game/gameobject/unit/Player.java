package game.gameobject.unit;

import game.core.GameWorld;
import game.gameobject.unit.model.Unit;
import game.util.GameOptions;
import game.util.PlayerOptionsProvider;

/**
 * Created by Max on 6/29/2014.
 */
public class Player extends Unit {
    public Player(Integer x, Integer y, String spriteFileName, GameWorld gameWorld) {
        super(x, y, spriteFileName, true, PlayerOptionsProvider.getName(),
                PlayerOptionsProvider.getMaxHp(), 0, 0, PlayerOptionsProvider.getMaxSpeed(), gameWorld);
        setDef(PlayerOptionsProvider.getDef());
        setLookDirection(GameOptions.DIRECTION.RIGHT);
    }
}
