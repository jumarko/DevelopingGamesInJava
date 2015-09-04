package com.example.games.java.ch02.images;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import com.example.games.java.ch02.SimpleScreenManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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

    public static String getResourcePath(String resourcePath) {
        notEmpty(resourcePath, "resourcePath cannot be empty!");

        final URL resource = ImageTest.class.getClassLoader().getResource(resourcePath);
        notNull(resource, "Cannot find any resource denoted by path: " + resourcePath);
        return resource.getFile();
    }



}
