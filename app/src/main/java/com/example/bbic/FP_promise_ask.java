package com.example.bbic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.Bookmark_Adapter.PlaceData;
import com.example.bbic.Bookmark_Adapter.Promise_AskAdapter;
import com.example.bbic.Bookmark_Adapter.Promise_ListAdapter;

import java.util.ArrayList;

public class FP_promise_ask extends Fragment {

    private ArrayList<PlaceData> arrayList;
    private Promise_AskAdapter askAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_promise_ask, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.promise_ask_rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        askAdapter = new Promise_AskAdapter(arrayList);
        arrayList = new ArrayList<>();

        for(int i = 1; i<=10 ; i++ ) {
            arrayList.add(new PlaceData(R.drawable.left_curve,R.drawable.image_profile,R.drawable.image_profile,R.drawable.image_profile,
                    "타이틀", "주소","22.03.02 AM 09:00",R.drawable.ic_baseline_close,R.drawable.ic_baseline_check));
        }
        askAdapter.setArrayList(arrayList);
        recyclerView.setAdapter(askAdapter);

        return rootView;
    }
}
