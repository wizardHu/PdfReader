package com.wizard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wizard.components.MarqueTextView;
import com.wizard.pdfreader.R;

/**
 * Created by Amplee on 2018/10/8.
 */
public class BookListAdapter extends BaseAdapter {

    LayoutInflater inflater = null;

    public BookListAdapter( Context context) {
        inflater = LayoutInflater.from(context);
    }




    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.main_book_item, parent, false); //加载布局

        MarqueTextView tv = convertView.findViewById(R.id.marqueTextView);
        tv.setSelected(true);

        return convertView;
    }
}
