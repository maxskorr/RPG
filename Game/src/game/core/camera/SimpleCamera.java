package game.core.camera;

import game.core.model.Point;
import game.gameobject.model.GameObject;

/**
 * Created by Semyon Danilov on 02.07.2014.
 */
//следящая камера
public class SimpleCamera implements Camera {

    private static final long BASIC_ANIM_SPEED = 400; //px/s, keep it positive

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
        long y = topLeft.getY() + (((bottomRight.getY() - topLeft.getY())) / 2);
        long x = topLeft.getX() + (((bottomRight.getX() - topLeft.getX())) / 2);
        return Point.newPoint(x, y);
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
        return (point.getX() <= bottomRight.getX()) && (point.getX() >= topLeft.getX()) && (point.getY() >= topLeft.getY()) && (point.getY() <= bottomRight.getY());
    }

    @Override
    public boolean intersects(final Point left, final Point right) {
        long lx1 = topLeft.getX();
        long ly1 = topLeft.getY();
        long rx1 = bottomRight.getX();
        long ry1 = bottomRight.getY();
        long lx2 = left.getX();
        long ly2 = left.getY();
        long rx2 = right.getX();
        long ry2 = right.getY();
        return !(lx1 > rx2 || lx2 > rx1 || ly1 > ry2 || ly2 > ry1);
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

    @Override
    public long getHeight() {
        return height;
    }

    @Override
    public long getWidth() {
        return width;
    }

    private void animate(final long deltaTime) {
        double timeF = (double) (((double) deltaTime) / 1000d);
        Point curCenter = getCenter();
        long curX = curCenter.getX();
        long curY = curCenter.getY();
        long destX = animateTo.getX();
        long destY = animateTo.getY();

        long betweenX = Math.abs(destX - curX);
        long betweenY = Math.abs(destY - curY);

        long dx = (long) (BASIC_ANIM_SPEED * timeF);
        long dy = (long) (BASIC_ANIM_SPEED * timeF);

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
        Point old = topLeft;
        topLeft = topLeft.add(dx, dy);
        old.recycle();
        old = bottomRight;
        bottomRight = bottomRight.add(dx, dy);
        old.recycle();
        if (curX == destX && curY == destY) {
            stopAnimation();
        }
    }

    private void stopAnimation() {
        this.isAnimating = false;
        this.animateTo = null;
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
