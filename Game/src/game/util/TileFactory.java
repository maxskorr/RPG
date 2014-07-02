package game.util;

import game.core.GameWorld;
import game.gameobject.model.GameObject;
import game.map.model.Tile;

/**
 * Created by Max on 6/29/2014.
 */
public class TileFactory {
    static public Tile make(final Integer x, final Integer y,
                            final GameOptions.TILE_TYPE tile_type, final GameWorld gameWorld) {
        // TODO: invent diff. coordinates for tiles and gameobjects(?)
        final GameObject gameObject = GameObjectFactory.make(x, y, tile_type, gameWorld);
        final boolean visitable;

        switch (tile_type) {
            case WALL:
                visitable = false;
                break;
            case FLOOR:
                visitable = true;
                break;
            case PLAYER:
                visitable = false;
                break;
            default:
                visitable = false;
        }

        final Tile tile = new Tile(gameObject, visitable, gameWorld, tile_type);

        return tile;
    }
}
