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
        int centerRenderX = (int) (center.x);
        int centerRenderY = (int) (center.y);

        List<List<Tile>> tiles = gameWorld.getCurrentLevel().getLevelMap().getTiles();

        for (int x = 0; x < tiles.size(); x++) {
            List<Tile> columns = tiles.get(x);
            for (int y = 0; y < columns.size(); y++) {
                Tile tile = columns.get(y);
                int xC = x * TILE_SIZE;
                int yC = y * TILE_SIZE;
                Point p = new Point(xC, yC);
                if (camera.isInBounds(p)) {
                    xC -= centerRenderX;
                    yC -= centerRenderY;

                    final Stack<Drawable> drawables = tile.getDrawables();

                    for (Drawable drawable: drawables) {
                        drawable.onRender(g, xC, yC);
                    }
                }
            }
        }

        for (Iterator<GameObject> it = gameObjects.iterator(); it.hasNext();) {
            GameObject object = it.next();
            int xC = object.getTileX() * TILE_SIZE;
            int yC = object.getTileY() * TILE_SIZE;
            Point p = new Point(xC, yC);
            if (camera.isInBounds(p)) {
                xC -= centerRenderX;
                yC -= centerRenderY;
                Drawable drawable = object.getDrawable();
                drawable.onRender(g, xC, yC);
            }
        }

        g.dispose();
        bs.show(); //показать
    }

    public Camera getCamera() {
        return camera;
    }

}