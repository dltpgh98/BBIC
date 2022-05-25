package com.example.bbic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FP_friend_list extends Fragment {


    private ListView listView;
    private FriendListAdapter adapter;
    private List<Friend> friendList;
    Bundle bundle; // 프래그먼트 간의 데이터 전달 시 필요

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_friend_list, container, false);

        listView = (ListView) rootView.findViewById(R.id.friend_list_rv);
        friendList = new ArrayList<Friend>();

        adapter = new FriendListAdapter(getContext(), friendList);
        listView.setAdapter(adapter);

        String getFriend = null;

        if(getArguments() != null){
            getFriend = getArguments().getString("friendlist");
            System.out.println("friendlist 친구 목록 확인 : " + getFriend);
        }

        try{
            JSONObject jsonObject = new JSONObject(getFriend);
            System.out.println(getFriend);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            long userCode;
            long friendCode;
            String friendName;
            String friendEmail;
            String friendProfile;
            double friendLong;
            double friendLat;
            int friendGhost;

            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                userCode = object.getLong("F.K_code1");
                friendCode = object.getLong("F.K_code2");
                friendName = object.getString("K.K_name");
                friendEmail = object.getString("K.K_email");
                friendProfile = object.getString("K.K_profile");
                friendLong = object.getDouble("K.K_long");
                friendLat = object.getDouble("K.K_lat");
                friendGhost = object.getInt("K.K_ghost");

                Friend friend = new Friend(userCode, friendCode, friendName, friendEmail, friendProfile, friendGhost, friendLong, friendLat);
                friendList.add(friend);
                count++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return rootView;
    }
}
