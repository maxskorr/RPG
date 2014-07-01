package game.gameobject.unit;

import game.core.GameWorld;
import game.gameobject.unit.model.Unit;
import game.graphics.sprite.model.AbstractSprite;
import game.util.GameOptions;
import game.util.PlayerOptionsProvider;
import game.util.ResourceManager;

/**
 * Created by Max on 6/29/2014.
 */
public class Player extends Unit {
    public Player(Integer x, Integer y, String spriteFileName, GameWorld gameWorld) {
        super(x, y, spriteFileName, true, PlayerOptionsProvider.getName(),
                PlayerOptionsProvider.getMaxHp(), 0, 0, PlayerOptionsProvider.getMaxSpeed(), gameWorld);
        setDef(PlayerOptionsProvider.getDef());

        for (int i = 0; i < 4; i++) {
            final AbstractSprite sprite
                    = ResourceManager.getSpriteFromComplexImage(spriteFileName, 0, i, 1, 3);
            putSprite(sprite);
        }

        setCurrentSprite(getSprites().get(0));

        setLookDirection(GameOptions.DIRECTION.DOWN);
    }
}
