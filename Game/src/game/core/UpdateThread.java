package game.core;

import game.controller.model.Controller;
import game.core.camera.Camera;
import game.gameobject.model.GameObject;
import game.graphics.Drawable;
import game.util.Logger;

import java.util.Set;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
//TODO: add pause
public class UpdateThread extends Thread {

    private static final Logger LOGGER = Logger.getLogger(UpdateThread.class);

    private Game game;
    private GameWorld gameWorld;

    private long timePassed = 0;
    private long frames;
    private long lastUpdate = System.currentTimeMillis();

    private static final int ENGINE_MAX_FPS = 60;

    private static final int ENGINE_MIN_DELAY = 1000 / ENGINE_MAX_FPS;

    private final Game.GameEngineLock renderLock;

    public UpdateThread(final Game game) {
        this.game = game;
        this.gameWorld = game.getGameWorld();
        this.renderLock = game.getRenderLock();
    }

    @Override
    public void run() {
        while (true) {
            final long s = System.currentTimeMillis();
            renderLock.lock();
            long deltaTime = s - lastUpdate;
            while (deltaTime < ENGINE_MIN_DELAY) {
                try {
                    sleep(ENGINE_MIN_DELAY - deltaTime);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                } finally {
                    deltaTime = System.currentTimeMillis() - lastUpdate;
                }
            }
            lastUpdate = System.currentTimeMillis();
            try {
                update(deltaTime);
                game.startRender();
                renderLock.notifyCanDraw();
                renderLock.waitUntilCanUpdate();
            } finally {
                renderLock.unlock();
            }
            long e = System.currentTimeMillis();
            timePassed += (e - s);
            frames++;
            if (timePassed >= 1000) {
                timePassed = 0;
                LOGGER.log(frames + " FPS");
                frames = 0;
            }
        }
    }

    private void update(final long deltaTime) {
        Set<Controller> controllers = game.getControllers();


        // Scheduled additions
        for (GameObject gameObject : gameWorld.getScheduledForAddGameObjects()) {
            gameWorld.addGameObject(gameObject);
        }

        for (Drawable drawable: gameWorld.getScheduledForAddDrawables()) {
            gameWorld.addDrawable(drawable);
        }

        gameWorld.getScheduledForAddGameObjects().clear();
        gameWorld.getScheduledForAddDrawables().clear();
        // **

        game.getKeyboardHandler().getKeyEvents();
        game.getKeyboardHandler().setCanUpdate(false);

        for (Controller controller : controllers) {
            controller.update();
        }

        game.getKeyboardHandler().setCanUpdate(true);

        for (GameObject go : gameWorld.getGameObjects()) {
            go.update(deltaTime);
        }

        for (Camera camera : game.getCameras()) {
            camera.update(deltaTime);
        }

        // Scheduled deletions
        for (GameObject gameObject : gameWorld.getScheduledForDeleteGameObjects()) {
            gameWorld.removeGameObject(gameObject);
        }

        for (Drawable drawable: gameWorld.getScheduledForDeleteDrawables()) {
            gameWorld.removeDrawable(drawable);
        }

        gameWorld.getScheduledForDeleteGameObjects().clear();
        gameWorld.getScheduledForDeleteDrawables().clear();
        // **
    }

}