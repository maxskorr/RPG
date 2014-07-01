package game.util;

/**
 * Created by Max on 6/27/2014.
 */
public class GameOptions {
    public static final int TILE_SIZE = 20;
    public static final int PHYSICS_ITERATION = 80;
    public static final int ANIMATION_DELAY_MILLISECONDS = 70;

    public enum TILE_TYPE {

        WALL("tile_wall.png"),
        FLOOR("tile_floor.png"),
        PLAYER("players.png"),
        SKILL_FIREBALL("skill_fireball.png"),
        SKILL_HEAL("skill_heal.png");

        private final String filename;

        TILE_TYPE(final String filename) {
            this.filename = filename;
        }

        public String getFilename() {
            return filename;
        }

    }

    public enum DIRECTION {UP, RIGHT, DOWN, LEFT} // Направления движения

    public static final String ASSETS_GRAPHICS_PATH = "assets/graphics/";

    public static final String ASSETS_MAPS_PATH = "assets/maps/";
}
