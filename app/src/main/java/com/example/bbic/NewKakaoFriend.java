package com.example.bbic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bbic.Adapter.FriendAskAdapter;
import com.example.bbic.Data.Friend;

import java.util.List;

public class NewKakaoFriend extends AppCompatActivity {

    private ListView listView;
    private FriendAskAdapter adapter;
    private List<Friend> friendList;
    private List<Friend> userFriendlist;
    private List<KakaoFriend> kakaoFriendList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        Intent intent = getIntent();
        kakaoFriendList = intent.getParcelableExtra("key");

        System.out.println("가져온 카카카오친구 목록" + kakaoFriendList);


    }
}