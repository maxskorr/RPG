package game.graphics;

import java.awt.*;

/**
 * Created by Max on 6/27/2014.
 */
public interface Drawable {

    public void beforeRender();

    public void onRender(final Canvas canvas, final int x, final int y);

}
