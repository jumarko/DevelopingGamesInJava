package com.example.games.java.ch02;

import javax.swing.*;
import java.awt.*;

public class FullScreenTest extends JFrame {

    public static final int DEMO_TIME_MILLIS = 5000;

    public static void main(String[] args) {
        new FullScreenTest().run();
    }

    @Override
    public void paint(Graphics g) {
        if (g instanceof Graphics2D) {
            final Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.drawString("Hello, World!", 20, 50);
    }

    private void run() {
        final DisplayMode displayMode = new DisplayMode(1024, 768, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
        setBackground(Color.blue);
        setForeground(Color.white);
        setFont(new Font("Dialog", 0, 24));

        final SimpleScreenManager screen = new SimpleScreenManager();
        try {
            screen.setFullScreen(displayMode, this);
            Thread.sleep(DEMO_TIME_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            screen.restoreScreen();
        }
    }


}
