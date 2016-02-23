package com.moridrin.lifelogger.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jeroenhermkens on 22/02/16.
 */
public class MultiIngredient implements Ingredient {
    List<SingleIngredient> ingredients = new ArrayList<>();

    public MultiIngredient(SingleIngredient... ingredients) {
        this.ingredients = Arrays.asList(ingredients);
    }

    public SingleIngredient get(int id) {
        return ingredients.get(id);
    }

    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++) {
            names.add(ingredients.get(i).getName());
        }
        return names;
    }
}
