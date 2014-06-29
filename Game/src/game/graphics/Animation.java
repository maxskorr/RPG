package game.graphics;

import java.awt.*;

/**
 * Created by Semyon Danilov on 27.06.2014.
 */
public class Animation {

    private int framesQuantity;

    private int currentFrame;

    private int duration;

    private Image[] frames;

    private boolean isTimeDependent;

    private boolean paused; // Находится ли анимация в состоянии паузы

    public Animation(final ImageSet frames) {
        this.frames = frames.getImages();
        setCurrentFrame(0);
        setFramesQuantity(this.frames.length);
        setTimeDependent(false);
        setPaused(true);
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public int getFramesQuantity() {
        return framesQuantity;
    }

    public void setFramesQuantity(final int framesQuantity) {
        this.framesQuantity = framesQuantity;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(final int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public Image[] getFrames() {
        return frames;
    }

    public void setFrames(final Image[] frames) {
        this.frames = frames;
    }

    public boolean isTimeDependent() {
        return isTimeDependent;
    }

    public void setTimeDependent(final boolean isTimeDependent) {
        this.isTimeDependent = isTimeDependent;
    }

    public Image next() {
        int currentFrame = getCurrentFrame();
        final Image frame = frames[paused ? currentFrame : currentFrame++];

        if (currentFrame >= framesQuantity) {
            currentFrame = 0;
        }

        setCurrentFrame(currentFrame);

        return frame;
    }
}
