package com.example.bbic.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bbic.Data.Subway;
import com.example.bbic.R;

import java.util.List;

public class SubwayListAdapter extends BaseAdapter {

    private Context context;
    private List<Subway> subwayList;

    public SubwayListAdapter(Context context, List<Subway> subwayList) {
        this.context = context;
        this.subwayList = subwayList;
    }

    @Override
    public int getCount() {
        return subwayList.size();
    }

    @Override
    public Object getItem(int i) {
        return subwayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.bookmark_transit_subway_list, null);


        TextView subwayName = (TextView) v.findViewById(R.id.subway_this_station_name_tv);
        subwayName.setText(subwayList.get(i).getStationName());



        return v;
    }
}