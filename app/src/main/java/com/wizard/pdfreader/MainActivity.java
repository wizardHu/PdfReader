package com.wizard.pdfreader;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private String[] data = { "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Mango", "Mango", "Mango", "Mango", "Mango" };

    @ViewById
    ListView listview;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("wizard","test");





    }

    @AfterViews
    void afterViews() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( MainActivity.this, android.R.layout.simple_list_item_1, data);
        listview.setAdapter(adapter);
    }
}
