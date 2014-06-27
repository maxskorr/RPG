package game.model;

/**
 * Created by Max on 6/27/2014.
 */
public class Tile extends GameObject {
    private boolean visitable;

    public Tile(Integer x, Integer y, Sprite sprite) {
        super(x, y, sprite, gameWorld);
    }

    /**
     * Будет переопределён в классах-наследниках типа ловушек/телепортов..
     * @param gameObject Тот, кто инициирует вызов триггера
     */
    public void trigger(GameObject gameObject) {
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
