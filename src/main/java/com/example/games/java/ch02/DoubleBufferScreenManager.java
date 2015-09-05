package com.example.games.java.ch02;

import static org.apache.commons.lang3.Validate.notNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

/**
 * Screen manager that uses double buffering techniques.
 * @see BufferStrategy
 */
public class DoubleBufferScreenManager {

    private final GraphicsDevice device;

    public DoubleBufferScreenManager() {
        this.device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }

    public DisplayMode[] getCompatibleDisplayModes() {
        return device.getDisplayModes();
    }

    private DisplayMode findFirstCompatibleMode(DisplayMode[] modes) {
        notNull(modes, "displayModes cannot be null!");

        final DisplayMode[] goodModes = getCompatibleDisplayModes();
        for (final DisplayMode mode : modes) {
            for (final DisplayMode goodMode : goodModes) {
                if (displayModesMatch(mode, goodMode)) {
                    return mode;
                }
            }
        }

        return null;
    }

    public DisplayMode getCurrentDisplayMode() {
        return device.getDisplayMode();
    }

    /**
     * Check if two display modes are equal
     * - the bit depth is ignored if one of modes has value {@link DisplayMode#BIT_DEPTH_MULTI}
     * - the refresh reate is ignored if one of modes has value {@link DisplayMode#REFRESH_RATE_UNKNOWN}
     */
    private boolean displayModesMatch(DisplayMode mode1, DisplayMode mode2) {
        notNull(mode2, "goodMode cannot be null!");
        notNull(mode1, "mode cannot be null!");

        // TODO: couldn't we just use DisplayMode#equals ??
        if(mode1.getWidth() != mode2.getWidth() || mode1.getHeight() != mode2.getHeight()) {
            return false;
        }

        if (mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
            && mode1.getBitDepth() != mode2.getBitDepth()) {
            return false;
        }

        if (mode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && mode2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
                && mode1.getRefreshRate() != mode2.getRefreshRate()) {
            return false;
        }

        return true;
    }

    public void setFullScreenToCompatibleDisplayMode(DisplayMode[] possibleModes) {
        setFullScreen(findFirstCompatibleMode(possibleModes));
    }

    public void setFullScreen(DisplayMode displayMode) {

        final JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        // we are using active rendering so there's no need to react on OS paint events
        window.setIgnoreRepaint(true);
        window.setResizable(false);
        device.setFullScreenWindow(window);

        if (displayMode != null && device.isDisplayChangeSupported()) {
            try {
                device.setDisplayMode(displayMode);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                // invalid display mode
                // modify for Mac OS X
                window.setSize(displayMode.getWidth(), displayMode.getHeight());
            }
        }

        // workaround for possible dead-lock
        try {
            EventQueue.invokeAndWait(() -> window.createBufferStrategy(2));
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns graphics context.
     * This ScreenManager uses double buffering thus applications have to call {@code update()}
     * when they want to display graphics.
     * Applications are responsible for grahpics' disposal.
     */
    public Graphics2D getGraphics() {
        final Window window = device.getFullScreenWindow();
        if (window != null) {
            return (Graphics2D) window.getBufferStrategy().getDrawGraphics();
        }
        return null;
    }

    /**
     * Update display.
     */
    public void update() {
        final JFrame window = getFullScreenWindow();
        if (window != null) {
            final BufferStrategy strategy = window.getBufferStrategy();
            if (!strategy.contentsLost()) {
                strategy.show();
            }
        }

        // synchronize display on some systems - on Linux this is being used for solving issues with Event queue
        Toolkit.getDefaultToolkit().sync();
    }


    public JFrame getFullScreenWindow() {
        return (JFrame) device.getFullScreenWindow();
    }

    public int getWidth() {
        final JFrame window = getFullScreenWindow();
        if (window == null) {
            return 0;
        }

        return window.getWidth();
    }

    public int getHeight() {
        final JFrame window = getFullScreenWindow();
        if (window == null) {
            return 0;
        }
        return window.getHeight();
    }

    public void restoreScreen() {
        final Window window = device.getFullScreenWindow();
        if (window != null) {
            window.dispose();
        }
        device.setFullScreenWindow(null);
    }

    /**
     * Creates image compatible with current graphics configuration (i.e. the same bit depth and color model).
     */
    public BufferedImage createCompatibleImage(int w, int h, int transparency) {
        final JFrame window = getFullScreenWindow();
        if (window == null) {
            return null;
        }
        return window.getGraphicsConfiguration().createCompatibleImage(w, h, transparency);
    }
}
