package game.util;

/**
 * Created by Max on 6/27/2014.
 */
public class GameOptions {
    public static int TILE_SIZE = 20;

    private static GameOptions ourInstance = new GameOptions();

    public static GameOptions getInstance() {
        return ourInstance;
    }

    private GameOptions() {
    }


}
