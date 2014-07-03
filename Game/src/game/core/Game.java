package game.core;

import game.controller.BotController;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
    private Set<Controller> controllers = new LinkedHashSet<>();

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
        gameWorld.setPlayer(player);
        gameWorld.addUnit(player, controllers, new KeyboardController(keyboardHandler, player));



        //Добавление 2 игрока
        final Player player2 = (Player) GameObjectFactory.make(40, 60, PLAYER, gameWorld);
        gameWorld.setSecondPlayer(player2);
        gameWorld.addUnit(player2, controllers, new KeyboardController2(keyboardHandler, player2));

        final Player bot = (Player) GameObjectFactory.make(80, 80, PLAYER, gameWorld);
        gameWorld.addUnit(bot, controllers, new BotController(bot));

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

    public Set<Controller> getControllers() {
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
