package com.example.bbic.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bbic.Data.Bus;
import com.example.bbic.Data.Subway;
import com.example.bbic.R;

import java.util.List;

public class BusListAdapter extends BaseAdapter {

    private Context context;
    private List<Bus> busList;

    public BusListAdapter(Context context, List<Bus> busList) {
        this.context = context;
        this.busList = busList;
    }

    @Override
    public int getCount() {
        return busList.size();
    }

    @Override
    public Object getItem(int i) {
        return busList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.bookmark_transit_bus_list, null);


        TextView busStationName = (TextView) v.findViewById(R.id.bus_station_name_tv);
        TextView busStationDirection = (TextView) v.findViewById(R.id.bus_to_station_name_tv);
        TextView busNum = (TextView) v.findViewById(R.id.bus1_num_tv);

        busStationName.setText(String.valueOf(busList.get(i).getBusStationName()));
        busStationDirection.setText(busList.get(i).getBusDirction());
        busNum.setText(String.valueOf(busList.get(i).getBusNum()));

        return v;
    }
}
