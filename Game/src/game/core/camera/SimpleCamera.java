package game.core.camera;

import game.core.model.Point;
import game.gameobject.model.GameObject;

/**
 * Created by Semyon Danilov on 02.07.2014.
 */
//следящая камера
public class SimpleCamera implements Camera {

    private static final long BASIC_ANIM_SPEED = 40; //px/s, keep it positive

    private Point topLeft;
    private Point bottomRight;
    private Integer width;
    private Integer height;

    private boolean isAnimating;
    private Point animateTo;

    private GameObject observedObject;
    private long observedLastX;
    private long observedLastY;

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
    public Point getCenter() {
        long y = topLeft.y + (((bottomRight.y - topLeft.y)) / 2);
        long x = topLeft.x + (((bottomRight.x - topLeft.x)) / 2);
        return new Point(x, y);
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
    public boolean isInBounds(final Point point) {
        return (point.x <= bottomRight.x) && (point.x >= topLeft.x) && (point.y >= topLeft.y) && (point.y <= bottomRight.y);
    }

    @Override
    public void smoothAnimTo(final Point newCenter) {
        this.isAnimating = true;
        this.animateTo = newCenter;
    }

    @Override
    public void update(final long deltaTime) {
        if (shouldRecalculate()) {
            recalculateBounds();
        }
        if (isAnimating) {
            animate(deltaTime);
        }
    }

    private void animate(final long deltaTime) {
        float timeF  = deltaTime / 1000;
        Point curCenter = getCenter();
        long curX = curCenter.x;
        long curY = curCenter.y;
        long destX = animateTo.x;
        long destY = animateTo.y;

        long betweenX = Math.abs(destX - curX);
        long betweenY = Math.abs(destY - curY);

        long dx = (long) (BASIC_ANIM_SPEED * timeF);
        long dy =(long) (BASIC_ANIM_SPEED * timeF);

        if (dx > betweenX) {
            dx = betweenX;
        }
        if (dy > betweenY) {
            dy = betweenY;
        }
        dx = dx * (curX > destX ? -1 : 1);
        dy = dy * (curY > destY ? -1 : 1);

        curX = curX + dx;
        curY = curY + dy;
        topLeft = topLeft.add(dx, dy);
        bottomRight = bottomRight.add(dx, dy);
        if (curX == destX && curY == destY) {
            stopAnimation();
        }
    }

    private void stopAnimation() {
        this.isAnimating = false;
        this.animateTo = null;
    }

    private boolean shouldRecalculate() {
        return !isAnimating && observedObject.getRealX() != observedLastX && observedObject.getRealY() != observedLastY;
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
        topLeft = new Point(topLeftX, topLeftY);
        bottomRight = new Point(botRightX, botRightY);
        observedLastX = x;
        observedLastY = y;
    }

}
