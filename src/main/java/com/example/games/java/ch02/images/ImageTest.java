package com.example.games.java.ch02.images;

import static com.example.games.java.ch02.images.ImageReader.getResourcePath;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import com.example.games.java.ch02.SimpleScreenManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageTest extends JFrame {

    public static final int DEMO_TIME_MILLIS = 5000;
    public static final String PLAYER_IMAGE_PATH = "ch02/images/player1.png";
    public static final int FONT_SIZE = 24;

    private final ImageReader imageReader = new ImageReader();

    private volatile boolean imagesLoaded;
    private Image bgImage;
    private Image opaqueImage;
    private Image transparentImage;
    private Image translucentImage;
    private Image antiAliasedImage;


    public static void main(String[] args) {
        new ImageTest().run();
    }

    @Override
    public void paint(Graphics g) {
        if (g instanceof Graphics2D) {
            final Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }

        if (imagesLoaded) {
            g.drawImage(bgImage, 0, 0, null);
            drawImage(g, opaqueImage, 0, 0, "Opaque");
            drawImage(g, transparentImage, 320, 0, "Transparent");
            drawImage(g, translucentImage, 0, 300, "Translucent");
            drawImage(g, antiAliasedImage, 320, 300, "Transparent (antialiased)");

        } else {
            g.drawString("Loading images...!", 5, FONT_SIZE);
        }
    }

    private void drawImage(Graphics g, Image image, int x, int y, String caption) {
        g.drawImage(image, x, y, null);
        g.drawString(caption, x + 5, y + FONT_SIZE + image.getHeight(null));
    }

    private void run() {
        final DisplayMode displayMode = new DisplayMode(1024, 768, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
        setBackground(Color.yellow);
        setForeground(Color.red);
        setFont(new Font("Dialog", 0, FONT_SIZE));

        final SimpleScreenManager screen = new SimpleScreenManager();
        try {
            screen.setFullScreen(displayMode, this);
            loadImages();
            Thread.sleep(DEMO_TIME_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            screen.restoreScreen();
        }
    }

    private void loadImages() {
        bgImage = imageReader.readImage(getResourcePath("ch02/images/background.jpg"));
        opaqueImage = imageReader.readImage(getResourcePath("ch02/images/opaque.png"));
        transparentImage = imageReader.readImage(getResourcePath("ch02/images/transparent.png"));
        translucentImage = imageReader.readImage(getResourcePath("ch02/images/translucent.png"));
        antiAliasedImage = imageReader.readImage(getResourcePath("ch02/images/antialiased.png"));
        imagesLoaded = true;

        // signals AWT to repaint window
        repaint();
    }


}
