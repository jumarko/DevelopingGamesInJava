package com.example.games.java.ch02.animation;

import static com.example.games.java.ch02.DisplayModeUtils.getDisplayMode;
import static com.example.games.java.ch02.images.ImageReader.getResourcePath;
import static com.example.games.java.ch02.images.ImageReader.readImage;
import static org.apache.commons.lang3.Validate.notNull;

import com.example.games.java.ch02.SimpleScreenManager;
import com.example.games.java.ch02.DoubleBufferScreenManager;

import javax.swing.*;
import java.awt.*;

/**
 * Updated version of {@link AnimationTest1} which uses double buffering ({@link DoubleBufferScreenManager}
 */
public class AnimationTest2 {

    private static final long DEMO_TIME_MILLIS = 5000;

    public static final DisplayMode[] POSSIBLE_MODES = {
            new DisplayMode(1280, 800, 32, 0),
            new DisplayMode(1280, 800, 24, 0),
            new DisplayMode(1280, 800, 16, 0)
    };

    private DoubleBufferScreenManager screen;
    private Image bgImage;
    private Image player1;
    private Image player2;
    private Image player3;
    private Animation anim;

    public static void main(String[] args) {
        new AnimationTest2().run();
    }

    private void run() {
        try {
            screen = new DoubleBufferScreenManager();
            screen.setFullScreenToCompatibleDisplayMode(POSSIBLE_MODES);
            loadImages();
            animationLoop();
        } finally {
            screen.restoreScreen();
        }
    }

    private void loadImages() {
        bgImage = readImage(getResourcePath("ch02/images/background.jpg"));
        player1 = readImage(getResourcePath("ch02/images/player1.png"));
        player2 = readImage(getResourcePath("ch02/images/player2.png"));
        player3 = readImage(getResourcePath("ch02/images/player3.png"));

        anim = new Animation();
        anim.addFrame(player1, 250);
        anim.addFrame(player2, 150);
        anim.addFrame(player1, 150);
        anim.addFrame(player2, 150);
        anim.addFrame(player3, 200);
        anim.addFrame(player2, 150);
    }

    private void animationLoop() {
        final long startTime = System.currentTimeMillis();
        long currTime = startTime;

        while (currTime - startTime < DEMO_TIME_MILLIS) {
            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;

            anim.update(elapsedTime);

            final Graphics graphics = screen.getGraphics();
            draw(graphics);
            graphics.dispose();
            screen.update();

            // rest
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) { }
        }
    }

    private void draw(Graphics graphics) {
        notNull(graphics, "graphics cannot be null!");
        graphics.drawImage(bgImage, 0, 0, null);
        graphics.drawImage(anim.getImage(), 0, 0, null);
    }
}
