package game.level.model;

import game.map.model.LevelMap;

/**
 * Created by Max on 6/28/2014.
 */
public class Level {
    private LevelMap levelMap; // Или у уровня будет список карт?

    public LevelMap getLevelMap() {
        return levelMap;
    }

    public Level(LevelMap levelMap) {
        this.levelMap = levelMap;
    }
}
