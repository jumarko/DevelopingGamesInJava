package com.example.games.java.ch02.images;

import static org.apache.commons.lang3.Validate.notEmpty;

import com.example.games.java.ch02.SimpleScreenManager;

import javax.swing.*;
import java.awt.*;

public class ImageReader {

    public Image readImage(String imagePath) {
        notEmpty(imagePath, "imagePath cannot be empty!");

        // following code triggers loading image in separate thread
        // if we try to display it before it's loaded we might not see anything
//        Toolkit.getDefaultToolkit().getImage(imagePath);
        // => It's better to use ImageIcon which handles this transparently via MediaTracker class

        final ImageIcon imageIcon = new ImageIcon(imagePath);
        return imageIcon.getImage();
    }

    public static void main(String[] args) {

    }
}
