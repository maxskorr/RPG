package game.controller;

import game.controller.keyboard.KeyEvent;
import game.controller.keyboard.KeyboardHandler;
import game.controller.model.Controller;
import game.gameobject.unit.model.Unit;

import java.util.List;

/**
 * Created by Max on 6/27/2014.
 */
public class KeyboardController implements Controller {

    private final int[] keys = {};

    private static final int UP = java.awt.event.KeyEvent.VK_UP;
    private static final int RIGHT = java.awt.event.KeyEvent.VK_RIGHT;
    private static final int DOWN = java.awt.event.KeyEvent.VK_DOWN;
    private static final int LEFT = java.awt.event.KeyEvent.VK_LEFT;

    private final Unit unitUnderControl;
    private final KeyboardHandler keyboardHandler;

    public Unit getUnitUnderControl() {
        return unitUnderControl;
    }

    public KeyboardController(final KeyboardHandler keyboardHandler, final Unit unitUnderControl) {
        this.unitUnderControl = unitUnderControl;
        this.keyboardHandler = keyboardHandler;
    }

    @Override
    public void update() {
        List<KeyEvent> keyEventList = keyboardHandler.getKeyEvents();
        for (KeyEvent keyEvent : keyEventList) {
            int keyCode = keyEvent.keyCode;
            char keyChar = keyEvent.keyChar;
            int type = keyEvent.type;
            if (type == KeyEvent.KEY_DOWN) {
                continue;
            }
            switch (keyCode) {
                case UP:
                    getUnitUnderControl().moveBy(0, -1);
                    break;
                case RIGHT:
                    getUnitUnderControl().moveBy(1, 0);
                    break;
                case DOWN:
                    getUnitUnderControl().moveBy(0, 1);
                    break;
                case LEFT:
                    getUnitUnderControl().moveBy(-1, 0);
                    break;
            }
        }
    }
}
