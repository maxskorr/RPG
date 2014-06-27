package game.graphics;

import java.awt.*;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class ImageSet {

    private Image[] images;

    public ImageSet(final Image... images) {
        this.images = images;
    }

    public Image getImage(final int i) {
        return images[i];
    }

    public Image[] getImages() {
        return images;
    }

    public boolean hasMany() {
        return images.length > 1;
    }

}
