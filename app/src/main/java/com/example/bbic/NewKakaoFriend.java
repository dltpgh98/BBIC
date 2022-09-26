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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        Intent intent = getIntent();
        ArrayList<KakaoFriend> kakaoFriendArrayList = (ArrayList<KakaoFriend>)getIntent().getSerializableExtra("key");

        System.out.println("가져온 카카오톡 친구 리스트 배열 학인(배열 크기)" + kakaoFriendArrayList.size());

        System.out.println("가져온 카카오친구 목록" + kakaoFriendArrayList.toString());

//        for (int i = 0; i < kakaoFriendArrayList.size(); i++){
//            String str =  kakaoFriendArrayList.get(i).getFriend_Nickname();
//        }

        listView = (ListView) findViewById(R.id.Kakao_friend_add_lv);
        adapter = new NewKakaoFriendListAdater(kakaoFriendArrayList, this);
        listView.setAdapter(adapter);

    }
}