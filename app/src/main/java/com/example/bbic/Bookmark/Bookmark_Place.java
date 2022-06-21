package com.example.bbic.Bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bbic.R;

public class Bookmark_Place extends Fragment {
    Bookmark_Place_new bookmark_place_new;
    Bookmark_Place_abc bookmark_place_abc;
    Bundle bundle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bookmark_place, container, false);

        bookmark_place_new = new Bookmark_Place_new();
        bookmark_place_abc = new Bookmark_Place_abc();

        String locationposlist = "";
        long userCode = 0;

        if(getArguments() != null){
            locationposlist = getArguments().getString("locationposlist");
            userCode = getArguments().getLong("userCode");
            System.out.println("받은 locationposlist 확인 : " + locationposlist);
            System.out.println("받은 유저 코드" + userCode);
        }
        bundle = new Bundle();
        bundle.putString("locationposlist", locationposlist);
        bundle.putLong("userCode", userCode);
        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
        bookmark_place_abc.setArguments(bundle);
        bookmark_place_new.setArguments(bundle);
        transaction.replace(R.id.bookmark_container, bookmark_place_abc);
        transaction.commit();


        getChildFragmentManager().beginTransaction().replace(R.id.bookmark_container, bookmark_place_new).commit();

        Button btn = rootView.findViewById(R.id.bookmark_leftTab_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.bookmark_container, bookmark_place_new).commit();
            }
        });

        btn = rootView.findViewById(R.id.bookmark_rightTap_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.bookmark_container, bookmark_place_abc).commit();
            }
        });


        return rootView;
    }
}