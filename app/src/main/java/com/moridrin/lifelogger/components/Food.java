package com.moridrin.lifelogger.components;

import android.media.Image;

import java.util.List;

/**
 * Created by jeroenhermkens on 17/02/16.
 */
public class Food {
    private String name;
    private Image image;

    public Food(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
