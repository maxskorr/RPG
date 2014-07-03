package game.graphics;

import java.awt.*;

/**
 * Created by Max on 6/27/2014.
 */
public interface Drawable {

    public void afterRender(final Graphics graphics, final int x, final int y);

    public void onRender(final Graphics graphics, final int x, final int y);
}
