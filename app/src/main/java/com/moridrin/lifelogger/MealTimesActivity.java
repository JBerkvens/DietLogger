package com.moridrin.lifelogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MealTimesActivity extends AppCompatActivity {

    private ListView mealTimesListView;
    private ArrayList<String> mealTimesList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_times);

        mealTimesListView = (ListView)findViewById(R.id.mealTimesListView);
        mealTimesList = new ArrayList<>();
        mealTimesList.add("Breakfast");
        mealTimesList.add("Second Breakfast");
        mealTimesList.add("Elevenses");
        mealTimesList.add("Luncheon");
        mealTimesList.add("Afternoon Tea");
        mealTimesList.add("High Tea");
        mealTimesList.add("Dinner");
        mealTimesList.add("Supper");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mealTimesList);
        mealTimesListView.setAdapter(arrayAdapter);

        mealTimesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MealTimesActivity.this, MealsActivity.class);
                intent.putExtra("MealTime", mealTimesList.get(position));
                startActivity(intent);
            }
        });
    }
}
