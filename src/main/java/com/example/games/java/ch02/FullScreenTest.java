package com.example.games.java.ch02;

import static com.example.games.java.ch02.images.ImageReader.getResourcePath;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import com.example.games.java.ch02.images.ImageReader;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class FullScreenTest extends JFrame {

    public static final int DEMO_TIME_MILLIS = 5000;
    public static final String PLAYER_IMAGE_PATH = "ch02/images/player1.png";

    private final ImageReader imageReader = new ImageReader();

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
        final Image playerImage = imageReader.readImage(getResourcePath(PLAYER_IMAGE_PATH));
        g.drawImage(playerImage, 100, 100, null);
    }


    private void run() {
        final DisplayMode displayMode = new DisplayMode(1024, 768, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
        setBackground(Color.yellow);
        setForeground(Color.red);
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
