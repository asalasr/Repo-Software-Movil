package com.example.vinilos.ui.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vinilos.R;
import com.example.vinilos.models.Album;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class AlbumAdapterTest extends ArrayAdapter<Album> {


    public AlbumAdapterTest( Context context, int resource, @NonNull List<Album> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Album user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.album_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        //TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        // Populate the data into the template view using the data object
        tvName.setText(user.getName());
        //tvHome.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;
    }

}
