package game.core;

import game.core.camera.Camera;
import game.core.camera.SimpleCamera;
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

        int centerRenderX = player.getTileX() - (WINDOW_WIDTH / 2 / TILE_SIZE);
        int centerRenderY = player.getTileY() - (WINDOW_HEIGHT / 2 / TILE_SIZE);

        int startRenderX = player.getTileX() - RANGE + 1;
        int startRenderY = player.getTileY() - RANGE + 1;

        if (startRenderX < 0) {
            startRenderX = 0;
        }

        if (startRenderY < 0) {
            startRenderY = 0;
        }

        int finalRenderX = player.getTileX() + RANGE;
        int finalRenderY = player.getTileY() + RANGE;
        final int maxRenderX = gameWorld.getCurrentLevel().getLevelMap().getWidth();
        final int maxRenderY = gameWorld.getCurrentLevel().getLevelMap().getHeight();

        if (finalRenderX > maxRenderX) {
            finalRenderX = maxRenderX;
        }

        if (finalRenderY > maxRenderY) {
            finalRenderY = maxRenderY;
        }

        for (int x = startRenderX; x < finalRenderX; x++) {
            for (int y = startRenderY; y < finalRenderY; y++) {
                Tile tile = gameWorld.getCurrentLevel().getLevelMap().getTile(x, y);
                int xC = (x - centerRenderX) * TILE_SIZE;
                int yC = (y - centerRenderY) * TILE_SIZE;

                final Stack<Drawable> drawables = tile.getDrawables();

                for (Drawable drawable: drawables) {
                    drawable.onRender(g, xC - player.getDeltaRenderX(), yC - player.getDeltaRenderY());
                }
            }
        }

        for (Iterator<GameObject> it = gameObjects.iterator(); it.hasNext();) {
            GameObject object = it.next();
            if ((startRenderX < object.getTileX() && object.getTileX() < finalRenderX) && (startRenderY < object.getTileY() && object.getTileY() < finalRenderY)) {
                Drawable drawable = object.getDrawable();
                final int x,y;
                if(object!=player) {
                     x = (object.getTileX() - centerRenderX) * TILE_SIZE + object.getDeltaRenderX() - player.getDeltaRenderX();
                     y = (object.getTileY() - centerRenderY) * TILE_SIZE + object.getDeltaRenderY() - player.getDeltaRenderY();
                }else{
                     x = (object.getTileX() - centerRenderX) * TILE_SIZE;
                     y = (object.getTileY() - centerRenderY) * TILE_SIZE;
                }
                drawable.onRender(g, x, y);
            }
        }

        g.dispose();
        bs.show(); //показать
    }

    public Camera getCamera() {
        return camera;
    }

}