package game.core;

import game.core.camera.Camera;
import game.core.camera.SimpleCamera;
import game.gameobject.model.GameObject;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class GameFrame extends JFrame {

    private Canvas canvas;
    private Game game;
    private GameWorld gameWorld;
    private GameObject player;
    private List<Camera> cameras = new LinkedList<>();

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
        canvas.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        Camera fpCamera = new SimpleCamera(game, canvas, player, CAMERA_WIDTH, CAMERA_HEIGHT);
        GameObject secondPlayer = gameWorld.getSecondPlayer();
        Camera spCamera = new SimpleCamera(game, canvas, secondPlayer, CAMERA_WIDTH, CAMERA_HEIGHT);
        cameras.add(fpCamera);
        cameras.add(spCamera);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //выход из приложения по нажатию клавиши ESC
        setLayout(new BorderLayout());
        add(getCanvas(), BorderLayout.CENTER); //добавляем холст на наш фрейм
        pack();
        setResizable(false);
        setVisible(true);
    }

    public static int WINDOW_WIDTH = 400; //ширина
    public static int WINDOW_HEIGHT = 300; //высота
    public static int CAMERA_HEIGHT = 320; //высота canvas
    public static int CAMERA_WIDTH = 420; //высота canvas
    public static int TILE_SIZE = 20; //размер тайла
    public static String NAME = "Level 1";

    public void render() {
        for (Camera camera : cameras) {
            camera.render();
        }
    }

    public Camera getCamera() {
        return cameras.get(0);
    }

}