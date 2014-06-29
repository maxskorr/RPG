package game.controller;

import game.controller.model.Controller;
import game.gameobject.unit.model.Unit;
import game.util.Logger;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Max on 6/27/2014.
 */
public class KeyboardController extends KeyAdapter implements Controller {
    private final int[] keys = {KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT};
    private final Unit unitUnderControl;

    public Unit getUnitUnderControl() {
        return unitUnderControl;
    }

    public KeyboardController(final Unit unitUnderControl) {
        this.unitUnderControl = unitUnderControl;
    }

    /**
     * Клавиша нажата.
     *
     * @param e Событие клавиатуры
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        Logger.getLogger(this.getClass()).log("keyPressed " + i);

        if (i == keys[0]) {
            getUnitUnderControl().setSpeedY(-1);
        } else if (i == keys[1]) {
            getUnitUnderControl().setSpeedX(1);
        } else if (i == keys[2]) {
            getUnitUnderControl().setSpeedY(1);
        } else if (i == keys[3]) {
            getUnitUnderControl().setSpeedX(-1);
        }
    }

    /**
     * Клавиша отпущена.
     *
     * @param e Событие клавиатуры
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();
        Logger.getLogger(this.getClass()).log("keyReleased " + i);

        if (i == keys[0]) {
            getUnitUnderControl().setSpeedY(-1);
        } else if (i == keys[1]) {
            getUnitUnderControl().setSpeedX(1);
        } else if (i == keys[2]) {
            getUnitUnderControl().setSpeedY(1);
        } else if (i == keys[3]) {
            getUnitUnderControl().setSpeedX(-1);
        }
    }
}
