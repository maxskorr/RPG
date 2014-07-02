package game.core;

import game.controller.KeyboardController;
import game.controller.KeyboardController2;
import game.controller.keyboard.KeyboardHandler;
import game.controller.model.Controller;
import game.core.camera.Camera;
import game.core.model.Point;
import game.gameobject.unit.Player;
import game.level.StartMenu;
import game.level.model.Level;
import game.util.GameObjectFactory;
import game.util.Logger;

import javax.swing.*;
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

        KeyboardHandler keyboardHandler = new KeyboardHandler();
        final KeyboardController keyboardController = new KeyboardController(keyboardHandler, player);
        controllers.add(keyboardController);


        frame = new GameFrame(this);
        frame.init();
        frame.getCanvas().addKeyListener(keyboardHandler);

        //Добавление 2 игрока
        final Player player2 = (Player) GameObjectFactory.make(40, 60, PLAYER, gameWorld);
        gameWorld.addGameObject(player2);
        KeyboardHandler keyboardHandler2 = new KeyboardHandler();
        final KeyboardController2 keyboardController2 = new KeyboardController2(keyboardHandler2, player2);
        controllers.add(keyboardController2);
        frame.getCanvas().addKeyListener(keyboardHandler2);

        getCamera().smoothAnimTo(Point.newPoint(200l, 200l));
    }

    public void init() {
        gameWorld = new GameWorld();
        // final Level level = new RandomLevel(gameWorld);
        final Level level = new StartMenu(gameWorld);
        gameWorld.setCurrentLevel(level);
    }

    public void startRender() {
        SwingUtilities.invokeLater(renderRunnable);
    }

    public Camera getCamera() {
        return frame.getCamera();
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
