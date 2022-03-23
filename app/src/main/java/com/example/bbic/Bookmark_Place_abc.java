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

import com.example.bbic.Bookmark_Adapter.PlaceAdapter;
import com.example.bbic.Bookmark_Adapter.PlaceData;

import java.util.ArrayList;

public class Bookmark_Place_abc extends Fragment {

    private ArrayList<PlaceData> arrayList;
    private PlaceAdapter placeAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bookmark_place_abc, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.place_abc_rv);      //리스트 객체생성
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);                         //형태를 잡아줌


        placeAdapter = new PlaceAdapter(arrayList);                                 //어뎁터 선언
        arrayList = new ArrayList<>();
        for(int i=1;i<=10;i++){
            if(i%2==0)
                arrayList.add(new PlaceData(R.drawable.setting_menu,i+"번째 사람","인천"+i+"번지"));
            else
                arrayList.add(new PlaceData(R.drawable.setting_menu,i+"번째 사람","부천"+i+"번지"));

        }
        placeAdapter.setArrayList(arrayList);

        recyclerView.setAdapter(placeAdapter);

        TextView textView = (TextView)rootView.findViewById(R.id.place_abc_tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaceData placeData = new PlaceData(R.drawable.setting_menu,"테스트","부천");
                arrayList.add(placeData);
                placeAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }
}
