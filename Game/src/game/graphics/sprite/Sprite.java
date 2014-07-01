package game.graphics.sprite;

import game.graphics.sprite.model.AbstractSprite;

import java.awt.*;

/**
 * Created by Max on 6/27/2014.
 */
public class Sprite extends AbstractSprite {

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
    public void onRender(final Graphics graphics, final int x, final int y) {
        graphics.drawImage(image, x, y, null);
    }

}
