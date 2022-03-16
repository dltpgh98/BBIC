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

import com.example.bbic.Bookmark_Adapter.Friend_AskAdapter;
import com.example.bbic.Bookmark_Adapter.PlaceData;

import java.util.ArrayList;

public class FP_friend_ask extends Fragment {
    private ArrayList<PlaceData> arrayList;
    private Friend_AskAdapter askAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
//    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_friend_ask, container, false);

//        imageView = rootView.findViewById(R.id.ask_profile_iv);
//        imageView.setClipToOutline(true);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.friend_ask_rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        askAdapter = new Friend_AskAdapter(arrayList);
        arrayList = new ArrayList<>();

        for(int i = 1; i<=10 ; i++ ) {
            if (i % 2 == 0)
                arrayList.add(new PlaceData(R.drawable.image_profile,R.color.transparent,R.color.red,R.color.green,i+"번째 테스트"));
        }
        askAdapter.setArrayList(arrayList);
        recyclerView.setAdapter(askAdapter);

        return rootView;
    }
}
