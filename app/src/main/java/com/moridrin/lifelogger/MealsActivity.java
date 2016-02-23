package com.moridrin.lifelogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.moridrin.lifelogger.utilities.ListUtilities;

import java.util.ArrayList;

public class MealsActivity extends AppCompatActivity {

    private String mealTime;

    private ListView mainMealsListView;
    private ArrayList<String> mainMeals;
    private ArrayAdapter<String> mainArrayAdapter;
    private ListView otherMealsListView;
    private ArrayList<String> otherMeals;
    private ArrayAdapter<String> otherArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        mealTime = getIntent().getExtras().getString("MealTime");
        mainMealsListView = (ListView) findViewById(R.id.listViewMainMeals);
        mainMeals = getMainMeals();
        mainArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mainMeals);
        mainMealsListView.setAdapter(mainArrayAdapter);
        mainMealsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MealsActivity.this, MealActivity.class);
                intent.putExtra("Meal", mainMeals.get(position));
                startActivity(intent);
            }
        });

        otherMealsListView = (ListView) findViewById(R.id.listViewOtherMeals);
        otherMeals = getOtherMeals();
        otherArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, otherMeals);
        otherMealsListView.setAdapter(otherArrayAdapter);
        otherMealsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MealsActivity.this, MealActivity.class);
                intent.putExtra("Meal", otherMeals.get(position));
                startActivity(intent);
            }
        });

        ListUtilities.setDynamicHeight(mainMealsListView);
        ListUtilities.setDynamicHeight(otherMealsListView);
    }

    private ArrayList<String> getMainMeals() {
        ArrayList<String> meals = new ArrayList<>();
        switch (mealTime) {
            case "Breakfast":
                meals.add("Bread & Yogurt");
                meals.add("Yogurt, Fruit & Nuts");
                meals.add("Smoothie & Yogurt");
                break;
            case "Second Breakfast":
                break;
            case "Elevenses":
                meals.add("Fruit");
                meals.add("Nuts");
                break;
            case "Luncheon":
                meals.add("Soup, Bread & Yogurt");
                break;
            case "Afternoon Tea":
                meals.add("Fruit");
                break;
            case "High Tea":
                meals.add("Vegetable Soup");
                break;
            case "Dinner":
                meals.add("Warm Meal");
                break;
            case "Supper":
                meals.add("Fruit");
                meals.add("Dried Fruit");
                meals.add("Banana Pancake");
                meals.add("Nuts");
                meals.add("Cracker");
                meals.add("Gingerbread");
                break;
        }
        if (meals.size() == 0) {
            findViewById(R.id.textViewMain).setVisibility(View.INVISIBLE);
        }
        return meals;
    }

    private ArrayList<String> getOtherMeals() {
        ArrayList<String> meals = new ArrayList<>();
        switch (mealTime) {
            case "Breakfast":
                meals.add("Yogurt, Fruit & Muesli");
                meals.add("Porridge");
                meals.add("Banana Pancake");
                meals.add("Omelet");
                break;
            case "Second Breakfast":
                break;
            case "Elevenses":
                break;
            case "Luncheon":
                meals.add("Warm Meal");
                meals.add("Salad");
                meals.add("Bread & Yogurt");
                meals.add("Yogurt, Fruit & Nuts");
                meals.add("Smoothie & Yogurt");
                meals.add("Yogurt, Fruit & Muesli");
                meals.add("Porridge");
                meals.add("Banana Pancake");
                meals.add("Omelet");
                break;
            case "Afternoon Tea":
                break;
            case "High Tea":
                break;
            case "Dinner":
                meals.add("Soup, Sandwich, Egg, Yogurt & Fruit");
                meals.add("Soup, Bread, Omelet, Yogurt & Fruit");
                meals.add("Salad, Bread, Yogurt & Fruit");
                break;
            case "Supper":
                meals.add("Cucumber");
                meals.add("Paprika / Arugula");
                meals.add("Nuts");
                meals.add("Fruit");
                meals.add("Dried Fruit");
                meals.add("Wine");
                meals.add("Chocolate");
                meals.add("Coconut");
                break;
        }
        if (meals.size() == 0) {
            findViewById(R.id.textViewOther).setVisibility(View.INVISIBLE);
        }
        return meals;
    }
}
