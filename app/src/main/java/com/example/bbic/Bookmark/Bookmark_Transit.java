package com.example.bbic.Bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bbic.R;

public class Bookmark_Transit extends Fragment {
    Bookmark_Transit_bus bookmark_transit_bus;
    Bookmark_Transit_subway bookmark_transit_subway;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bookmark_transit, container, false);

        bookmark_transit_bus = new Bookmark_Transit_bus();
        bookmark_transit_subway = new Bookmark_Transit_subway();

        String getsubwaylist = null;
        String getbuslist = null;
        long userCode = 0;

        if(getArguments() != null){
            getsubwaylist = getArguments().getString("subwaylist");
            getbuslist = getArguments().getString("buslist");
            userCode  =getArguments().getLong("userCode");
            System.out.println("bookmark_transit_buslist" + getbuslist);
        }

        bundle = new Bundle();
        bundle.putString("subwaylist", getsubwaylist);
        bundle.putString("buslist", getbuslist);
        bundle.putLong("userCode", userCode);
        bookmark_transit_bus.setArguments(bundle);
        bookmark_transit_subway.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.bookmark_container, bookmark_transit_bus).commit();

        Button btn = rootView.findViewById(R.id.bookmark_leftTab_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.bookmark_container, bookmark_transit_bus).commit();
            }
        });

        btn = rootView.findViewById(R.id.bookmark_rightTap_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.bookmark_container, bookmark_transit_subway).commit();
            }
        });

//        btn = rootView.findViewById(R.id.bookmark_rightTap_btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getChildFragmentManager().beginTransaction().replace(R.id.bookmark_container, bookmark_transit_subway).commit();
//            }
//        });


        return rootView;
    }
}
