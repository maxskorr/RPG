package game.gameobject;

import game.core.GameWorld;
import game.gameobject.model.GameObject;

/**
 * Created by Max on 6/29/2014.
 */
public class Wall extends GameObject {
    @Override
    public void update(long deltaTime) {

    }

    public Wall(Integer x, Integer y, String spriteFileName, GameWorld gameWorld) {
        super(x, y, spriteFileName, gameWorld);
    }
}
