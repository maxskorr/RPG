package game.map.model;

import game.core.GameWorld;
import game.gameobject.model.GameObject;
import game.graphics.Drawable;

import java.util.List;
import java.util.Stack;

/**
 * Created by Max on 6/27/2014.
 */
public class Tile {

    private boolean visitable;
    private final Stack<GameObject> layers; // Слои тайла
    public final int MAX_DEPTH = 3; // Максимальное количество слоёв
    public final int MIN_DEPTH = 1; // Минимальное количество слоёв

    public Drawable getDrawable() {
        return layers.peek().getDrawable();
    }

    public Stack<Drawable> getDrawables() {
        final Stack<Drawable> drawables = new Stack<>();

        for (GameObject go: layers) {
            drawables.push(go.getDrawable());
        }

        return drawables;
    }

    public Tile(final GameObject resident, final boolean visitable, final GameWorld gameWorld) {
        setVisitable(visitable);
        layers = new Stack<>();
        layers.add(resident);
    }

    public GameObject popGameObject() {
        if (layers.size() <= 0 && layers.size()  > MIN_DEPTH)
            throw new NullPointerException();

        if (layers.size() == MIN_DEPTH)
            throw new ArrayIndexOutOfBoundsException();

        return layers.pop();
    }

    public boolean removeGameObject(final GameObject gameObject) {
        if (layers.size() <= 0 && layers.size() > MIN_DEPTH)
            throw new NullPointerException();

        if (layers.size() == MIN_DEPTH)
            throw new ArrayIndexOutOfBoundsException();
        return layers.remove(gameObject);
    }

    public GameObject peekGameObject() {
        if (layers.size() <= 0)
            throw new NullPointerException();

        return layers.peek();
    }

    public boolean pushGameObject(final GameObject gameObject) {
        if (layers.size() >= MAX_DEPTH)
            throw new IllegalStateException();

        return layers.add(gameObject);
    }

    public List<GameObject> getLayers() {
        return layers;
    }

    /**
     * Будет переопределён в классах-наследниках типа ловушек/телепортов..
     * @param gameObject Инициатор вызова триггера
     */
    public void trigger(final GameObject gameObject) {
        // EMPTY
    }

    public void update() {
        final GameObject resident = peekGameObject();

        if (resident == null)
            throw new NullPointerException();

        //resident.update();
    }

    public boolean isVisitable() {
        return visitable;
    }

    public void setVisitable(boolean visitable) {
        this.visitable = visitable;
    }
}
