package com.moridrin.lifelogger;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.moridrin.lifelogger.components.Ingredient;
import com.moridrin.lifelogger.components.MultiIngredient;
import com.moridrin.lifelogger.components.SingleIngredient;
import com.moridrin.lifelogger.components.Unit;
import com.moridrin.lifelogger.utilities.SaveFoodLog;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MealActivity extends AppCompatActivity {

    ArrayList<Ingredient> ingredients;
    LinearLayout container;
    private String meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        meal = getIntent().getExtras().getString("Meal");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        container = (LinearLayout) findViewById(R.id.container);
        ingredients = getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            final Ingredient ingredient = ingredients.get(i);
            final AutoCompleteTextView nameEditText = new AutoCompleteTextView(this);
            final NumberPicker amountNumberPicker = new NumberPicker(this);
            CardView ingredientCard = new CardView(this);
            ingredientCard.setContentPadding(16, 16, 16, 16);
            ingredientCard.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ingredientCard.setUseCompatPadding(true);
            ingredientCard.setCardElevation(6);
            RelativeLayout cardContent = new RelativeLayout(this);
            cardContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            RelativeLayout ingredientLayout = new RelativeLayout(this);
            ingredientLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            SingleIngredient singleIngredient = null;
            if (ingredient instanceof MultiIngredient) {
                MultiIngredient multiIngredient = (MultiIngredient) ingredient;
                Spinner ingredientCategorySpinner = new Spinner(this);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, multiIngredient.getNames());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ingredientCategorySpinner.setAdapter(adapter);
                ingredientCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        MultiIngredient multiIngredient = (MultiIngredient) ingredient;
                        SingleIngredient singleIngredient = multiIngredient.get(position);
                        amountNumberPicker.setMinValue(singleIngredient.getAmount() / 2);
                        amountNumberPicker.setMaxValue((int) (singleIngredient.getAmount() * 1.5));
                        amountNumberPicker.setValue(singleIngredient.getAmount());
                        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_list_item_1, singleIngredient.getAutoComplete());
                        nameEditText.setAdapter(autoCompleteAdapter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                ingredientLayout.addView(ingredientCategorySpinner);
                singleIngredient = multiIngredient.get(0);
            } else {
                singleIngredient = (SingleIngredient) ingredient;
                TextView ingredientCategoryTextView = new TextView(this);
                ingredientCategoryTextView.setTextAppearance(android.R.style.TextAppearance_Large);
                ingredientCategoryTextView.setText(singleIngredient.getCategory());
                ingredientLayout.addView(ingredientCategoryTextView);
            }
            TableLayout inputLayout = new TableLayout(this);
            inputLayout.setLayoutParams(new ViewGroup.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            inputLayout.setColumnStretchable(0, true);
            TableRow tableRow = new TableRow(this);
            TableRow.LayoutParams nameEditTextParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            nameEditTextParams.gravity = Gravity.CENTER_VERTICAL;
            ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, singleIngredient.getAutoComplete());
            nameEditText.setAdapter(autoCompleteAdapter);
            nameEditText.setLayoutParams(nameEditTextParams);
            nameEditText.setForegroundGravity(Gravity.CENTER_VERTICAL);
            tableRow.addView(nameEditText);
            amountNumberPicker.setMinValue(singleIngredient.getAmount() / 2);
            amountNumberPicker.setMaxValue((int) (singleIngredient.getAmount() * 1.5));
            amountNumberPicker.setValue(singleIngredient.getAmount());
            tableRow.addView(amountNumberPicker);
            TextView unitTextView = new TextView(this);
            unitTextView.setText(singleIngredient.getUnit().toString());
            TableRow.LayoutParams unitTextViewParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            unitTextViewParams.gravity = Gravity.CENTER_VERTICAL;
            unitTextView.setLayoutParams(unitTextViewParams);
            tableRow.addView(unitTextView);
            inputLayout.addView(tableRow);
            ingredientLayout.addView(inputLayout);
            cardContent.addView(ingredientLayout);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, inputLayout.getId());
            ingredientCard.addView(cardContent);
            container.addView(ingredientCard);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = true;
                try {
                    for (int i = 0; i < container.getChildCount(); i++) {
                        if (success) {
                            String category;
                            String name;
                            String amount;
                            String unit;
                            CardView ingredientCard = (CardView) container.getChildAt(i);
                            RelativeLayout cardContent = (RelativeLayout) ingredientCard.getChildAt(0);
                            RelativeLayout ingredientLayout = (RelativeLayout) cardContent.getChildAt(0);
                            View categoryView = ingredientLayout.getChildAt(0);
                            if (categoryView instanceof TextView) {
                                TextView categoryTextView = (TextView) categoryView;
                                category = categoryTextView.getText().toString();
                            } else {
                                Spinner categorySpinner = (Spinner) categoryView;
                                category = categorySpinner.getSelectedItem().toString();
                            }
                            TableLayout inputLayout = (TableLayout) ingredientLayout.getChildAt(1);
                            TableRow tableRow = (TableRow) inputLayout.getChildAt(0);
                            EditText nameEditText = (EditText) tableRow.getChildAt(0);
                            name = nameEditText.getText().toString();
                            NumberPicker amountNumberPicker = (NumberPicker) tableRow.getChildAt(1);
                            amount = "" + amountNumberPicker.getValue();
                            TextView unitTextView = (TextView) tableRow.getChildAt(2);
                            unit = unitTextView.getText().toString();
                            success = new SaveFoodLog().execute(category, name, amount, unit).get();
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    success = false;
                }
                String message;
                if (success) {
                    message = "Everything logged successfully.";
                } else {
                    message = "NOT logged!";
                }
                Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    private ArrayList<Ingredient> getIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        switch (meal) {
            case "Bread & Yogurt":
                ingredients.add(new SingleIngredient(Unit.pc, 2, "Bread"));
                ingredients.add(new SingleIngredient(Unit.gr, 10, "Butter"));
                ingredients.add(new SingleIngredient(Unit.gr, 20, "Spread"));
                ingredients.add(new SingleIngredient(Unit.ml, 200, "Drinks"));
                ingredients.add(new SingleIngredient(Unit.ml, 150, "Yogurt"));
                break;
            case "Yogurt, Fruit & Nuts":
                ingredients.add(new SingleIngredient(Unit.ml, 200, "Yogurt"));
                ingredients.add(new SingleIngredient(Unit.gr, 125, true, "Fruit"));
                ingredients.add(new SingleIngredient(Unit.gr, 25, true, "Nuts"));
                break;
            case "Smoothie & Yogurt":
                ingredients.add(new SingleIngredient(Unit.gr, 400, true, "Smoothy"));
                ingredients.add(new MultiIngredient(new SingleIngredient(Unit.ml, 150, "Yogurt"), new SingleIngredient(Unit.pc, 1, "Boiled Egg")));
                break;
        }
        return ingredients;
    }
}
