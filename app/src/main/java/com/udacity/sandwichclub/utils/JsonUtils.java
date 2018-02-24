/*
 * Copyright (c) 2018 Udacity project - Antonio Fiori
 */

package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject nameObject = jsonObject.getJSONObject(NAME);
        // get mainName string
        String mainName = nameObject.optString(MAIN_NAME);
        // get alsoKnownAs array and put elements on a list
        JSONArray alsoKnownAsArray = nameObject.getJSONArray(ALSO_KNOWN_AS);
        List<String> alsoKnowsAs = new ArrayList<>();
        if (alsoKnownAsArray.length() != 0) {
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnowsAs.add(alsoKnownAsArray.optString(i));
            }
        }
        // get placeOfOrigin, description and image strings
        String placeOfOrigin = jsonObject.optString(PLACE_OF_ORIGIN);
        String description = jsonObject.optString(DESCRIPTION);
        String image = jsonObject.optString(IMAGE);
        // get ingredients array and put elements on a list
        JSONArray ingredientsArray = jsonObject.getJSONArray(INGREDIENTS);
        List<String> ingredients = new ArrayList<>();
        if (ingredientsArray.length() != 0) {
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.optString(i));
            }
        }
        return new Sandwich(mainName, alsoKnowsAs, placeOfOrigin, description, image, ingredients);
    }
}