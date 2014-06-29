package game.level;

import game.core.GameWorld;
import game.level.model.Level;
import game.util.MapFactory;

/**
 * Created by Max on 6/28/2014.
 */
public class StartMenu extends Level {
    public StartMenu(final GameWorld gameWorld) {
        super(MapFactory.loadMap("startMenu.map", gameWorld));
    }
    // Планировалось главное меню сделать уровнем?
}
