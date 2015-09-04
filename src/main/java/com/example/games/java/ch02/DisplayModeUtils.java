package com.example.games.java.ch02;

import static java.lang.Integer.parseInt;

import java.awt.*;

public class DisplayModeUtils {

    public static DisplayMode getDisplayMode(String[] args) {
        final DisplayMode displayMode;
        if (args.length == 3) {
            return new DisplayMode(parseInt(args[0]), parseInt(args[1]), parseInt(args[2]),
                    DisplayMode.REFRESH_RATE_UNKNOWN);
        }

        return new DisplayMode(800, 600, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
    }
}
