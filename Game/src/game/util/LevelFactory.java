package game.util;

/**
 * Created by Max on 6/28/2014.
 *
 * Отвечает за создание уровней.
 */
public class LevelFactory {
    private final LevelFactory instance;

    public LevelFactory getInstance() {
        return instance;
    }

    private LevelFactory() {
        this.instance = this;
    }
}
