package game.core;

import game.controller.model.Controller;
import game.gameobject.model.GameObject;
import game.util.Logger;

import java.util.List;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
//TODO: add pause
public class UpdateThread extends Thread {

    private static final Logger LOGGER = Logger.getLogger(UpdateThread.class);

    private Game game;
    private GameWorld gameWorld;


    private final Game.GameEngineLock renderLock;

    public UpdateThread(final Game game) {
        this.game = game;
        this.gameWorld = game.getGameWorld();
        this.renderLock = game.getRenderLock();
    }

    private long timePassed = 0;
    private long frames;
    private long lastUpdate = System.currentTimeMillis();

    private static final int ENGINE_MAX_FPS = 60;

    private static final int ENGINE_MIN_DELAY = 1000 / ENGINE_MAX_FPS;

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
        List<Controller> controllers = game.getControllers();


        for (GameObject gameObject : gameWorld.getScheduledForAdd()) {
            gameWorld.addGameObject(gameObject);
        }
        gameWorld.getScheduledForAdd().clear();

        for (Controller controller : controllers) {
            controller.update();
        }
        for (GameObject go : gameWorld.getGameObjects()) {
            go.update(deltaTime);
        }
        game.getCamera().update(deltaTime);
        //List<GameObject> scheduledForDelete = gameWorld.getScheduledForDelete(); //иначе ConcurrentModificationException
        //Мы типа меняем другой список с чего исключение?
        for (GameObject gameObject : gameWorld.getScheduledForDelete()) {
            gameWorld.removeGameObject(gameObject);
        }
        gameWorld.getScheduledForDelete().clear();
    }

}