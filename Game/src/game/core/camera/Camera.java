package game.core.camera;

import game.core.model.Point;
import game.gameobject.model.GameObject;

/**
 * Created by Semyon Danilov on 02.07.2014.
 */
public interface Camera {

    Point getTopLeftBound();

    Point getBottomRightBound();

    Point getCenter();

    void setResolution(final int width, final int height);

    void attachTo(final GameObject gameObject);

    void setTopLeftBound(final Point point);

    void setBottomRightBound(final Point point);

    boolean isInBounds(final Point point);

    void smoothAnimTo(final Point newCenter);

    void update(final long deltaTime);
}
