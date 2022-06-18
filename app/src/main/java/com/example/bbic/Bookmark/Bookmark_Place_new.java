package com.example.bbic.Bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.Adapter.LocationListAdapter;
import com.example.bbic.Adapter.PlaceAdapter;
import com.example.bbic.Adapter.PlaceData;
import com.example.bbic.Data.Location;
import com.example.bbic.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Bookmark_Place_new extends Fragment {

    private ListView listView;
    private LocationListAdapter adapter;
    private List<Location> locationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bookmark_place_new, container, false);

        String locationposlist = null;
        long getuserCode = 0;

        listView = (ListView) rootView.findViewById(R.id.place_new_rv);
        locationList = new ArrayList<Location>();
        adapter = new LocationListAdapter(getContext(), locationList);
        listView.setAdapter(adapter);

        if(getArguments() != null){
            locationposlist = getArguments().getString("locationposlist");
            getuserCode = getArguments().getLong("userCode");
            System.out.println("locationposlist 장소 목록 확인 : " + locationposlist);
            System.out.println("getuserCode 확인 : " + getuserCode);
        }

        try{
            JSONObject jsonObject = new JSONObject(locationposlist);
            System.out.println(locationposlist);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            String locationName;
            long userCode;
            String locationAddress;
            double locationLong;
            double locationLat;

            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                locationName = object.getString("L_name");
                userCode = object.getLong("K_code");
                locationAddress = object.getString("L_address");
                locationLong = object.getDouble("L_long");
                locationLat = object.getDouble("L_lat");

                Location location = new Location(locationName,userCode,locationAddress,locationLong,locationLat);
                if(getuserCode == userCode){
                    locationList.add(location);
                }
                count++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return rootView;
    }
}
