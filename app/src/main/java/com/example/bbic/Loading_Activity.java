package com.example.bbic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class Loading_Activity extends AppCompatActivity {

    //로딩화면
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this,Login_Activity.class);
        startActivity(intent);
        finish();
    }
}
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.loading);                                           //loading.xml
//        StartLoading();                                                             //startLoading()호출
//    }
//    private void StartLoading() {                                                   //정의
//        Handler handler = new Handler();                                            //Handler 객체 생성
//        handler.postDelayed(new Runnable() {                                        //handler 객체 딜레이 2001(2초)끝나면 종료
//            @Override
//            public void run() {
//                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);  //화면
//                startActivity(intent);
//                finish();
//            }
//        }, 2001);
//    }