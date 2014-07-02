package game.controller;

import game.controller.keyboard.KeyEvent;
import game.controller.keyboard.KeyboardHandler;
import game.controller.model.Controller;
import game.gameobject.skill.Fireball;
import game.gameobject.skill.Heal;
import game.gameobject.unit.model.Unit;
import game.util.GameObjectFactory;
import game.util.GameOptions;

import java.util.List;

/**
 * Created by Max on 6/27/2014.
 */
public class KeyboardController2 implements Controller {

    private final int[] keys = {};

    public static final int UP = java.awt.event.KeyEvent.VK_W;
    public static final int RIGHT = java.awt.event.KeyEvent.VK_D;
    public static final int DOWN = java.awt.event.KeyEvent.VK_S;
    public static final int LEFT = java.awt.event.KeyEvent.VK_A;
    public static final int FIRE = java.awt.event.KeyEvent.VK_Q;
    public static final int HEAL = java.awt.event.KeyEvent.VK_E;

    private final Unit unitUnderControl;
    private final KeyboardHandler keyboardHandler;

    public Unit getUnitUnderControl() {
        return unitUnderControl;
    }

    public KeyboardController2(final KeyboardHandler keyboardHandler, final Unit unitUnderControl) {
        this.unitUnderControl = unitUnderControl;
        this.keyboardHandler = keyboardHandler;
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
                    case HEAL:
                        final Unit unit = getUnitUnderControl();
                        final int x = unit.getRealX();
                        final int y = unit.getRealY();
                        final Heal heal = (Heal) GameObjectFactory.make(x, y, GameOptions.TILE_TYPE.SKILL_HEAL, unit.getGameWorld());
                        unit.getGameWorld().addGameObject(heal);
                        unit.cast(heal);
                        break;
                }
            } else if (type == KeyEvent.KEY_DOWN) {
                switch (keyCode) {
                    case UP:
                        getUnitUnderControl().setSpeedY(-1);
                        break;
                    case RIGHT:
                        getUnitUnderControl().setSpeedX(1);
                        break;
                    case DOWN:
                        getUnitUnderControl().setSpeedY(1);
                        break;
                    case LEFT:
                        getUnitUnderControl().setSpeedX(-1);
                        break;
                    case FIRE:
                        final Unit unit = getUnitUnderControl();
                        final int x = unit.getRealX() + (GameOptions.TILE_SIZE + 1 ) * unit.getLookDirection().getX();
                        final int y = unit.getRealY() + (GameOptions.TILE_SIZE + 1 ) * unit.getLookDirection().getY();
                        final Fireball fireball = (Fireball) GameObjectFactory.make(x, y, GameOptions.TILE_TYPE.SKILL_FIREBALL, unit.getGameWorld());
                        fireball.setDirection(unit.getLookDirection());
                        unit.getGameWorld().addGameObject(fireball);
                        break;
                }
            }
        }
    }
}
