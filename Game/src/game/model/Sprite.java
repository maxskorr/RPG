package game.model;

import java.awt.*;

/**
 * Created by Max on 6/27/2014.
 */
public class Sprite {
    private Image image;

    public Sprite(Image image) {
        setImage(image);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
