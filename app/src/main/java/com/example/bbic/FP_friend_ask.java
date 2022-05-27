package com.example.bbic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bbic.Adapter.FriendAskAdapter;
import com.example.bbic.Adapter.FriendListAdapter;
import com.example.bbic.Data.Friend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FP_friend_ask extends Fragment {

    private ListView listView;
    private FriendAskAdapter adapter;
    private List<Friend> friendList;
    private List<Friend> userFriendlist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_friend_ask, container, false);

        listView = (ListView) rootView.findViewById(R.id.friend_ask_rv);
        friendList = new ArrayList<Friend>();
        userFriendlist = new ArrayList<Friend>();

        adapter = new FriendAskAdapter(getContext(), friendList, userFriendlist, this);
        listView.setAdapter(adapter);

        String getFriend = null;
        long userKakaoCode = 0;

        if (getArguments() != null) {
            getFriend = getArguments().getString("friendlist");
            userKakaoCode = getArguments().getLong("userCode");
            System.out.println("friendask 친구 목록 확인 : " + getFriend);
            System.out.println("유저의 카카오코드 확인" + userKakaoCode);
        }

        try {
            JSONObject jsonObject = new JSONObject(getFriend);
            System.out.println(getFriend);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            long userCode;
            long friendCode;
            int friendStatus;
            String friendName;
            String friendEmail;
            String friendProfile;
            double friendLong;
            double friendLat;
            int friendGhost;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                userCode = object.getLong("F.K_code1");
                friendCode = object.getLong("F.K_code2");
                friendStatus = object.getInt("F.F_status");
                friendName = object.getString("K.K_name");
                friendEmail = object.getString("K.K_email");
                friendProfile = object.getString("K.K_profile");
                friendLong = object.getDouble("K.K_long");
                friendLat = object.getDouble("K.K_lat");
                friendGhost = object.getInt("K.K_ghost");

                Friend friend = new Friend(userCode, friendCode, friendStatus, friendName, friendEmail, friendProfile, friendGhost, friendLong, friendLat);
                if(userCode == userKakaoCode){
                    if(friendStatus == 0){
                        friendList.add(friend);
                    }
                }
//                friendList.add(friend); //원본용
//                userFriendlist.add(friend);//코드 검색용
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;
    }
}
