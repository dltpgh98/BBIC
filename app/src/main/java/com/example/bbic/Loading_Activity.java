package com.example.bbic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Loading_Activity extends AppCompatActivity {

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    //로딩화면
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this,Login_Activity.class);
        checkRunTimePermission();
        startActivity(intent);
        finish();
    }

    boolean checkRunTimePermission(){//런타임 퍼미션 처리
        //위치 퍼미션 체크
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(Loading_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(Loading_Activity.this,Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {//퍼미션을 가지고있다면
            Log.d("포그라운드 위치권한","TRUE");
        }else{//권한이 없다면
            Toast.makeText(Loading_Activity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            do {
                if(ActivityCompat.shouldShowRequestPermissionRationale(Loading_Activity.this,REQUIRED_PERMISSIONS[0])){//권한 요청을 거부한적이 있을때
                    ActivityCompat.requestPermissions(Loading_Activity.this, REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);//사용자에게 권한 요청
                }else{//요청이 처음이라면
                    ActivityCompat.requestPermissions(Loading_Activity.this, REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);//사용자에게 권한 요청
                }

                hasFineLocationPermission = ContextCompat.checkSelfPermission(Loading_Activity.this,Manifest.permission.ACCESS_FINE_LOCATION);
                hasCoarseLocationPermission = ContextCompat.checkSelfPermission(Loading_Activity.this,Manifest.permission.ACCESS_COARSE_LOCATION);
            }while (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED);
        }
        int hasBackGroundPermission = ContextCompat.checkSelfPermission(Loading_Activity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        if (hasBackGroundPermission == PackageManager.PERMISSION_GRANTED){
            Log.d("백그라운드 위치권한","TRUE");
        }else{
            do {
                ActivityCompat.requestPermissions(Loading_Activity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},0);
                hasBackGroundPermission = ContextCompat.checkSelfPermission(Loading_Activity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

            }while (hasBackGroundPermission == PackageManager.PERMISSION_DENIED);
        }
        return true;
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