package com.example.games.java.ch02.images;

import static com.example.games.java.ch02.DisplayModeUtils.getDisplayMode;
import static com.example.games.java.ch02.images.ImageReader.getResourcePath;
import static com.example.games.java.ch02.images.ImageReader.readImage;
import static java.awt.RenderingHints.*;
import static java.lang.Integer.parseInt;

import com.example.games.java.ch02.DisplayModeUtils;
import com.example.games.java.ch02.SimpleScreenManager;

import javax.swing.*;
import java.awt.*;

/**
 * Microbenchmark for various kind of images.
 */
public class ImageSpeedTest extends JFrame {


    private static final int FONT_SIZE = 24;
    private static final long TIME_PER_IMAGE_MILLIS = 1500;

    private ImageReader imageReader = new ImageReader();

    private volatile boolean imagesLoaded;
    private SimpleScreenManager screen;
    private Image bgImage;
    private Image opaqueImage;
    private Image transparentImage;
    private Image translucentImage;
    private Image antiAliasedImage;

    public static void main(String[] args) {
        new ImageSpeedTest().run(getDisplayMode(args));
    }

    @Override
    public void paint(Graphics g) {
        if (g instanceof Graphics2D) {
            ((Graphics2D) g).setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
        }

        if (imagesLoaded) {
            drawImage(g, opaqueImage, "Opaque");
            drawImage(g, transparentImage, "Transparent");
            drawImage(g, translucentImage, "Translucent");
            drawImage(g, antiAliasedImage, "Antialiased");

            // notify that test is finished
            synchronized (this) {
                notify();
            }
        } else {
            g.drawString("Loading images...", 5, FONT_SIZE);
        }
    }

    private void drawImage(Graphics g, Image image, String name) {
        int width = screen.getFullScreenWindow().getWidth() - image.getWidth(null);
        int height = screen.getFullScreenWindow().getHeight() - image.getHeight(null);
        int numImages = 0;

        g.drawImage(bgImage, 0, 0, null);

        final long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < TIME_PER_IMAGE_MILLIS) {
            int x = Math.round((float) Math.random() * width);
            int y = Math.round((float) Math.random() * height);
            g.drawImage(image, x, y, null);
            numImages++;
        }

        final long timeSpent = System.currentTimeMillis() - start;
        float speed = numImages * 1000f / timeSpent;
        System.out.println(name + ": " + speed + " images per second");

    }

    private void run(DisplayMode displayMode) {
        setBackground(Color.yellow);
        setForeground(Color.white);
        setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));

        imagesLoaded = false;
        screen = new SimpleScreenManager();
        try {
            screen.setFullScreen(displayMode, this);
            synchronized (this) {
                loadImages();
                // wait for test finish
                try {
                    wait();
                } catch (InterruptedException ie) {

                }
            }
        } finally {
            screen.restoreScreen();
        }
    }

    private void loadImages() {
        bgImage = readImage(getResourcePath("ch02/images/background.jpg"));
        opaqueImage = readImage(getResourcePath("ch02/images/opaque.png"));
        transparentImage = readImage(getResourcePath("ch02/images/transparent.png"));
        translucentImage = readImage(getResourcePath("ch02/images/translucent.png"));
        antiAliasedImage = readImage(getResourcePath("ch02/images/antialiased.png"));
        imagesLoaded = true;

        // signals AWT to repaint window
        repaint();

    }


}
