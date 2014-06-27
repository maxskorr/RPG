package game.util;

import game.graphics.AbstractSprite;
import game.graphics.AnimatedSprite;
import game.graphics.Animation;
import game.graphics.Sprite;
import game.log.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max & Edik on 6/27/2014.
 */
public class ResourceManager {

    private static final Logger LOGGER = Logger.getLogger(ResourceManager.class);

    private static Map<String, AbstractSprite> sprites = new HashMap<>();
    private static Map<String, Integer> animationsPerTile = new HashMap<>();

    public static Image loadImage(final String title, final Object o) {
        BufferedImage sourceImage = null;
        try {
            LOGGER.log(o.toString());
            Class c = ResourceManager.class;
            ClassLoader cl = c.getClassLoader();
            URL url = cl.getResource("assets/" + title);
            System.out.println(url);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int totalFrames = sourceImage.getWidth() / GameOptions.TILE_SIZE;
        final int totalAnimations = sourceImage.getHeight() / GameOptions.TILE_SIZE;
        final ArrayList<Image> frames = new ArrayList<>();
        animationsPerTile.put(title, totalAnimations);

        for (int i = 0; i < totalAnimations; i++) {
            for (int j = 0; j < totalFrames; j++) {
                final int x = i * GameOptions.TILE_SIZE;
                final int y = j * GameOptions.TILE_SIZE;

                BufferedImage frame = sourceImage.getSubimage(x, y,
                        GameOptions.TILE_SIZE, GameOptions.TILE_SIZE);

                frames.add(Toolkit.getDefaultToolkit().createImage(frame.getSource()));
            }

            AbstractSprite sprite = null;

            if (frames.size() == 1) {
                sprite = new Sprite(frames.get(0));
            } else {
                Animation animation = new Animation((Image[]) frames.toArray());
                sprite = new AnimatedSprite(animation);
            }

            String key = title + "_" + i;

            sprites.put(key, sprite);
            frames.clear();
        }

        return Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
    }

    public static AbstractSprite getSprite(final String title, final int animationNumber) {
        String key = title + "_" + animationNumber;

        if (!sprites.containsKey(key)) {
            loadImage(title, new Object());
        }

        if (!sprites.containsKey(key)) {
            throw new NullPointerException();
        }

        return sprites.get(key);
    }

    public static int getAnimationsNumberByTitle(final String title) {
        if (!animationsPerTile.containsKey(title))
            loadImage(title, new Object());

        if (!animationsPerTile.containsKey(title))
            throw new NullPointerException();

        return animationsPerTile.get(title);
    }
}
