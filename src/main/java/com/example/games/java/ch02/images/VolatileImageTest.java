package com.example.games.java.ch02.images;

import com.example.games.java.ch02.SimpleScreenManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;

public class VolatileImageTest extends JFrame {

    public static final int DEMO_TIME_MILLIS = 5000;
    public static final String PLAYER_IMAGE_PATH = "ch02/images/player1.png";
    public static final int FONT_SIZE = 24;

    private final ImageReader imageReader = new ImageReader();


    public static void main(String[] args) {
        new VolatileImageTest().run();
    }

    @Override
    public void paint(Graphics g) {

        if (g instanceof Graphics2D) {
            final Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }

        g.drawString("Waiting for volatile image", 100, 100);

        VolatileImage volatileImage = createVolatileImage(400, 300);
        // draw the volatile image - DOES NOT WORK!!!
//        do {
//            int valid = volatileImage.validate(getGraphicsConfiguration());
//            if (valid == VolatileImage.IMAGE_INCOMPATIBLE) {
//                // not compatible with display - recreate
//                volatileImage = createVolatileImage(400, 300);
//            } else if (valid == VolatileImage.IMAGE_RESTORED) {
//                final Graphics2D graphics = volatileImage.createGraphics();
//                graphics.draw(new Rectangle(100, 100, 200, 600));
//                graphics.drawString("HALLO", 500, 500);
////                graphics.dispose();
//            } else {
//                // draw the picture on the screen
//                g.drawImage(volatileImage, 0, 0, null);
////                g.dispose();
//            }
//        } while (volatileImage.contentsLost());


    }

    private void run() {
        final DisplayMode displayMode = new DisplayMode(1024, 768, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
        setBackground(Color.yellow);
        setForeground(Color.red);
        setFont(new Font("Dialog", 0, FONT_SIZE));

        final SimpleScreenManager screen = new SimpleScreenManager();
        try {
            screen.setFullScreen(displayMode, this);
            repaint();
            Thread.sleep(DEMO_TIME_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            screen.restoreScreen();
        }
    }

}
