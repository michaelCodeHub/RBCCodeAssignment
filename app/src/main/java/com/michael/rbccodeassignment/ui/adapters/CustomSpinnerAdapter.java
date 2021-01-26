package com.michael.rbccodeassignment.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.michael.rbccodeassignment.R;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private final List<String> items;
    private final Context context;

    public CustomSpinnerAdapter(Context context, List<String> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.items = items;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);

        if (textView == null) {
            textView = new TextView(context);
        }
        textView.setTextColor(Color.BLACK);
        textView.setText(items.get(position));
        return textView;
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.spinner_item, null);
        }
        TextView lbl = convertView.findViewById(R.id.text1);
        lbl.setTextColor(Color.BLACK);
        lbl.setText(items.get(position));
        return convertView;
    }
}
