package game.core;

import game.controller.KeyboardController;
import game.gameobject.unit.Player;
import game.graphics.AnimatedSprite;
import game.level.StartMenu;
import game.level.model.Level;
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

        final Player player = (Player) gameWorld.getCurrentLevel().getLevelMap().getTile(1, 1).getResident();
        gameWorld.addGameObject(player);
        final KeyboardController keyboardController = new KeyboardController(player);
        frame.getCanvas().addKeyListener(keyboardController);
    }

    public void init() {
        gameWorld = new GameWorld();
        final Level level = new StartMenu(gameWorld);
        gameWorld.setCurrentLevel( level );
        ((AnimatedSprite)level.getLevelMap().getTile(1, 1).getDrawable()).setPaused(false);
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
