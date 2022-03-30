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

public class Bookmark_Place_new extends Fragment {
    private ArrayList<PlaceData> arrayList;
    private LinearLayoutManager linearLayoutManager;
    private PlaceAdapter placeAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bookmark_place_new, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.place_new_rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        placeAdapter = new PlaceAdapter(arrayList);

        recyclerView.setAdapter(placeAdapter);

        TextView textView = (TextView) rootView.findViewById(R.id.place_new_tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaceData placeData = new PlaceData(R.drawable.setting_menu_f,"장소","부천 부계동");
                arrayList.add(placeData);
                placeAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }
}
