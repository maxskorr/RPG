package game.core.camera;

import game.core.Game;
import game.core.model.Point;
import game.gameobject.model.GameObject;

import java.awt.*;

/**
 * Created by Semyon Danilov on 02.07.2014.
 */
//следящая камера
public class SimpleCamera extends BaseCamera {

    private GameObject observedObject;
    private long observedLastX;
    private long observedLastY;

    public SimpleCamera(final Game game, final Canvas canvas, final GameObject observedObject, final int width, final int height) {
        super(game, canvas);
        this.observedObject = observedObject;
        this.width = width;
        this.height = height;
        recalculateBounds();
    }

    @Override
    public void setResolution(final int width, final int height) {
        this.width = width;
        this.height = height;
        recalculateBounds();
    }

    @Override
    public void attachTo(final GameObject gameObject) {
        this.observedObject = gameObject;
    }

    @Override
    public void setTopLeftBound(final Point point) {

    }

    @Override
    public void setBottomRightBound(final Point point) {

    }

    @Override
    public void update(final long deltaTime) {
        if (shouldRecalculate()) {
            recalculateBounds();
        }
        super.update(deltaTime);
    }

    @Override
    public long getHeight() {
        return height;
    }

    @Override
    public long getWidth() {
        return width;
    }

    private boolean shouldRecalculate() {
        return !isAnimating && (observedObject.getRealX() != observedLastX || observedObject.getRealY() != observedLastY);
    }

    private void recalculateBounds() {
        long x = observedObject.getRealX();
        long y = observedObject.getRealY();
        long halfWidth = width / 2;
        long halfHeight = height / 2;
        long topLeftX = x - halfWidth;
        long topLeftY = y - halfHeight;
        long botRightX = x + halfWidth;
        long botRightY = y + halfHeight;
        if (topLeft != null) {
            topLeft.recycle();
        }
        topLeft = Point.newPoint(topLeftX, topLeftY);
        if (bottomRight != null) {
            bottomRight.recycle();
        }
        bottomRight = Point.newPoint(botRightX, botRightY);
        observedLastX = x;
        observedLastY = y;
    }

}
