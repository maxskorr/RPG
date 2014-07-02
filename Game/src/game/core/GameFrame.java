package game.core;

import game.core.camera.Camera;
import game.core.camera.SimpleCamera;
import game.core.model.Point;
import game.gameobject.model.GameObject;
import game.graphics.Drawable;
import game.map.model.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class GameFrame extends JFrame {

    private Canvas canvas;
    private Game game;
    private GameWorld gameWorld;
    private GameObject player;
    private Camera camera;

    public Canvas getCanvas() {
        return canvas;
    }

    public GameFrame(final Game game) {
        super(NAME);
        this.game = game;
        this.gameWorld = game.getGameWorld();
    }

    void init() {
        player = gameWorld.getPlayer();
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        camera = new SimpleCamera(player, WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //выход из приложения по нажатию клавиши ESC
        setLayout(new BorderLayout());
        add(getCanvas(), BorderLayout.CENTER); //добавляем холст на наш фрейм
        pack();
        setResizable(false);
        setVisible(true);
    }

    public static int WINDOW_WIDTH = 400; //ширина
    public static int WINDOW_HEIGHT = 300; //высота
    public static int CANVAS_HEIGHT = 300; //высота canvas
    public static int CANVAS_WIDTH = 300; //высота canvas
    public static int TILE_SIZE = 20; //размер тайла
    public static int RANGE = 15; //дальность обзора
    public static String NAME = "Level 1";

    public void render() {
        List<GameObject> gameObjects = gameWorld.getGameObjects();
        BufferStrategy bs = getCanvas().getBufferStrategy();

        if (bs == null) {
            getCanvas().createBufferStrategy(2); //создаем BufferStrategy для нашего холста
            getCanvas().requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics(); // Получаем Graphics из созданной нами BufferStrategy
        g.setColor(Color.black); // Выбрать цвет
        g.fillRect(0, 0, getWidth(), getHeight());

        Point center = camera.getCenter();
        int centerRenderX = (int) (center.getX() - (camera.getWidth() / 2));
        int centerRenderY = (int) (center.getY() - (camera.getHeight() / 2));
        List<List<Tile>> tiles = gameWorld.getCurrentLevel().getLevelMap().getTiles();

        for (int y = 0; y < tiles.size(); y++) {
            List<Tile> row = tiles.get(y);
            for (int x = 0; x < row.size(); x++) {
                Tile tile = row.get(x);
                int xC = x * TILE_SIZE;
                int yC = y * TILE_SIZE;
                Point p = Point.newPoint(xC, yC);
                Point p2 = p.add(TILE_SIZE, TILE_SIZE);
                if (camera.intersects(p, p2)) {
                    xC -= centerRenderX;
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
            if (camera.intersects(p, p2)) {
                xC -= centerRenderX;
                yC -= centerRenderY;
                Drawable drawable = object.getDrawable();
                drawable.onRender(g, xC, yC);
                drawable.afterRender(g, xC, yC);
            }
            p2.recycle();
            p.recycle();
        }
        g.dispose();
        bs.show(); //показать
    }

    public Camera getCamera() {
        return camera;
    }

}