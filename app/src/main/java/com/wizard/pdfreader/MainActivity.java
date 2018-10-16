package com.wizard.pdfreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.wizard.adapter.BookListAdapter;

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

        BookListAdapter adapter = new BookListAdapter( this);

        listview.setAdapter(adapter);
    }
}
