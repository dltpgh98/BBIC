package com.example.bbic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Loading_Activity extends AppCompatActivity {

    private Button gpsBtn;

    //로딩화면
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        Intent intent = new Intent(this,Login_Activity.class);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        if(checkRunTimePermission()==true){
            startActivity(intent);
            finish();
        }

        ActivityCompat.requestPermissions(Loading_Activity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},0);

        gpsBtn = (Button) findViewById(R.id.loading_gps_btn);
        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Loading_Activity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},0);
            }
        });
    }

    boolean checkRunTimePermission(){//런타임 퍼미션 처리
        //위치 퍼미션 체크
        int hasBackGroundPermission = ContextCompat.checkSelfPermission(Loading_Activity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        if (hasBackGroundPermission == PackageManager.PERMISSION_GRANTED){
            return true;
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            Log.d("권환확인","TRUE");

            Intent intent = new Intent(this,Login_Activity.class);
            startActivity(intent);
            finish();
        }
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