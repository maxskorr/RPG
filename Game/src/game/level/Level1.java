package game.level;

import game.core.GameWorld;
import game.level.model.Level;
import game.util.MapFactory;

/**
 * Created by Max on 6/28/2014.
 */
public class Level1 extends Level {
    public Level1(final GameWorld gameWorld) {
        super(MapFactory.loadMap("level1.map", gameWorld));
    }
}
