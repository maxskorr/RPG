package game.util;

import game.core.GameWorld;
import game.gameobject.Floor;
import game.gameobject.Wall;
import game.gameobject.model.GameObject;
import game.gameobject.skill.Fireball;
import game.gameobject.unit.Player;

/**
 * Created by Max on 6/29/2014.
 */
public class GameObjectFactory {
    static public GameObject make(final Integer x, final Integer y,
                                  final GameOptions.TILE_TYPE tile_type, final GameWorld gameWorld) {
        GameObject gameObject = null;

        switch (GameOptions.TILE_TYPE.values()[tile_type.ordinal()]) {
            case WALL:
                gameObject = new Wall(x, y, tile_type.getFilename(), gameWorld);
                break;
            case FLOOR:
                gameObject = new Floor(x, y, tile_type.getFilename(), gameWorld);
                break;
            case PLAYER:
                gameObject = new Player(x, y, tile_type.getFilename(), gameWorld);
                break;
            case SKILL_FIREBALL:
                gameObject = new Fireball(x, y, tile_type.getFilename(), gameWorld);
                break;
            default:
                gameObject = null;
                break;
        }

        return gameObject;
    }
}
