package com.example.games.java.ch02.animation;

import static com.example.games.java.ch02.animation.AnimationTest2.POSSIBLE_MODES;
import static com.example.games.java.ch02.images.ImageReader.getResourcePath;
import static com.example.games.java.ch02.images.ImageReader.readImage;

import com.example.games.java.ch02.DoubleBufferScreenManager;

import java.awt.*;

public class SpriteTest1 {

    private static final long DEMO_TIME = 10000;

    private DoubleBufferScreenManager screen;
    private Image bgImage;
    private Sprite sprite;

    public static void main(String[] args) {
        new SpriteTest1().run();
    }

    private void run() {
        screen = new DoubleBufferScreenManager();
        try {
            screen.setFullScreenToCompatibleDisplayMode(POSSIBLE_MODES);
            loadImages();
            animationLoop();
        } finally {
            screen.restoreScreen();
        }
    }

    private void animationLoop() {
        final long start = System.currentTimeMillis();
        long current = start;

        while (current - start < DEMO_TIME) {
            long elapsedTime = System.currentTimeMillis() - current;
            current += elapsedTime;

            // update sprites
            update(elapsedTime);

            // update screen
            final Graphics2D graphics = screen.getGraphics();
            try {
                draw(graphics);
            } finally {
                graphics.dispose();
            }
            screen.update();

            // rest for a while
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    private void update(long elapsedTime) {
        // check sprite's bounds
        if (sprite.getX() < 0) {
            sprite.setVelocityX(Math.abs(sprite.getVelocityX()));
        } else if (sprite.getX() + sprite.getWidth() >= screen.getWidth()) {
            sprite.setVelocityX(-Math.abs(sprite.getVelocityX()));
        }

        if (sprite.getY() < 0) {
            sprite.setVelocityY(Math.abs(sprite.getVelocityY()));
        } else if (sprite.getY() + sprite.getHeigth() >= screen.getHeight()) {
            sprite.setVelocityY(-Math.abs(sprite.getVelocityY()));
        }

        sprite.update(elapsedTime);
    }

    private void draw(Graphics2D graphics) {
        graphics.drawImage(bgImage, 0, 0, null);

        graphics.drawImage(sprite.getImage(),
                Math.round(sprite.getX()),
                Math.round(sprite.getY()),
                null);
    }

    private void loadImages() {

        bgImage = readImage(getResourcePath("ch02/images/background.jpg"));
        final Image player1 = readImage(getResourcePath("ch02/images/player1.png"));
        final Image player2 = readImage(getResourcePath("ch02/images/player2.png"));
        final Image player3 = readImage(getResourcePath("ch02/images/player3.png"));


        final Animation anim = new Animation();
        anim.addFrame(player1, 250);
        anim.addFrame(player2, 150);
        anim.addFrame(player1, 150);
        anim.addFrame(player2, 150);
        anim.addFrame(player3, 200);
        anim.addFrame(player2, 150);

        sprite = new Sprite(anim);

        // Get moving, sprite!
        sprite.setVelocityX(0.2f);
        sprite.setVelocityY(0.2f);
    }
}
