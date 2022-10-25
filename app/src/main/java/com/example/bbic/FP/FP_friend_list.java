package com.example.bbic.FP;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bbic.Adapter.FriendListAdapter;
import com.example.bbic.Data.Friend;
import com.example.bbic.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FP_friend_list extends Fragment {
    FP_friend_ask fp_friend_ask;

    private ListView listView;
    private FriendListAdapter adapter;
    private List<Friend> friendList;
    private List<Friend> userFriendlist;
    Bundle bundleask; // 프래그먼트 간의 데이터 전달 시 필요

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_friend_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.friend_list_rv);
        friendList = new ArrayList<Friend>();
        userFriendlist = new ArrayList<Friend>();
        adapter = new FriendListAdapter(getContext(), friendList, userFriendlist, this);
        listView.setAdapter(adapter);

        String getFriend = null;
        long userKakaoCode = 0;

        if (getArguments() != null) {
            getFriend = getArguments().getString("friendlist");
            userKakaoCode = getArguments().getLong("userCode");
            System.out.println("friendlist 친구 목록 확인 : " + getFriend);
            System.out.println("친구 리스트에서 확인 마지막 제발 " + userKakaoCode);
        }


        try {
            JSONObject jsonObject = new JSONObject(getFriend);
            System.out.println("받은 친구 리스트" + getFriend);
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

                Friend friend = new Friend(userCode, friendCode, friendStatus, friendName, friendEmail, friendProfile, friendGhost, friendLong, friendLat, "","",5);
                if (userCode == userKakaoCode) {
                    if (friendStatus == 1) {
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
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this).attach(this).commit();

        return rootView;
    }

//    public void searchUser(long userCode) {
//        System.out.println("서치유저 실행은 하는지?");
//        friendList.clear();
//        for (int i = 0; i < userFriendlist.size(); i++) {
//            System.out.println("---------------------------------------");
//            System.out.println(friendList.get(i).getUserKakapCode());
//            System.out.println(userCode);
//            if (userFriendlist.get(i).getUserKakapCode() == userCode) {//값이 있으면 true를 반환함
//                friendList.add(userFriendlist.get(i));
//            }
//        }
//        adapter.notifyDataSetChanged();//어댑터에 값일 바뀐것을 알려줌
//    }
//
//    public void searchStatus(int status) {
//        userFriendlistStatus.clear();
//        for (int i = 0; i < userFriendlist.size(); i++) {
//            System.out.println("---------------------------------------");
//            System.out.println(userFriendlist.get(i).getFriendStatus());
//            System.out.println(status);
//            if (userFriendlist.get(i).getFriendStatus() == status) {//값이 있으면 true를 반환함
//                userFriendlistStatus.add(userFriendlist.get(i));
//            }
//        }
//        adapter.notifyDataSetChanged();//어댑터에 값일 바뀐것을 알려줌
//    }

}