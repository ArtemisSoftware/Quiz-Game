package com.titan.quizgame.util;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.titan.quizgame.quiz.models.Category;

import java.util.List;

public class Loader {

    public static ArrayAdapter<Category> loadCategories(Context context, List<Category> categories) {

        ArrayAdapter<Category> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public static ArrayAdapter<String> loadStringArray(Context context, String[] items) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
