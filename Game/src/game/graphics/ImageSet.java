package game.graphics;

import java.awt.*;
import java.util.Arrays;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class ImageSet {

    private Image[] images;

    public int size() {
        return images.length;
    }

    public ImageSet(final Image... images) {
        this.images = images;
    }

    public Image getImage(final int i) {
        return images[i];
    }

    public Image[] getImages() {
        return images;
    }

    public Image[] getImages(final int x, final int n) {
        Image[] images = Arrays.copyOfRange(this.images, x, x + n);

        return images;
    }

    public boolean hasMany() {
        return images.length > 1;
    }

}
