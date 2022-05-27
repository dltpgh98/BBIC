package com.example.bbic.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bbic.Data.Friend;
import com.example.bbic.Data.Location;
import com.example.bbic.R;

import java.util.List;

public class LocationListAdapter extends BaseAdapter {

private Context context;
private List<Location> location;

    public LocationListAdapter(Context context, List<Location> location) {
        this.context = context;
        this.location = location;
    }

    @Override
    public int getCount() {
        return location.size();
    }

    @Override
    public Object getItem(int i) {
        return location.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.bookmark_place_list, null);


        TextView locationName = (TextView)v.findViewById(R.id.place_name);
        TextView locationAddress = (TextView) v.findViewById(R.id.place_address);

        locationName.setText(location.get(i).getLocationName());
        locationAddress.setText(location.get(i).getLocationAddress());

        return v;
    }
}
