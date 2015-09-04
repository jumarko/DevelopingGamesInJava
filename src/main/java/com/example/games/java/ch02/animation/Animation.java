package com.example.games.java.ch02.animation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the series of images and display duration.
 */
public class Animation {

    private List<AnimFrame> frames;
    private int currFrameIndex;
    private long animTime;
    private long totalDuration;

    public Animation() {
        frames = new ArrayList<>();
        totalDuration = 0;
        start();
    }

    public synchronized void start() {
        animTime = 0;
        currFrameIndex = 0;
    }

    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    public synchronized void update(long elapsedTime) {
        if (frames.size() > 1) {
            animTime += elapsedTime;
        }

        if (animTime >= totalDuration) {
            animTime = animTime % totalDuration;
            currFrameIndex = 0;
        }

        while (animTime > getFrame(currFrameIndex).endTime) {
            currFrameIndex++;
        }
    }

    public synchronized Image getImage() {
        if (frames.isEmpty()) {
            return null;
        }
        return getFrame(currFrameIndex).image;
    }



    private AnimFrame getFrame(int index) {
        return frames.get(index);
    }


    private static class AnimFrame {
        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }

}
