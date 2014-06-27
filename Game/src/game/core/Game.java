package game.core;

import game.graphics.Tile;
import game.log.Logger;

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
        int width = 100;
        int height = 100;
        Tile[][] map = new Tile[width][height];
        gameWorld = new GameWorld();
        gameWorld.setMap(map);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if ((x == 3 && y % 3 == 0) || (x == 0 || y == 0 || (width - 1) == x || (height - 1) == y)) {
                    map[x][y] = new Tile(x, y, "tile_floor.png", gameWorld);
                } else {
                    map[x][y] = new Tile(x, y, "tile_wall.png", gameWorld);
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
