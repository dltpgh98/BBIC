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

import com.example.bbic.Bookmark_Adapter.Friend_ListAdapter;
import com.example.bbic.Bookmark_Adapter.PlaceData;

import java.util.ArrayList;

public class FP_friend_list extends Fragment {

    private ArrayList<PlaceData> arrayList;
    private Friend_ListAdapter listAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_friend_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.friend_list_rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        listAdapter = new Friend_ListAdapter(arrayList);
        arrayList = new ArrayList<>();

        for(int i = 1; i<=10 ; i++ ){
            if(i%2==0)
                arrayList.add(new PlaceData(R.drawable.image_profile,R.color.green,R.drawable.ic_baseline_menu,i+"번째 테스트"));
        }
        listAdapter.setArrayList(arrayList);
        recyclerView.setAdapter(listAdapter);

        return rootView;
    }
}
