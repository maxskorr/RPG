package game.core;

import game.graphics.Drawable;
import game.graphics.Tile;
import game.model.GameObject;
import game.util.GameOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class GameFrame extends JFrame {

    private Canvas canvas;
    private Game game;
    private GameWorld gameWorld;

    private GameObject player;

    public GameFrame(final Game game) {
        super(NAME);
        this.game = game;
        this.gameWorld = game.getGameWorld();
    }

    void init() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //выход из приложения по нажатию клавиши ESC
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER); //добавляем холст на наш фрейм
        pack();
        setResizable(false);
        setVisible(true);
    }

    public static int WINDOW_WIDTH = 400; //ширина
    public static int WINDOW_HEIGHT = 300; //высота
    public static int CANVAS_HEIGHT = 300; //высота canvas
    public static int CANVAS_WIDTH = 300; //высота canvas
    public static int TILE_SIZE = 20; //размер тайла
    public static int SPEED = 50; //миллисекунд на кадр
    public static int RANGE = 10; //дальность обзора
    public static String NAME = "Level 1";

    public void render() {
        List<GameObject> gameObjects = gameWorld.getGameObjects();
        Tile[][] map = gameWorld.getMap();
        player = map[0][0];
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2); //создаем BufferStrategy для нашего холста
            canvas.requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics(); //получаем Graphics из созданной нами BufferStrategy
        g.setColor(Color.black); //выбрать цвет
        g.fillRect(0, 0, getWidth(), getHeight());

        int centetRenderX = player.getX()-(WINDOW_WIDTH/2/ TILE_SIZE);
        int centetRenderY = player.getY()-(WINDOW_HEIGHT/2/ TILE_SIZE);

        int startRenderX = player.getX() - RANGE;
        int startRenderY = player.getY() - RANGE;

        if (startRenderX < 0) {
            startRenderX = 0;
        }
        if (startRenderY < 0) {
            startRenderY = 0;
        }

        int finalRenderX = player.getX() + RANGE;
        int finalRenderY = player.getY() + RANGE;
        if (finalRenderX > GameOptions.MAP_WIDTH) {
            finalRenderX = GameOptions.MAP_WIDTH;
        }
        if (finalRenderY > GameOptions.MAP_HEIGHT) {
            finalRenderY = GameOptions.MAP_HEIGHT;
        }


        for (int x = startRenderX; x < finalRenderX; x++) {
            for (int y = startRenderY; y < finalRenderY; y++) {
                Tile tile = map[x][y];
                int xC = (x - centetRenderX) * TILE_SIZE;
                int yC = (y - centetRenderY) * TILE_SIZE;
                Drawable drawable = tile.getDrawable();
                drawable.onRender(g, xC, yC);
            }
        }

        for (Iterator<GameObject> it = gameObjects.iterator(); it.hasNext();) {
            GameObject object = it.next();
            if ((startRenderX < object.getX() && object.getX() < finalRenderX) && (startRenderY < object.getY() && object.getY() < finalRenderY)) {
                Drawable drawable = object.getDrawable();
                int x = (object.getX() - centetRenderX) * TILE_SIZE;
                int y = (object.getY() - centetRenderY) * TILE_SIZE;
                drawable.onRender(g, x, y);
            }
        }

        g.dispose();
        bs.show(); //показать
    }

}