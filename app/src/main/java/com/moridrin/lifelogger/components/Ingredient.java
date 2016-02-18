package com.moridrin.lifelogger.components;

/**
 * Created by jeroenhermkens on 17/02/16.
 */
public class Ingredient {
    private Food food;
    private Unit unit;
    private int amount;

    public Ingredient(Food food, Unit unit, int amount) {
        this.food = food;
        this.unit = unit;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return amount + " " + unit + " of " + food.toString();
    }
}
