package game.map.model;

import game.core.GameWorld;
import game.gameobject.model.GameObject;
import game.graphics.Drawable;

/**
 * Created by Max on 6/27/2014.
 */
public class Tile {

    private boolean visitable;
    private GameObject resident; // Резидент тайла

    public Drawable getDrawable() {
        return resident.getDrawable();
    }

    public Tile(final GameObject resident, final boolean visitable, final GameWorld gameWorld) {
        setResident(resident);
        setVisitable(visitable);
    }

    public GameObject getResident() {
        return resident;
    }

    public void setResident(GameObject resident) {
        this.resident = resident;
    }

    /**
     * Будет переопределён в классах-наследниках типа ловушек/телепортов..
     * @param gameObject Инициатор вызова триггера
     */
    public void trigger(final GameObject gameObject) {
        // EMPTY
    }

    public void update() {
        final GameObject resident = getResident();

        if (resident == null)
            throw new NullPointerException();

        resident.update();
    }

    public boolean isVisitable() {
        return visitable;
    }

    public void setVisitable(boolean visitable) {
        this.visitable = visitable;
    }
}
