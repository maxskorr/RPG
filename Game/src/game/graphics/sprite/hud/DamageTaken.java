package game.graphics.sprite.hud;

import game.core.GameWorld;
import game.graphics.sprite.hud.model.Hud;

import java.awt.*;

/**
 * Created by max on 7/4/14.
 */
public class DamageTaken extends Hud {
    private final int damage;
    private long timestampCreated = System.currentTimeMillis();
    private final long LIFELONG = 400;

    public DamageTaken(final int x, final int y, final int damage, final GameWorld gameWorld) {
        super(y, x, gameWorld);
        this.damage = damage;
    }

    @Override
    public void afterRender(final Graphics graphics, int x, int y) {

    }

    @Override
    public void onRender(final Graphics graphics, int x, int y) {
        final char[] textChars = ("" + damage).toCharArray();
        final int textWidth = graphics.getFontMetrics().charsWidth(textChars, 0, textChars.length);
        final int textHeight = graphics.getFontMetrics().getHeight();

        graphics.setColor(Color.BLACK);
        graphics.fillRoundRect(x, y, textWidth + 10, textHeight, 5, 5);
        graphics.setColor(Color.RED);
        graphics.drawRoundRect(x, y, textWidth + 10, textHeight, 5, 5);
        graphics.drawChars(textChars, 0, textChars.length, (int) (x + textWidth * 0.25), (int) (y + textHeight * 0.8));

        final long dt = System.currentTimeMillis() - timestampCreated;

        if (dt >= LIFELONG)
            gameWorld.scheduledForDeleteHUD(this);
    }
}
