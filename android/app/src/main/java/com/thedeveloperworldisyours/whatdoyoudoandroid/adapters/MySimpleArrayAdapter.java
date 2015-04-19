package com.thedeveloperworldisyours.whatdoyoudoandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thedeveloperworldisyours.whatdoyoudoandroid.R;
import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Mission;

import java.util.ArrayList;

/**
 * Created by javiergonzalezcabezas on 19/4/15.
 */
public class MySimpleArrayAdapter extends ArrayAdapter<Mission> {
    private final Activity mContext;
    private final ArrayList<Mission> mValues;

    public MySimpleArrayAdapter(Activity context, ArrayList<Mission> values) {
        super(context, R.layout.row_mission, values);
        this.mContext = context;
        this.mValues = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_mission, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.row_mission_firstLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(mValues.get(position).getName());
        rowView.setTag(mValues.get(position).getId());
        return rowView;
    }

}
