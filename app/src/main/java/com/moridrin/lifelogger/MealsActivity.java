package com.moridrin.lifelogger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MealsActivity extends AppCompatActivity {

    private ListView mealsListView;
    private ArrayList<String> mealsList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        mealsListView = (ListView)findViewById(R.id.mealsListView);
        mealsList = new ArrayList<>();
        mealsList.add("Bread");
        mealsList.add("Smoothie");
        mealsList.add("Fruit");
        mealsList.add("Yogurt");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mealsList);
        mealsListView.setAdapter(arrayAdapter);

        mealsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MealsActivity.this, MealActivity.class);
                intent.putExtra("MealTime", mealsList.get(position));
                startActivity(intent);
            }
        });
    }
}
