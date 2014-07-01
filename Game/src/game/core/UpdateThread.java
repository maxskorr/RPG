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

    @Override
    public void run() {
        while (true) {
            final long s = System.currentTimeMillis();
            renderLock.lock();
            final long deltaTime = s - lastUpdate;
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
        for (Controller controller : controllers) {
            controller.update();
        }
        for (GameObject go : gameWorld.getGameObjects()) {
            go.update(deltaTime);
        }

        List<GameObject> scheduledForDelete = gameWorld.getScheduledForDelete(); //иначе ConcurrentModificationException
        for (GameObject gameObject : scheduledForDelete) {
            gameWorld.removeGameObject(gameObject);
        }
        scheduledForDelete.clear();
    }

}