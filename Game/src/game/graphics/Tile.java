package game.graphics;

import game.core.GameWorld;
import game.model.GameObject;

/**
 * Created by Max on 6/27/2014.
 */
public class Tile extends GameObject {

    private boolean visitable;

    public Tile(final Integer x, final Integer y, final String spriteFileName, final GameWorld gameWorld) {
        super(x, y, spriteFileName, gameWorld);
    }

    /**
     * Будет переопределён в классах-наследниках типа ловушек/телепортов..
     * @param gameObject Тот, кто инициирует вызов триггера
     */
    public void trigger(final GameObject gameObject) {
        // EMPTY
    }

    @Override
    public void update() {
        //
    }

    public boolean isVisitable() {
        return visitable;
    }

    public void setVisitable(boolean visitable) {
        this.visitable = visitable;
    }
}
