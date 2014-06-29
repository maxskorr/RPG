package game.controller.keyboard;

import game.util.Pool;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Semyon Danilov on 28.06.2014.
 */
public class KeyboardHandler implements KeyListener {

    private Lock inputLock = new ReentrantLock();

    private boolean[] pressedKeys = new boolean[127];

    private List<KeyEvent> keyEvents = new ArrayList<>();

    private List<KeyEvent> keyEventsBuffer = new ArrayList<>();

    private Pool<KeyEvent> keyEventPool = new Pool<>(50, new Pool.Factory<KeyEvent>() {
        @Override
        public KeyEvent newInstance() {
            return new KeyEvent();
        }
    });

    public void onKey(final java.awt.event.KeyEvent event) {
        int id = event.getID();
        int keyCode = event.getKeyCode();
        char keyChar = event.getKeyChar();
        KeyEvent keyEvent = keyEventPool.get();
        keyEvent.keyCode = keyCode;
        keyEvent.keyChar = keyChar;
        inputLock.lock();
        try {
            keyEventsBuffer.add(keyEvent);
            switch (id) {
                case java.awt.event.KeyEvent.KEY_PRESSED:
                    keyEvent.type = KeyEvent.KEY_DOWN;
                    pressedKeys[keyCode] = true;
                    break;
                case java.awt.event.KeyEvent.KEY_RELEASED:
                    keyEvent.type = KeyEvent.KEY_UP;
                    pressedKeys[keyCode] = false;
                    break;
            }
        } finally {
            inputLock.unlock();
        }
    }

    public List<KeyEvent> getKeyEvents() {
        inputLock.lock();
        try {
            for (KeyEvent event : keyEvents) {
                keyEventPool.free(event);
            }
            keyEvents.clear();
            keyEvents.addAll(keyEventsBuffer);
            keyEventsBuffer.clear();
        } finally {
            inputLock.unlock();
        }
        return keyEvents;
    }

    @Override
    public void keyTyped(final java.awt.event.KeyEvent e) {
        //нафиг надо
    }

    @Override
    public void keyPressed(final java.awt.event.KeyEvent e) {
        onKey(e);
    }

    @Override
    public void keyReleased(final java.awt.event.KeyEvent e) {
        onKey(e);
    }

}

