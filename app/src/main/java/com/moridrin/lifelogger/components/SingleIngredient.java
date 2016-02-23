package com.moridrin.lifelogger.components;

import com.moridrin.lifelogger.utilities.GetAutocomplete;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by jeroenhermkens on 17/02/16.
 */
public class SingleIngredient implements Ingredient {
    private String category;
    private String name;
    private Unit unit;
    private int amount;
    private boolean multipleInstances = false;

    public SingleIngredient(Unit unit, int amount, String category) {
        this.category = category;
        this.unit = unit;
        this.amount = amount;
    }

    public SingleIngredient(Unit unit, int amount, boolean multipleInstances, String category) {
        this.category = category;
        this.unit = unit;
        this.amount = amount;
        this.multipleInstances = multipleInstances;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public List<String> getAutoComplete() {
        List<String> autoCompleteList = null;
        try {
            autoCompleteList = new GetAutocomplete().execute(category).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (autoCompleteList == null) {
            autoCompleteList = new ArrayList<>();
        }
        return autoCompleteList;
    }

    @Override
    public String toString() {
        return amount + " " + unit + " of " + category;
    }

    public Unit getUnit() {
        return unit;
    }
}
