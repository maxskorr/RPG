package game.level;

import game.core.GameWorld;
import game.level.model.Level;
import game.util.MapFactory;

/**
 * Created by Max on 6/28/2014.
 */
public class RandomLevel extends Level {
    public RandomLevel(final GameWorld gameWorld) {
        super( MapFactory.generateRandomMap(40, 40, gameWorld) );
    }
}
