package game.controller;

import game.controller.keyboard.KeyEvent;
import game.controller.keyboard.KeyboardHandler;
import game.controller.model.Controller;
import game.gameobject.skill.Fireball;
import game.gameobject.unit.model.Unit;
import game.util.GameObjectFactory;
import game.util.GameOptions;
import game.util.Logger;

import java.util.List;

/**
 * Created by Max on 6/27/2014.
 */
public class KeyboardController implements Controller {

    private final int[] keys = {};

    public static final int UP = java.awt.event.KeyEvent.VK_UP;
    public static final int RIGHT = java.awt.event.KeyEvent.VK_RIGHT;
    public static final int DOWN = java.awt.event.KeyEvent.VK_DOWN;
    public static final int LEFT = java.awt.event.KeyEvent.VK_LEFT;
    public static final int FIRE = java.awt.event.KeyEvent.VK_SHIFT;

    private final Unit unitUnderControl;
    private final KeyboardHandler keyboardHandler;

    public Unit getUnitUnderControl() {
        return unitUnderControl;
    }

    public KeyboardController(final KeyboardHandler keyboardHandler, final Unit unitUnderControl) {
        this.keyboardHandler = keyboardHandler;
        this.unitUnderControl = unitUnderControl;
    }

    @Override
    public void update() {
        List<KeyEvent> keyEventList = keyboardHandler.getKeyEvents();
        for (KeyEvent keyEvent : keyEventList) {
            int keyCode = keyEvent.keyCode;
            int type = keyEvent.type;
            if (type == KeyEvent.KEY_UP) {
                switch (keyCode) {
                    case UP:
                        getUnitUnderControl().setSpeedY(0);
                        break;
                    case RIGHT:
                        getUnitUnderControl().setSpeedX(0);
                        break;
                    case DOWN:
                        getUnitUnderControl().setSpeedY(0);
                        break;
                    case LEFT:
                        getUnitUnderControl().setSpeedX(0);
                        break;
                }
            } else if (type == KeyEvent.KEY_DOWN) {
                switch (keyCode) {
                    case UP:
                        getUnitUnderControl().setSpeedY(-1);
                        getUnitUnderControl().setLookDirection(GameOptions.DIRECTION.UP);
                        break;
                    case RIGHT:
                        getUnitUnderControl().setSpeedX(1);
                        getUnitUnderControl().setLookDirection(GameOptions.DIRECTION.RIGHT);
                        break;
                    case DOWN:
                        getUnitUnderControl().setSpeedY(1);
                        getUnitUnderControl().setLookDirection(GameOptions.DIRECTION.DOWN);
                        break;
                    case LEFT:
                        getUnitUnderControl().setSpeedX(-1);
                        getUnitUnderControl().setLookDirection(GameOptions.DIRECTION.LEFT);
                        break;
                    case FIRE:
                        final Unit unit = getUnitUnderControl();
                        final int x = unit.getX();
                        final int y = unit.getY();
                        final Fireball fireball = (Fireball) GameObjectFactory.make(x, y, GameOptions.TILE_TYPE.SKILL_FIREBALL, unit.getGameWorld());
                        fireball.setDirection(unit.getLookDirection());
                        unit.getGameWorld().addGameObject(fireball);
                        Logger.getLogger(this.getClass()).log("fireball");
                        break;
                }
            }
        }
    }
}
