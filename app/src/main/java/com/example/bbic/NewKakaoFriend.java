package com.example.bbic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bbic.Adapter.FriendAskAdapter;
import com.example.bbic.Adapter.NewFriendAskAdapter;
import com.example.bbic.Data.Friend;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewKakaoFriend extends AppCompatActivity {

    private ListView listView;
    private NewFriendAskAdapter adapter;
    private List<Friend> friendList;
    private List<Friend> userFriendlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        listView = findViewById(R.id.Kakao_friend_add_lv);
        friendList = new ArrayList<Friend>();
        userFriendlist = new ArrayList<Friend>();

        adapter = new NewFriendAskAdapter(this, friendList, userFriendlist);
        listView.setAdapter(adapter);

        Intent intent = getIntent();

        try{
            long userCode = intent.getLongExtra("코드", 0);

            test test = new test();
            test.test1();

//                test.getArray();
//
//                System.out.println("가져온 카카오톡 친구목록" + Arrays.toString(test.getArray()));

            new Thread() {
                @Override
                public void run() {
                    Document doc;
                    try {
                        Thread.sleep(2000);
                        test.getArray();
                        System.out.println("가져온 카카오톡 친구목록" + Arrays.toString(test.getArray()));
                        System.out.println("가져온 카카오톡 친구목록" + test.getArray()[0].getFriend_id());
                        System.out.println("가져온 카카오톡친구 배열 크기" + test.getArray().length);
                        int count = 0;

                        while (count < test.getArray().length) {

                            long friendCode = test.getArray()[count].getFriend_id();
                            int friendStatus = 0;
                            String friendName = test.getArray()[count].getFriend_Nickname();
                            String friendEmail = "";
                            String friendProfile  = test.getArray()[count].getFriend_Image();
                            int friendGhost = 0;
                            double friendLong = 0;
                            double friendLat = 0;
                            System.out.println();

                            Friend friend = new Friend(userCode, friendCode, friendStatus, friendName, friendEmail, friendProfile, friendGhost, friendLong, friendLat);
                            friendList.add(friend);

                            count++;
                        }


                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
            }.start();
        }catch (Exception e){

        }


    }
}
