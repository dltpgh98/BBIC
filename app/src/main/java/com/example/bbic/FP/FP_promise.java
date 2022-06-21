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
    Bundle bundlelist;


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
            System.out.println("유저코드" + userCode);
            System.out.println("약속 리스트" + promiselist);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Promise_write.class);
                startActivity(intent);
            }
        });

        bundlelist = new Bundle();
        bundlelist.putLong("userCode", userCode);
        bundlelist.putString("promiselist", promiselist);
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