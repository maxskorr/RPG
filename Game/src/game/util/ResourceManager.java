package game.util;

import game.graphics.Animation;
import game.graphics.ImageSet;
import game.graphics.sprite.AnimatedSprite;
import game.graphics.sprite.DebugSprite;
import game.graphics.sprite.Sprite;
import game.graphics.sprite.model.AbstractSprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static game.util.GameOptions.ANIMATION_DELAY_MILLISECONDS;

/**
 * Created by Max & Edik on 6/27/2014.
 */
public class ResourceManager {

    private static final Logger LOGGER = Logger.getLogger(ResourceManager.class);
    public static final Random random = new Random();

    private static Map<String, ImageSet> imageSets = new HashMap<>();
    private static Map<String, Integer> animationsPerTile = new HashMap<>();
    private static Map<String, ArrayList<ImageSet>> complexImages = new HashMap<>(); // Хэш сложных картинок

    public static URL getPath(final String filename) {
        final Class c = ResourceManager.class;
        final ClassLoader cl = c.getClassLoader();
        final URL url = cl.getResource(filename);

        return url;
    }

    private static Image loadImage(final String filename, final Object o) {
        BufferedImage sourceImage = null;
        try {
            LOGGER.log(o.toString());
            final URL url = getPath(GameOptions.ASSETS_GRAPHICS_PATH + filename);

            System.out.println(url);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int totalFrames = sourceImage.getWidth() / GameOptions.TILE_SIZE;
        final int totalAnimations = sourceImage.getHeight() / GameOptions.TILE_SIZE;
        animationsPerTile.put(filename, totalAnimations);

        final ArrayList<Image> frames = new ArrayList<>(totalFrames);

        for (int i = 0; i < totalAnimations; i++) {
            for (int j = 0; j < totalFrames; j++) {
                final int x = j * GameOptions.TILE_SIZE;
                final int y = i * GameOptions.TILE_SIZE;

                BufferedImage frame = sourceImage.getSubimage(x, y,
                        GameOptions.TILE_SIZE, GameOptions.TILE_SIZE);

                frames.add(Toolkit.getDefaultToolkit().createImage(frame.getSource()));
            }

            ImageSet imageSet = null;

            if (frames.size() == 1) {
                imageSet = new ImageSet(frames.get(0));
            } else {
                Image[] images = Arrays.copyOf(frames.toArray(), frames.size(), Image[].class);
                imageSet = new ImageSet(images);
            }

            String key = filename + "_" + i;

            imageSets.put(key, imageSet);
            frames.clear();
        }

        return Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
    }

    /**
     * Загрузить сложную картинку, из которой будут вырезаны все спрайты,
     * и добавить её в хэш
     * @param filename Имя файла картинки
     * @param o Пустой объект(не null)
     * @return Матрица тайлов(сложная картинка)
     */
    private static ArrayList<ImageSet> loadComplexImage(final String filename, final Object o) {
        BufferedImage sourceImage = null;

        try {
            LOGGER.log(o.toString());
            final URL url = getPath(GameOptions.ASSETS_GRAPHICS_PATH + filename);

            System.out.println(url);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int totalRows = sourceImage.getHeight() / GameOptions.TILE_SIZE;
        final int totalColumns = sourceImage.getWidth() / GameOptions.TILE_SIZE;

        final ArrayList<ImageSet> complexImage = new ArrayList<>();

        for (int i = 0; i < totalRows; i++) {
            final Image[] rowOfImages = new Image[totalColumns];

            for (int j = 0; j < totalColumns; j++) {
                final int x = j * GameOptions.TILE_SIZE;
                final int y = i * GameOptions.TILE_SIZE;

                BufferedImage tileImage = sourceImage.getSubimage(x, y,
                        GameOptions.TILE_SIZE, GameOptions.TILE_SIZE);

                rowOfImages[j] = Toolkit.getDefaultToolkit().createImage(tileImage.getSource());
            }

            final ImageSet imageSet = new ImageSet(rowOfImages);
            complexImage.add(imageSet);
        }

        complexImages.put(filename, complexImage);

        return complexImage;
    }

    public static AbstractSprite getSprite(final String title, final int animationNumber) {
        String key = title + "_" + animationNumber;

        if (!imageSets.containsKey(key)) {
            loadImage(title, new Object());
        }

        if (!imageSets.containsKey(key)) {
            throw new NullPointerException();
        }

        ImageSet imageSet = imageSets.get(key);
        AbstractSprite sprite = null;
        if (imageSet.hasMany()) {
            Animation animation = new Animation(imageSet);
            sprite = new AnimatedSprite(animation);
            animation.setFrameDelay(ANIMATION_DELAY_MILLISECONDS);
            animation.setTimeDependent(true);
        } else {
            sprite = new Sprite(imageSet.getImage(0));
        }
        return Build.DEBUG ? new DebugSprite(sprite) : sprite;
    }

    /**
     *
     * @param x Номер клетки по горизонтали
     * @param y Номер клетки по вертикали
     * @param rows Количество клетов по вертикали
     * @param cols Количество клеток по горизонтали
     * @return Возвращает
     */
    public static AbstractSprite getSpriteFromComplexImage(final String imageFilename,
                                                           final int x, final int y,
                                                           final int rows, final int cols) {
        if (!complexImages.containsKey(imageFilename))
            loadComplexImage(imageFilename, new Object());

        if (!complexImages.containsKey(imageFilename) || complexImages.get(imageFilename).size() <= 0)
            throw new NullPointerException();

        final ArrayList<ImageSet> complexImage = complexImages.get(imageFilename);

        if (cols <= 0 || x < 0  || y < 0)
            throw new IllegalArgumentException();

        if (y >= complexImage.size() || x >= complexImage.get(0).size())
            throw new ArrayIndexOutOfBoundsException();

        if (rows > 1)
            throw new NoSuchElementException(); // Ещё не поддерживается

        final AbstractSprite sprite;

        if (cols == 1) {
            sprite = new Sprite(complexImage.get(y).getImage(x));
        } else {
            sprite = new AnimatedSprite(new Animation(new ImageSet(complexImage.get(y).getImages(x, cols))));
        }

        return Build.DEBUG ? new DebugSprite(sprite) : sprite;
    }

    public static int getAnimationsNumberByTitle(final String title) {
        if (!animationsPerTile.containsKey(title))
            loadImage(title, new Object());

        if (!animationsPerTile.containsKey(title))
            throw new NullPointerException();

        return animationsPerTile.get(title);
    }
}
