package game.controller.keyboard;

import game.util.Pool;

/**
 * Created by Semyon Danilov on 28.06.2014.
 */
public class KeyEvent implements Pool.Poolable<KeyEvent> {

    public static final int KEY_DOWN = 0;
    public static final int KEY_UP = 1;

    public int type;
    public int keyCode;
    public char keyChar;


    @Override
    public void clear() {
        keyCode = -1;
        keyChar = '*';
        type = -1;
    }

}
