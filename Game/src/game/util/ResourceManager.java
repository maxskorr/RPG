package game.util;

import game.model.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max on 6/27/2014.
 */
public class ResourceManager {
    private static Map<String, Image> images = new HashMap<>();

    public static Image getImage(String title, Object o) {
        if(images.containsKey(title)){
            return images.get(title);
        }else{
            images.put(title, loadImage(title, o));
            return images.get(title);
        }
    }

    public static Image loadImage(String title, Object o) {
        BufferedImage sourceImage = null;
        try {
            URL url = o.getClass().getClassLoader().getResource("assets/" + title);
            System.out.println(url);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
    }

    public static Sprite getSprite(String title, Object o) {
        return new Sprite(getImage(title, o));
    }
}
