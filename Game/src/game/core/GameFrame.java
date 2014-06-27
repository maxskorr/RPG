package game.core;

import game.graphics.Tile;
import game.model.GameObject;

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
        this.game = game;
        this.gameWorld = game.getGameWorld();
    }

    void init() {

    }

    public static int WINDOW_WIDTH = 400; //ширина
    public static int WINDOW_HEIGHT = 300; //высота
    public static int TILE_SIZE = 20; //размер тайла
    public static int SPEED = 50; //миллисекунд на кадр
    public static int RANGE = 10; //дальность обзора
    public static String NAME = "Level 1";

    public void render() {
        List<GameObject> gameObjects = gameWorld.getGameObjects();
        Tile[][] map = gameWorld.getMap();

        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2); //создаем BufferStrategy для нашего холста
            requestFocus();
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
        if (finalRenderX > canvas.getWidth()) {
            finalRenderX = canvas.getWidth();
        }
        if (finalRenderY > canvas.getHeight()) {
            finalRenderY = canvas.getHeight();
        }


        for (int x = startRenderX; x < finalRenderX; x++) {
            for (int y = startRenderY; y < finalRenderY; y++) {
                g.drawImage(map[x][y].getImage(), (x-centetRenderX) * TILE_SIZE, (y-centetRenderY) * TILE_SIZE, null);
            }
        }
        for (Iterator<GameObject> it = gameObjects.iterator(); it.hasNext();) {
            GameObject object = it.next();
            if ((startRenderX < object.getX() && object.getX() < finalRenderX) && (startRenderY < object.getY() && object.getY() < finalRenderY)) {
                g.drawImage(object.getImage(), (object.getX()-centetRenderX) * TILE_SIZE, (object.getY()-centetRenderY) * TILE_SIZE, null);
            }
        }

        g.dispose();
        bs.show(); //показать
    }

}