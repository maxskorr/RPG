package game.core;

import game.graphics.Tile;
import game.util.GameOptions;
import game.util.Logger;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Max on 6/27/2014.
 */
public class Game {

    private static final Logger LOGGER = Logger.getLogger(Game.class);

    private GameFrame frame;
    private GameWorld gameWorld;

    private GameEngineLock renderLock = new GameEngineLock();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game();
                UpdateThread updateThread = new UpdateThread(game);
                updateThread.start();
            }
        });
    }

    public Game() {
        init();
        frame = new GameFrame(this);
        frame.init();
    }

    public void init() {
        Tile[][] map = new Tile[GameOptions.MAP_WIDTH][GameOptions.MAP_HEIGHT];
        gameWorld = new GameWorld();
        gameWorld.setMap(map);

        for (int x = 0; x < GameOptions.MAP_WIDTH; x++) {
            for (int y = 0; y < GameOptions.MAP_HEIGHT; y++) {
                if ((x == 3 && y % 3 == 0) || (x == 0 || y == 0 || (GameOptions.MAP_WIDTH - 1) == x || (GameOptions.MAP_HEIGHT - 1) == y)) {
                    map[x][y] = new Tile(x, y, "tile_wall.png", gameWorld);
                } else {
                    map[x][y] = new Tile(x, y, "player.png", gameWorld);
                }
            }
        }
    }

    public void startRender() {
        SwingUtilities.invokeLater(renderRunnable);
    }

    private Runnable renderRunnable = new Runnable() {

        @Override
        public void run() {
            renderLock.lock();
            try {
                frame.render();
            } finally {
                renderLock.notifyCanUpdate();
                renderLock.unlock();
            }

        }

    };

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public GameEngineLock getRenderLock() {
        return renderLock;
    }

    public class GameEngineLock extends ReentrantLock {

        private AtomicBoolean canUpdate = new AtomicBoolean(true);
        private Condition renderCondition = newCondition();

        public void waitUntilCanUpdate() {
            while(!canUpdate.get()) {
                try {
                    renderCondition.await();
                } catch (final InterruptedException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }

        public void notifyCanDraw() {
            canUpdate.set(false);
            renderCondition.signalAll();
        }

        public void notifyCanUpdate() {
            canUpdate.set(true);
            renderCondition.signalAll();
        }

    }

}
