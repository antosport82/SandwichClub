/*
 * Copyright (c) 2018 Udacity project - Antonio Fiori
 */

package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv) ImageView ingredientsIv;
    @BindView(R.id.also_known_tv) TextView mAlsoKnownAs;
    @BindView(R.id.ingredients_tv) TextView mIngredients;
    @BindView(R.id.origin_tv) TextView mOrigin;
    @BindView(R.id.description_tv) TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Toast.makeText(DetailActivity.this, "EXTRA_POSITION not found in intent", Toast.LENGTH_SHORT).show();
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Populates each textview with the content of sandwich object
     */
    private void populateUI(Sandwich sandwich) {
        // build 'also known as' string
        StringBuilder alsoKnownAsSb = new StringBuilder();
        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
            if (sandwich.getAlsoKnownAs().size() - i > 1) {
                // add a comma between multi elements
                alsoKnownAsSb.append(sandwich.getAlsoKnownAs().get(i));
                alsoKnownAsSb.append(", ");
            } else {
                // not adding the comma after the last one
                alsoKnownAsSb.append(sandwich.getAlsoKnownAs().get(i));
            }
        }
        mAlsoKnownAs.setText(alsoKnownAsSb);
        mDescription.setText(sandwich.getDescription());
        mOrigin.setText(sandwich.getPlaceOfOrigin());
        // build ingredients' string
        StringBuilder ingredientsSb = new StringBuilder();
        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            if (sandwich.getIngredients().size() - i > 1) {
                // add a comma between multi elements
                ingredientsSb.append(sandwich.getIngredients().get(i));
                ingredientsSb.append(", ");
            } else {
                // not adding the comma after the last one
                ingredientsSb.append(sandwich.getIngredients().get(i));
            }
        }
        mIngredients.setText(ingredientsSb);
    }
}