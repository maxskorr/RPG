package game.core;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class UpdateThread extends Thread {

    private Game game;

    @Override
    public void run() {
        update();
        game.startRender();
    }

    private void update() {

    }

}
