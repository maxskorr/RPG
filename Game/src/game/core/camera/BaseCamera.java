package game.core.camera;

import game.core.Game;
import game.core.GameWorld;
import game.core.model.Point;
import game.gameobject.model.GameObject;
import game.graphics.Drawable;
import game.graphics.sprite.hud.model.Hud;
import game.map.model.Tile;
import game.util.Build;
import game.util.GameOptions;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by semyon on 03.07.14.
 */
public abstract class BaseCamera implements Camera {

    protected static final long BASIC_ANIM_SPEED = 400; //px/s, keep it positive

    protected Point topLeft;
    protected Point bottomRight;
    protected Integer width;
    protected Integer height;

    protected boolean isAnimating;
    protected Point animateTo;

    protected GameWorld gameWorld;
    protected Canvas canvas;

    public BaseCamera(final Game game, final Canvas canvas) {
        this.gameWorld = game.getGameWorld();
        this.canvas = canvas;
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
    }

    @Override
    public abstract void attachTo(final GameObject gameObject);

    @Override
    public abstract void setTopLeftBound(final Point point);

    @Override
    public abstract void setBottomRightBound(final Point point);

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

    private final int TILE_SIZE = GameOptions.TILE_SIZE;

    @Override
    public void render() {

        Set<GameObject> gameObjects = gameWorld.getGameObjects();
        BufferStrategy bs = canvas.getBufferStrategy();

        if (bs == null) {
            canvas.createBufferStrategy(2); //создаем BufferStrategy для нашего холста
            canvas.requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics(); // Получаем Graphics из созданной нами BufferStrategy
        g.setColor(Color.black); // Выбрать цвет
        g.fillRect(0, 0, width, height);

        int cameraOffsetX = (int) topLeft.getX();
        int centerRenderY = (int) topLeft.getY();
        List<List<Tile>> tiles = gameWorld.getCurrentLevel().getLevelMap().getTiles();

        for (int y = 0; y < tiles.size(); y++) {
            java.util.List<Tile> row = tiles.get(y);
            for (int x = 0; x < row.size(); x++) {
                Tile tile = row.get(x);
                int xC = x * TILE_SIZE;
                int yC = y * TILE_SIZE;
                Point p = Point.newPoint(xC, yC);
                Point p2 = p.add(TILE_SIZE, TILE_SIZE);

                if (intersects(p, p2)) {
                    xC -= cameraOffsetX;
                    yC -= centerRenderY;

                    final Stack<Drawable> drawables = tile.getDrawables();

                    for (Drawable drawable: drawables) {
                        drawable.onRender(g, xC, yC);
                        drawable.afterRender(g, xC, yC);
                    }
                }
                p.recycle();
                p2.recycle();
            }
        }

        for (Iterator<GameObject> it = gameObjects.iterator(); it.hasNext();) {
            GameObject object = it.next();
            int xC = object.getRealX();
            int yC = object.getRealY();
            Point p = Point.newPoint(xC, yC);
            Point p2 = p.add(TILE_SIZE, TILE_SIZE);
            if (intersects(p, p2)) {
                xC -= cameraOffsetX;
                yC -= centerRenderY;
                Drawable drawable = object.getDrawable();
                drawable.onRender(g, xC, yC);
                drawable.afterRender(g, xC, yC);
            }
            p2.recycle();
            p.recycle();
        }

        for (Hud hud: gameWorld.getHUDs()) {
            int xC = hud.getX();
            int yC = hud.getY();

            Point p = Point.newPoint(xC, yC);
            Point p2 = p.add(TILE_SIZE, TILE_SIZE);

            if (intersects(p, p2)) {
                xC -= cameraOffsetX;
                yC -= centerRenderY;

                hud.onRender(g, xC, yC);
                hud.afterRender(g, xC, yC);
            }

            p2.recycle();
            p.recycle();
        }

        if (Build.DEBUG) {
            int xSz = width / TILE_SIZE;
            int ySz = height / TILE_SIZE;
            g.setColor(Color.red);
            for (int i = 0; i < xSz; i++) {
                int x = i * TILE_SIZE;
                g.drawLine(x, 0, x, height);
            }
            for (int i = 0; i < ySz; i++) {
                int y = i * TILE_SIZE;
                g.drawLine(0, y, width, y);
            }
        }

        g.dispose();
        bs.show(); //показать
    }

}
