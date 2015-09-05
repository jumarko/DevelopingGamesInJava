package com.example.games.java.ch02.animation;

import static org.apache.commons.lang3.Validate.notNull;

import java.awt.*;

/**
 * Sprite is a graphic that is able to move on the screen independently.
 */
public class Sprite {

    private final Animation animation;

    // position
    private float x;
    private float y;
    // speed - pixels per millisecond
    private float velocityX;
    private float velocityY;


    public Sprite(Animation animation) {
        notNull(animation, "animation cannot be null!");
        this.animation = animation;
    }

    /**
     * Updates position and animation of this sprite
     */
    public void update(long elapsedTime) {
        x += velocityX * elapsedTime;
        y += velocityY * elapsedTime;
        animation.update(elapsedTime);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public int getWidth() {
        return animation.getImage().getWidth(null);
    }

    public int getHeigth() {
        return animation.getImage().getHeight(null);
    }

    public Image getImage() {
        return animation.getImage();
    }
}
