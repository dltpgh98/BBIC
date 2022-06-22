package com.example.bbic.FP;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bbic.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FP_promise extends Fragment {
    FP_promise_list fp_promise_list;
    FP_promise_ask fp_promise_ask;
    FloatingActionButton fab;
    long userCode = 0;
    String promiselist = null;
    String friendlist = null;
    Bundle bundlelist;
    private String weather, tem, fineDust, ultraFineDust, covidNum, name, address, area, city, locationlist, buslist, subwaylist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_promise, container, false);

        fp_promise_list = new FP_promise_list();
        fp_promise_ask = new FP_promise_ask();
        fab = rootView.findViewById(R.id.fab_btn);


        if(getArguments() != null){
            userCode = getArguments().getLong("userCode");
            promiselist = getArguments().getString("promiselist");
            friendlist = getArguments().getString("friendlist");
            area = getArguments().getString("도");
            city = getArguments().getString("시");
            weather = getArguments().getString("날씨");
            tem = getArguments().getString("온도");
            fineDust = getArguments().getString("미세먼지");
            covidNum = getArguments().getString("코로나");
            locationlist = getArguments().getString("locationlist");
            buslist = getArguments().getString("buslist");
            subwaylist = getArguments().getString("subwaylist");
            ultraFineDust = getArguments().getString("초미세먼지");
            name = getArguments().getString("닉네임");
            address = getArguments().getString("프로필");
            System.out.println("유저코드" + userCode);
            System.out.println("약속 리스트" + promiselist);
            System.out.println("약속에서 친구 목록 확인" + friendlist);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Promise_write.class);
                intent.putExtra("friendlist", friendlist);
                intent.putExtra("userCode", userCode);
                intent.putExtra("promiselist", promiselist);
                intent.putExtra("locationlist", locationlist);
                intent.putExtra("subwaylist", subwaylist);
                intent.putExtra("buslist", buslist);
                intent.putExtra("도", area);
                intent.putExtra("시", city);
                intent.putExtra("날씨", weather);
                intent.putExtra("온도", tem);
                intent.putExtra("코로나", covidNum);
                intent.putExtra("미세먼지", fineDust);
                intent.putExtra("초미세먼지", ultraFineDust);
                intent.putExtra("닉네임", name);
                intent.putExtra("프로필", address);
                startActivity(intent);
            }
        });

        bundlelist = new Bundle();
        bundlelist.putLong("userCode", userCode);
        bundlelist.putString("promiselist", promiselist);
        bundlelist.putString("friendlist", friendlist);

        fp_promise_list.setArguments(bundlelist);
        fp_promise_ask.setArguments(bundlelist);
        getChildFragmentManager().beginTransaction().replace(R.id.promise_container, fp_promise_list).commit();

        Button btn = rootView.findViewById(R.id.p_leftTab_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.promise_container, fp_promise_list).commit();
            }
        });

        btn = rootView.findViewById(R.id.p_rightTap_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.promise_container, fp_promise_ask).commit();
            }
        });


        return rootView;
    }
}