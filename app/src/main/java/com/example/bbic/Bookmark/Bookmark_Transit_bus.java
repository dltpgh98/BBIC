package com.example.bbic.Bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.Adapter.BusListAdapter;
import com.example.bbic.Adapter.PlaceData;
import com.example.bbic.Adapter.SubwayListAdapter;
import com.example.bbic.Adapter.Transit_BusAdapter;
import com.example.bbic.Data.Bus;
import com.example.bbic.Data.Subway;
import com.example.bbic.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Bookmark_Transit_bus extends Fragment {
    private ListView listView;
    private BusListAdapter adapter;
    private List<Bus> busList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bookmark_transit_bus, container, false);

        String getbuslist = null;
        long userKakaoCode = 0;

        if (getArguments() != null) {
            getbuslist = getArguments().getString("buslist");
            userKakaoCode = getArguments().getLong("userCode");
        }
        listView = (ListView) rootView.findViewById(R.id.transit_bus_rv);//
        busList = new ArrayList<Bus>();
        adapter = new BusListAdapter(getContext(), busList);
        listView.setAdapter(adapter);


        try {
            JSONObject jsonObject = new JSONObject(getbuslist);
            System.out.println(getbuslist);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            int buskey;
            int stationCode;
            long userCode;
            String busNum;
            String busStationName;
            String busDirction;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                buskey = object.getInt("B.B_buskey");
                stationCode = object.getInt("B.B_stationcode");
                userCode = object.getLong("B.K_code");
                busNum = object.getString("B.B_num");
                busStationName = object.getString("BB.B_stationname");
                busDirction = object.getString("BB.B_direction");

                Bus bus = new Bus(buskey, stationCode, userCode, busNum, busStationName, busDirction);
                if (userKakaoCode == userCode) {
                    busList.add(bus);
                }
                count++;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



        return rootView;
    }
}