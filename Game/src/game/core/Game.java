package game.core;

import javax.swing.*;

/**
 * Created by Max on 6/27/2014.
 */
public class Game {

    private GameFrame frame;

    public void startRender() {
        SwingUtilities.invokeLater(renderRunnable);
    }

    private Runnable renderRunnable = new Runnable() {

        @Override
        public void run() {
            frame.render();
        }

    };

}
