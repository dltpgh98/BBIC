package com.example.bbic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FP_promise extends Fragment {
    FP_promise_list fp_promise_list;
    FP_promise_ask fp_promise_ask;
    FloatingActionButton fab;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_promise, container, false);

        fp_promise_list = new FP_promise_list();
        fp_promise_ask = new FP_promise_ask();
        fab = rootView.findViewById(R.id.fab_btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Promise_write.class);
                startActivity(intent);
            }
        });


        getChildFragmentManager().beginTransaction().replace(R.id.tab2_container, fp_promise_list).commit();

        Button btn = rootView.findViewById(R.id.btn2_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.tab2_container, fp_promise_list).commit();
            }
        });

        btn = rootView.findViewById(R.id.btn2_2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.tab2_container, fp_promise_ask).commit();
            }
        });


        return rootView;
    }
}
