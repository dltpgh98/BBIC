package com.example.bbic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FP_friend extends Fragment {
    FP_friend_list fp_friend_list;
    FP_friend_ask fp_friend_ask;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_friend, container, false);

        fp_friend_list = new FP_friend_list();
        fp_friend_ask = new FP_friend_ask();


        getChildFragmentManager().beginTransaction().replace(R.id.tab1_container, fp_friend_list).commit();

        Button btn = rootView.findViewById(R.id.btn1_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.tab1_container, fp_friend_list).commit();
            }
        });

        btn = rootView.findViewById(R.id.btn1_2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.tab1_container, fp_friend_ask).commit();
            }
        });


        return rootView;
    }
}
