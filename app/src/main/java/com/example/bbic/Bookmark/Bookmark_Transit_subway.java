package com.example.bbic.Bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.Adapter.PlaceData;
import com.example.bbic.Adapter.Transit_SubwayAdapter;
import com.example.bbic.R;

import java.util.ArrayList;

public class Bookmark_Transit_subway extends Fragment {

    private ArrayList<PlaceData> arrayList;
    private Transit_SubwayAdapter subwayAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.bookmark_transit_subway, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.transit_subway_rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        subwayAdapter =new Transit_SubwayAdapter(arrayList);
        arrayList = new ArrayList<>();
        for (int i =1;i<=10;i++){
            if(i%2==0)
                arrayList.add(new PlaceData(R.drawable.ic_baseline_star_24,
                        i+"번 역",(i-1)+"번 방면",(i+1)+"번 방면",
                        (i-1)+"번 역",(i+i+3)+"분"+i+"초",(i*i)-i+"전역",
                        (i-1)+"번 역",(i+i+1)+"분"+i+"초",(i*i)-(i-2)+"전역",
                        (i+i+3)+"번 역",(i+i+4)+"분"+(i+1)+"초",(i*i)-(i-3)+"전역" ,
                        (i+i)+"번 역",(i+i+3)+"분"+i+"초",(i*i)-i+"전역",
                        (i+i-1)+"번 역",(i+i+1)+"분"+i+"초",(i*i)-(i-2)+"전역",
                        (i+i+3)+"번 역",(i+i+4)+"분"+(i+1)+"초",(i*i)-(i-3)+"전역"));
        }
        subwayAdapter.setArrayList(arrayList);

        recyclerView.setAdapter(subwayAdapter);

//        TextView textView = (TextView) rootView.findViewById(R.id.textView2);
//
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                PlaceData placeData = new PlaceData(R.drawable.ic_baseline_star_24,
//                        "1번 역","0번 방면","3번 방면",
//                        "00분00초","00분00초","00분00초",
//                        "00분00초","00분00초","00분00초",
//                        "00분00초","00분00초","00분00초" ,
//                        "00분00초","00분00초","00분00초",
//                        "00분00초","00분00초","00분00초",
//                        "00분00초","00분00초","00분00초");
//                arrayList.add(placeData);
//                subwayAdapter.notifyDataSetChanged();
//            }
//        });

        return rootView;
    }
}
