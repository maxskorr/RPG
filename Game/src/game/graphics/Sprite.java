package game.graphics;

import java.awt.*;

/**
 * Created by Max on 6/27/2014.
 */
public class Sprite implements Drawable {

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

    @Override
    public void beforeRender() {

    }

    @Override
    public void onRender(final Canvas canvas, final int x, final int y) {

    }

}
