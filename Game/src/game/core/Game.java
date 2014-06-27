package game.core;

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

    public Game() {

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
