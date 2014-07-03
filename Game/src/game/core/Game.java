package game.core;

import game.controller.KeyboardController;
import game.controller.KeyboardController2;
import game.controller.keyboard.KeyboardHandler;
import game.controller.model.Controller;
import game.core.camera.Camera;
import game.gameobject.unit.Player;
import game.level.RandomLevel;
import game.level.model.Level;
import game.util.GameObjectFactory;
import game.util.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static game.util.GameOptions.TILE_TYPE.PLAYER;

/**
 * Created by Max on 6/27/2014.
 */
public class Game {

    private static final Logger LOGGER = Logger.getLogger(Game.class);

    private GameFrame frame;
    private GameWorld gameWorld;
    private List<Controller> controllers = new ArrayList<>();

    private GameEngineLock renderLock = new GameEngineLock();

    private KeyboardHandler keyboardHandler = new KeyboardHandler();

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

        // При создании игрока используем реальные координаты(в пикселах)!
        final Player player = (Player) GameObjectFactory.make(20, 20, PLAYER, gameWorld);
        gameWorld.addGameObject(player);
        gameWorld.setPlayer(player);

        final KeyboardController keyboardController = new KeyboardController(keyboardHandler, player);
        controllers.add(keyboardController);

        //Добавление 2 игрока
        final Player player2 = (Player) GameObjectFactory.make(40, 60, PLAYER, gameWorld);
        gameWorld.addGameObject(player2);
        gameWorld.setSecondPlayer(player2);
//        BotController botController = new BotController(player2);
//        controllers.add(botController);
        final KeyboardController2 keyboardController2 = new KeyboardController2(keyboardHandler, player2);
        controllers.add(keyboardController2);

        frame = new GameFrame(this);
        frame.init();

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(final KeyEvent e) {
                keyboardHandler.onKey(e);
                return false;
            }
        });

        //getCamera().smoothAnimTo(Point.newPoint(200l, 200l));
    }

    public void init() {
        gameWorld = new GameWorld();
        final Level level = new RandomLevel(gameWorld);
        //final Level level = new StartMenu(gameWorld);
        gameWorld.setCurrentLevel(level);
    }

    public KeyboardHandler getKeyboardHandler() {
        return keyboardHandler;
    }

    public void startRender() {
        SwingUtilities.invokeLater(renderRunnable);
    }

    public List<Camera> getCameras() {
        return frame.getCameras();
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

    public List<Controller> getControllers() {
        return controllers;
    }

    public class GameEngineLock extends ReentrantLock {

        private AtomicBoolean canUpdate = new AtomicBoolean(true);
        private Condition renderCondition = newCondition();

        public void waitUntilCanUpdate() {
            while (!canUpdate.get()) {
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
