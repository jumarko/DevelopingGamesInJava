package com.example.games.java.ch02;

import javax.swing.*;
import java.awt.*;

/**
 * @deprecated check new {@link DoubleBufferScreenManager} which uses double buffering
 */
public class SimpleScreenManager {

    private final GraphicsDevice device;

    public SimpleScreenManager() {
        this.device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }

    public void setFullScreen(DisplayMode displayMode, JFrame window) {
        window.setUndecorated(true);
        window.setResizable(false);
        device.setFullScreenWindow(window);

        if (displayMode != null && device.isDisplayChangeSupported()) {
            try {
                device.setDisplayMode(displayMode);
            } catch (IllegalArgumentException e) {
                // ignore - invalid display mode
            }
        }
    }

    public Window getFullScreenWindow() {
        return device.getFullScreenWindow();
    }

    public void restoreScreen() {
        final Window window = device.getFullScreenWindow();
        if (window != null) {
            window.dispose();
        }
        device.setFullScreenWindow(null);
    }
}
