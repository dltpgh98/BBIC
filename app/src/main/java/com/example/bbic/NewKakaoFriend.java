package com.example.bbic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bbic.Adapter.FriendAskAdapter;
import com.example.bbic.Adapter.NewKakaoFriendListAdater;
import com.example.bbic.Data.Friend;

import java.util.ArrayList;
import java.util.List;

public class NewKakaoFriend extends AppCompatActivity {

    private ListView listView;
    private NewKakaoFriendListAdater adapter;
    private List<KakaoFriend> kakaoFriendList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        Intent intent = getIntent();
        ArrayList<KakaoFriend> kakaoFriendArrayList = (ArrayList<KakaoFriend>)getIntent().getSerializableExtra("key");


        List<KakaoFriend> list = (List<KakaoFriend>)kakaoFriendArrayList;
        System.out.println("가져온 카카오톡 친구 리스트 배열 학인(배열 크기)" + list.size());

        System.out.println("가져온 카카오친구 목록" + list.toString());

        listView = (ListView) findViewById(R.id.Kakao_friend_add_lv);
        kakaoFriendList = new ArrayList<KakaoFriend>();
        adapter = new NewKakaoFriendListAdater(kakaoFriendList);
        listView.setAdapter(adapter);

//        KakaoFriend[] kakaoFriend = new KakaoFriend[2];
//
//        for (int i = 0; i <list.size() ; i++){
//            kakaoFriend[i] = new KakaoFriend(list.get(i).getFriend_id(), list.get(i).getFriend_Nickname(), list.get(i).getFriend_Image(), list.get(0).getFriend_uuid());
//
//            System.out.println("가져온 카카오친구 아이디" + list.get(0).getFriend_id());
//            System.out.println("가져온 카카오친구 닉네임" + list.get(0).getFriend_Nickname());
//            System.out.println("가져온 카카오친구 프로필" + list.get(0).getFriend_Image());
//            System.out.println("가져온 카카오친구 UUID" + list.get(0).getFriend_uuid());
//
//            kakaoFriendList.add(kakaoFriend[i]);
//        }

        int count= 0;
        while(count < list.size()){
            long friend_ID = list.get(count).getFriend_id();
            String friend_name = list.get(count).getFriend_Nickname();
            String friend_profile = list.get(count).getFriend_Image();
            String friend_UUID = list.get(count).getFriend_uuid();

            System.out.println("가져온 카카오친구 아이디" + list.get(count).getFriend_id());
            System.out.println("가져온 카카오친구 닉네임" + list.get(count).getFriend_Nickname());
            System.out.println("가져온 카카오친구 프로필" + list.get(count).getFriend_Image());
            System.out.println("가져온 카카오친구 UUID" + list.get(count).getFriend_uuid());

            KakaoFriend kakaoFriend = new KakaoFriend(friend_ID, friend_name, friend_profile, friend_UUID);
            kakaoFriendList.add(kakaoFriend);

            count++;
        }


    }
}