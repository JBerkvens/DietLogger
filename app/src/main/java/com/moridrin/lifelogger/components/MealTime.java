package com.moridrin.lifelogger.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeroenhermkens on 17/02/16.
 */
public enum MealTime {
    Breakfast,
    Second_Breakfast,
    Elevenses,
    Luncheon,
    Afternoon_Tea,
    Dinner,
    Supper;

    public List<Meal> getMeals() {
        List<Meal> meals = new ArrayList<>();
        switch (this) {
            case Breakfast:
                meals.add(Meal.Get("Spaghetti"));
        }
        return meals;
    }
    
    @Override
    public String toString() {
        return this.name().replace("_", " ");
    }
}
