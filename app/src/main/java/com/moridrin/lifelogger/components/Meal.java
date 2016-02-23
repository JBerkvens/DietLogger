package com.moridrin.lifelogger.components;

import android.media.Image;

import java.util.List;

/**
 * Created by jeroenhermkens on 17/02/16.
 */
public class Meal {
    private static List<Meal> meals;
    private String name;
    private String recipe;
    private List<Image> images;
    private List<SingleIngredient> singleIngredients;

    public Meal(String name, List<SingleIngredient> singleIngredients) {
        this.name = name;
        this.singleIngredients = singleIngredients;
        meals.add(this);
    }

    public static Meal Get(String name) {
        for (Meal meal : meals) {
            if (meal.name == name) {
                return meal;
            }
        }
        return null;
    }

    public void addImage(Image image) {
        if (!images.contains(image)) {
            this.images.add(image);
        }
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return name;
    }
}
