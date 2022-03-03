package com.example.bbic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.Bookmark_Adapter.PlaceData;
import com.example.bbic.Bookmark_Adapter.Transit_BusAdapter;

import java.util.ArrayList;

public class Bookmark_Transit_bus extends Fragment {

    private ArrayList<PlaceData> arrayList;
    private Transit_BusAdapter busAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bookmark_transit_bus, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.transit_bus_rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        busAdapter = new Transit_BusAdapter(arrayList);
        arrayList = new ArrayList<>();
        for (int i =1;i<=10;i++){
            if(i%2==0)
                arrayList.add(new PlaceData(R.drawable.ic_baseline_star_24,R.drawable.ic_baseline_directions_bus_24,
                        i+"정류장",i+"동 방면",
                        (i+i)+"분"+i+"초",(i+i+3)+"분"+i+"초",(i*i)-i+"번",
                        (i+i-1)+"분"+(i+2)+"초",(i+i+1)+"분"+i+"초",(i*i)-(i-2)+"번",
                        (i+i+3)+"분"+(i+1)+"초",(i+i+4)+"분"+(i+1)+"초",(i*i)-(i-3)+"번"));
        }

        busAdapter.setArrayList(arrayList);

        recyclerView.setAdapter(busAdapter);

        TextView textView = (TextView) rootView.findViewById(R.id.place_new_tv);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaceData placeData = new PlaceData(R.drawable.ic_baseline_star_24,R.drawable.ic_baseline_directions_bus_24,
                        "정류장","테스트 동","00분00초","00분00초","01번","00분00초","00분00초","01번","00분00초","00분00초","01번");
            }
        });

        return rootView;
    }
}
