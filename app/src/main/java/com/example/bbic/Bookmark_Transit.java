package com.example.bbic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Bookmark_Transit extends Fragment {
    Bookmark_Transit_bus bookmark_transit_bus;
    Bookmark_Transit_subway bookmark_transit_subway;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bookmark_transit, container, false);

        bookmark_transit_bus = new Bookmark_Transit_bus();
        bookmark_transit_subway = new Bookmark_Transit_subway();


        getChildFragmentManager().beginTransaction().replace(R.id.tab2_container, bookmark_transit_bus).commit();

        Button btn = rootView.findViewById(R.id.btn2_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.tab2_container, bookmark_transit_bus).commit();
            }
        });

        btn = rootView.findViewById(R.id.btn2_2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.tab2_container, bookmark_transit_subway).commit();
            }
        });


        return rootView;
    }
}
