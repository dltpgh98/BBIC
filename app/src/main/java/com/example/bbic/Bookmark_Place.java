package com.example.bbic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Bookmark_Place extends Fragment {
    Bookmark_Place_new bookmark_place_new;
    Bookmark_Place_abc bookmark_place_abc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bookmark_place, container, false);

        bookmark_place_new = new Bookmark_Place_new();
        bookmark_place_abc = new Bookmark_Place_abc();


        getChildFragmentManager().beginTransaction().replace(R.id.transit_container, bookmark_place_new).commit();

        Button btn = rootView.findViewById(R.id.transit_leftTab_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.transit_container, bookmark_place_new).commit();
            }
        });

        btn = rootView.findViewById(R.id.transit_rightTap_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.transit_container, bookmark_place_abc).commit();
            }
        });


        return rootView;
    }
}
