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

import com.example.bbic.Adapter.PlaceData;
import com.example.bbic.Adapter.SubwayListAdapter;
import com.example.bbic.Adapter.Transit_SubwayAdapter;
import com.example.bbic.Data.Subway;
import com.example.bbic.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Bookmark_Transit_subway extends Fragment {

    private ListView listView;
    private SubwayListAdapter adapter;
    private List<Subway> subwayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bookmark_transit_subway, container, false);

        String getsubwaylist = null;
        long userKakaoCode = 0;

        if (getArguments() != null) {
            getsubwaylist = getArguments().getString("subwaylist");
            userKakaoCode = getArguments().getLong("userCode");
        }

        listView = (ListView) rootView.findViewById(R.id.transit_subway_rv);//
        subwayList = new ArrayList<Subway>();
        adapter = new SubwayListAdapter(getContext(), subwayList);
        listView.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject(getsubwaylist);
            System.out.println(getsubwaylist);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            int stationCode;
            long userCode;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                stationCode = object.getInt("S_code");
                userCode = object.getLong("K_code");

                Subway subway = new Subway(stationCode, userCode);
                if (userKakaoCode == userCode) {
                    subwayList.add(subway);
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }
}
