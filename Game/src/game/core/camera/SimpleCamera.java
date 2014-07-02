package game.core.camera;

import game.core.model.Point;
import game.gameobject.model.GameObject;

/**
 * Created by Semyon Danilov on 02.07.2014.
 */
//следящая камера
public class SimpleCamera implements Camera {

    private Point topLeft;
    private Point bottomRight;
    private Integer width;
    private Integer height;

    private GameObject observedObject;
    private Integer observedLastX;
    private Integer observedLastY;

    public SimpleCamera(final GameObject observedObject, final int width, final int height) {
        this.observedObject = observedObject;
        this.width = width;
        this.height = height;
        recalculateBounds();
    }

    @Override
    public Point getTopLeftBound() {
        return topLeft;
    }

    @Override
    public Point getBottomRightBound() {
        return bottomRight;
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
    public void update() {
        if (shouldRecalculate()) {
            recalculateBounds();
        }
    }

    private boolean shouldRecalculate() {
        return observedObject.getX() != observedLastX && observedObject.getY() != observedLastY;
    }

    private void recalculateBounds() {
        int x = observedObject.getX();
        int y = observedObject.getY();
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        int topLeftX = x - halfWidth;
        int topLeftY = y - halfHeight;
        int botRightX = x + halfWidth;
        int botRightY = y + halfHeight;
        topLeft = new Point(topLeftX, topLeftY);
        bottomRight = new Point(botRightX, botRightY);
    }

}
