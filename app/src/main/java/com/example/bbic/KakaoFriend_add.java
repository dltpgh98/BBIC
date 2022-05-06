package com.example.bbic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kakao.sdk.talk.TalkApiClient;
import com.kakao.sdk.talk.model.FriendOrder;
import com.kakao.sdk.talk.model.Order;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import kotlin.jvm.functions.Function2;


public class KakaoFriend_add extends AppCompatActivity {

    TextView close;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promise_write);


        close = (TextView) findViewById(R.id.promise_write_close_tv);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
}