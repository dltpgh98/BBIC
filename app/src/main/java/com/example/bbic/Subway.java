package com.example.bbic;
import android.annotation.SuppressLint;
import android.widget.Toast;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

public class Subway extends AppCompatActivity {

    //참조를 위한 각 객체 생성
    private DrawerLayout drawerLayout;
    private View drawerView;
    private ImageButton menuIbtn, homeIbtn;
    private TextView
            temText, fineText, ultraText, covidText, nickName;
    private ImageView weatherImage, profile;
    private String name, address;
    private PhotoView subway;
    private SubwayMapTouchPoint subwayMapTouchPoint;
    public static final Locale DEFAULT_LOCALE = Locale.KOREAN;
    private float x = -1, y = -1;

    private Button[] drawerMenu = new Button[6];

    private final String temURL = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=경기도부천시날씨"; //웹크롤링 할 주소(1)
    private final String covidURL = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&qvt=0&query=코로나19"; //웹크롤링 할 주소(2)
    private String allDust, weather, tem, fineDust, ultraFineDust, covidNum;

    //버튼 클릭 리스너 클래스
    class BtnOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                //case를 통해 id에 따른 클릭이벤트 실행
                case R.id.menu_ibtn:
                    drawerLayout.openDrawer(drawerView);
                    break;
                case R.id.drawer_menu_1:
                    Log.d("클릭", "onClick: ");
                    Intent intent1 = new Intent(getApplicationContext(), FP.class);
                    intent1.putExtra("닉네임", name);
                    intent1.putExtra("프로필", address);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.drawer_menu_2:
                    break;
                case R.id.drawer_menu_3:
                    drawerLayout.closeDrawer(drawerView);
                    break;
                case R.id.drawer_menu_4:
                    break;
                case R.id.drawer_menu_5:
                    Intent intent5 = new Intent(getApplicationContext(), FP.class);
                    intent5.putExtra("닉네임", name);
                    intent5.putExtra("프로필", address);
                    startActivity(intent5);
                    finish();
                    break;
                case R.id.drawer_menu_6:
                    Intent intent6 = new Intent(getApplicationContext(), Setting_Activity.class);
                    intent6.putExtra("닉네임", name);
                    intent6.putExtra("프로필", address);
                    startActivity(intent6);
                    finish();
                    break;
                case R.id.home_btn:
                    Intent home = new Intent(getApplicationContext(), Maps_Activity.class);
                    home.putExtra("닉네임", name);
                    home.putExtra("프로필", address);
                    startActivity(home);
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway);



        //버튼 클릭 리스너 클래스 객체 생성(클릭 이벤트를 위함)
        BtnOnClickListener onClickListener = new BtnOnClickListener();


        //각 객체의 참조값을 넣어줌
        drawerLayout = (DrawerLayout) findViewById(R.id.main_activity);
        drawerView = (View) findViewById(R.id.drawer_main);
        menuIbtn = (ImageButton) findViewById(R.id.menu_ibtn);
        homeIbtn = (ImageButton) findViewById(R.id.home_btn); // 홈화면(지도)
        temText = (TextView) findViewById(R.id.drawer_tem_text);
        fineText = (TextView) findViewById(R.id.drawer_fine_text);
        ultraText = (TextView) findViewById(R.id.drawer_ultra_text);
        covidText = (TextView) findViewById(R.id.drawer_covid_text);
        weatherImage = (ImageView) findViewById(R.id.drawer_weather_img);
        profile = (ImageView)findViewById(R.id.drawer_profile_img); // 카카오톡 프로파일 이미지
        nickName = (TextView)findViewById(R.id.drawer_profile_name); // 카카오톡 닉네임
        subway = (PhotoView) findViewById(R.id.subway_img); // 지하철 노선도 이미지

        drawerMenu[0] = (Button) findViewById(R.id.drawer_menu_1);
        drawerMenu[1] = (Button) findViewById(R.id.drawer_menu_2);
        drawerMenu[2] = (Button) findViewById(R.id.drawer_menu_3);
        drawerMenu[3] = (Button) findViewById(R.id.drawer_menu_4);
        drawerMenu[4] = (Button) findViewById(R.id.drawer_menu_5);
        drawerMenu[5] = (Button) findViewById(R.id.drawer_menu_6);

        //레이아웃에 네비게이션 드로어 설젇
        drawerLayout.setDrawerListener(drawerListener);

        //버튼의 클릭 리스너 설정
        menuIbtn.setOnClickListener(onClickListener);
        homeIbtn.setOnClickListener(onClickListener);
        drawerMenu[0].setOnClickListener(onClickListener);
        drawerMenu[1].setOnClickListener(onClickListener);
        drawerMenu[2].setOnClickListener(onClickListener);
        drawerMenu[3].setOnClickListener(onClickListener);
        drawerMenu[4].setOnClickListener(onClickListener);
        drawerMenu[5].setOnClickListener(onClickListener);


        Intent intent = getIntent();
        name = intent.getStringExtra("닉네임");
        address = intent.getStringExtra("프로필");
        nickName.setText(name); // 카카오톡 프로필 닉네임
        Glide.with(this).load(address).circleCrop().into(profile); // 카카오톡 프로필 이미지

        subway.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int parentWidth = view.getWidth();    // 부모 View 의 Width
                int parentHeight = view.getHeight();    // 부모 View 의 Height



                //int action = view.getA
                float curx = motionEvent.getRawX();
                float cury =  motionEvent.getRawY();

                if(motionEvent.getAction() == motionEvent.ACTION_DOWN){
                    Log.d("viewTest", "X : " + view.getLeft() +" Y : " + view.getTop());    // View 를 터치한 지점의 절대 좌표값.
                    Log.d("viewTest", "X : " + motionEvent.getX() +" Y : " + motionEvent.getY());    // View 를 터치한 지점의 절대 좌표값.
                    Log.d("viewTest", "RawX : " + motionEvent.getRawX() +" RawY : " + motionEvent.getRawY());    // View 를 터치한 지점의 절대 좌표값.
                    Log.d("viewTest", "view.getHeight : " + view.getHeight() + " view.getWidth : " + view.getWidth());    // View 의 Width, Height
                }
                //1호선
                if(476 - 18<curx && curx< 476 + 18  && 791 - 18<= cury && cury <= 791 + 18){
                    Toast.makeText(getApplicationContext(), "소요산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(542 - 18<curx && curx<542  + 18  && 785 - 18<= cury && cury <= 785 + 18){
                    Toast.makeText(getApplicationContext(), "동두천 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(607 - 18<curx && curx<607  + 18  && 791 - 18<= cury && cury <=791  + 18){
                    Toast.makeText(getApplicationContext(), "보산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(673 - 18<curx && curx<673  + 18  && 788 - 18<= cury && cury <=788  + 18){
                    Toast.makeText(getApplicationContext(), "동두천 중앙 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(732 - 18<curx && curx<732  + 18  && 791 - 18<= cury && cury <=791  + 18){
                    Toast.makeText(getApplicationContext(), "지행 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(798 - 18<curx && curx<798  + 18  && 791 - 18<= cury && cury <=791  + 18){
                    Toast.makeText(getApplicationContext(), "덕정 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(864 - 18<curx && curx<864  + 18  && 791 - 18<= cury && cury <=791  + 18){
                    Toast.makeText(getApplicationContext(), "덕계 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(929 - 18<curx && curx<929  + 18  && 791 - 18<= cury && cury <=791  + 18){
                    Toast.makeText(getApplicationContext(), "양주 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(992 - 18<curx && curx<992  + 18  && 791 - 18<= cury && cury <=791  + 18){
                    Toast.makeText(getApplicationContext(), "녹양 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1060 - 18<curx && curx<1060  + 18  && 791 - 18<= cury && cury <=791  + 18){
                    Toast.makeText(getApplicationContext(), "가능 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1123 - 18<curx && curx<1123  + 18  && 791 - 18<= cury && cury <=791  + 18){
                    Toast.makeText(getApplicationContext(), "의정부 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1177 - 18<curx && curx<1177  + 18  && 791 - 18<= cury && cury <=791  + 18){
                    Toast.makeText(getApplicationContext(), "회룡 (환승, 1호선, 의정부경전역)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1251 - 18<curx && curx<1251  + 18  && 791 - 18<= cury && cury <=791  + 18){
                    Toast.makeText(getApplicationContext(), "망월사 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1338 - 18<curx && curx<1338  + 18  && 827 - 18<= cury && cury <=827  + 18){
                    Toast.makeText(getApplicationContext(), "도봉산 (환승, 1호선, 7호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1338 - 18<curx && curx<1338  + 18  && 874 - 18<= cury && cury <=874  + 18){
                    Toast.makeText(getApplicationContext(), "도봉 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1338 - 18<curx && curx<1338  + 18  && 913 - 18<= cury && cury <=913  + 18){
                    Toast.makeText(getApplicationContext(), "방학 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1338 - 18<curx && curx<1338  + 18  && 985 - 18<= cury && cury <=985  + 18){
                    Toast.makeText(getApplicationContext(), "창동 (환승 1호선, 4호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1338 - 18<curx && curx<1338  + 18  && 1014 - 18<= cury && cury <=1014  + 18){
                    Toast.makeText(getApplicationContext(), "녹천 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1338 - 18<curx && curx<1338  + 18  && 1050 - 18<= cury && cury <=1050  + 18){
                    Toast.makeText(getApplicationContext(), "월계 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1338 - 18<curx && curx<1338  + 18  && 1089 - 18<= cury && cury <=1089  + 18){
                    Toast.makeText(getApplicationContext(), "광운대 (환승 1호선, 경춘선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1323 - 18<curx && curx<1323  + 18  && 1122 - 18<= cury && cury <=1122  + 18){
                    Toast.makeText(getApplicationContext(), "석계 (환승, 1호선, 6호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1296 - 18<curx && curx<1296  + 18  && 1169 - 18<= cury && cury <=1169  + 18){
                    Toast.makeText(getApplicationContext(), "신이문 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1269 - 18<curx && curx<1269  + 18  && 1199 - 18<= cury && cury <=1199  + 18){
                    Toast.makeText(getApplicationContext(), "외대앞 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(1242 - 18<curx && curx<1242  + 18  && 1229 - 18<= cury && cury <=1229  + 18){
                    Toast.makeText(getApplicationContext(), "회기 (환승 1호선, 경의중앙선, 경춘선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1203 - 18<curx && curx<1203  + 18  && 1229 - 18<= cury && cury <=1229  + 18){
                    Toast.makeText(getApplicationContext(), "청량리 (환승, 1호선, 수인분당선, 경의중앙선. 경춘선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(1111 - 18<curx && curx<1111  + 18  && 1229 - 18<= cury && cury <=1229  + 18){
                    Toast.makeText(getApplicationContext(), "제기동 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(1060 - 18<curx && curx<1060  + 18  && 1229 - 18<= cury && cury <=1229  + 18){
                    Toast.makeText(getApplicationContext(), "신설동 (환승, 2호선, 우이신설선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(992 - 18<curx && curx<992  + 18  && 1229 - 18<= cury && cury <=1229  + 18){
                    Toast.makeText(getApplicationContext(), "동묘앞 (환승 1호선, 6호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(920 - 18<curx && curx<920  + 18  && 1229 - 18<= cury && cury <=1229  + 18){
                    Toast.makeText(getApplicationContext(), "동대문 (환승 1호선, 4호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(777 - 18<curx && curx<777  + 18  && 1229 - 18<= cury && cury <=1229  + 18){
                    Toast.makeText(getApplicationContext(), "종로5가 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(628 - 18<curx && curx<628  + 18  && 1232 - 18<= cury && cury <=1232  + 18){
                    Toast.makeText(getApplicationContext(), "종로3가 (환승, 1호선, 3호선, 5호선) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(536 - 18<curx && curx< 536 +18 && 1285 - 18<= cury && cury <= 1285 + 18){
                    Toast.makeText(getApplicationContext(), "종각 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1366 - 18<= cury && cury <= 1366 + 18){
                    Toast.makeText(getApplicationContext(), "시청 (환승 1호선, 2호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1470 - 18<= cury && cury <= 1470 + 18){
                    Toast.makeText(getApplicationContext(), "서울역 (환승, 1호선. 4호선, 공항철도, 경의중앙선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1559 - 18<= cury && cury <= 1559 + 18){
                    Toast.makeText(getApplicationContext(), "남영 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1768 - 18<= cury && cury <= 1768 + 18){
                    Toast.makeText(getApplicationContext(), "용산 (환승, 1호선, 경의중앙선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(351 - 18<curx && curx< 351 +18 && 1815 - 18<= cury && cury <= 1815 + 18){
                    Toast.makeText(getApplicationContext(), "노량진 (환승, 1호선, 9호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(205 - 18<curx && curx< 205 +18 && 1783 - 18<= cury && cury <= 1783 + 18){
                    Toast.makeText(getApplicationContext(), "대방 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(133 - 18<curx && curx< 133 +18 && 1711 - 18<= cury && cury <= 1711 + 18){
                    Toast.makeText(getApplicationContext(), "신길 (환승 1호선, 5호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(97 - 18<curx && curx< 97 +18 && 1741 - 18<= cury && cury <= 1741 + 18){
                    Toast.makeText(getApplicationContext(), "영등포 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(53 - 18<curx && curx< 53 +18 && 1786 - 18<= cury && cury <= 1786 + 18){
                    Toast.makeText(getApplicationContext(), "신도림 (환승, 1호선, 2호선, 2호선, 2호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(32 - 18<curx && curx< 32 +18 && 1815 - 18<= cury && cury <= 1815 + 18){
                    Toast.makeText(getApplicationContext(), "구로 (환승, 1호선, 1호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(32 - 18<curx && curx< 32 +18 && 1958 - 18<= cury && cury <= 1958 + 18){
                    Toast.makeText(getApplicationContext(), "가산 디지털 단지 (환승, 1호선, 7호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "독산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "금천구청 (환승, 1호선, 1호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "광명 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "석수 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "관악 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "안양 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "명학 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "금정 (환승 1호선, 4호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "군포 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "당정 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "의왕 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "성균관대 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "화서 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "수원 (환승, 1호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "세류 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "병점 (환승, 1호선, 1호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "서동탄 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "세마 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "오산대 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "오산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "진위 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "송탄 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "서정리 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "평택지제 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "평택 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "성환 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "직산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "두정 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "천안 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "봉명 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "쌍용(나사렛대) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "아산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "탕정 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "배방 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "온양온천 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신창(순천향대) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "구일 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개봉 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "오류동 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "온수 (환승, 1호선, 7호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "역곡 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "소사 (환승, 1호선, 서해선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "부천 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "중동 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "송내 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "부개 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "부평 (환승, 1호선, 인천1호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "백운 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "동암 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "간석 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "주안 (환승, 1호선, 인천2호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "도화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "제물포 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "도원 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "동인천 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "인천 (환승, 1호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }



                //2호선
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "까치산 (환승, 2호선, 5호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신정네거리 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "양천구청 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "도림천 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "신도림 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "대림 (환승, 2호선, 7호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "구로 디지털단지 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신대방 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신림 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "봉천 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "서울대 입구 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "낙성대 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "사당 (환승, 2호선, 4호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "방배 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "서초 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "교대 (환승, 2호선, 3호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "강남 (환승, 2호선, 신분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "역삼 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "선릉 (환승, 2호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "삼성 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "종합운동장 (환승, 2호선, 9호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "잠실새내 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "잠실 (한승, 2호선, 8호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "잠실나루 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "강변 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "구의 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "건대입구 (환승, 2호선, 7호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "성수 (환승, 2호선, 2호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "용답 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신답 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "용두 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "신설동 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "뚝섬 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "한양대 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "왕십리 (환승, 2호선, 5호선, 수인분당선, 경의중앙선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "상왕십리 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신당 (환승, 2호선, 6호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "동대문역사문화공원 (환승, 2호선, 4호선, 5호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "을지로4가 (환승, 2호선, 5호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "을지로3가 (환승, 2호선, 3호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "을지로입구 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "시청 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "충정로 (환승, 2호선, 5호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "아현 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "이대 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신촌 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "홍대입구 (환승, 2호선, 공항철도, 경의중앙선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "합정 (환승, 2호선, 6호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "당산 (환승, 2호선, 9호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "영등포구청 (환승, 2호선, 5호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "문래 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");




                //3호선
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "대화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "주엽 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "정발산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "마두 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "백석 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "대곡 (환승, 3호선, 경의중앙선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "화정 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "원당 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "원흥 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "삼송 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "지축 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "구파발 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "연신내 (환승, 3호선, 6호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "불광 (환승, 3호선, 6호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "녹번 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "홍제 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "무악재 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "독립문 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "경복궁 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "안국 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "종로3가 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "을지로3가 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "충무로 (환승, 3호선, 4호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "동대입구 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "약수 (환승, 3호선, 6호선) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "금호 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "옥수 (환승, 3호선, 경의중앙선) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "압구정 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신사 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "잠원 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "고속터미널 (환승, 3호선, 7호선, 9호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "교대 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "남부터미널 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "양재 (환승, 3호선, 신분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "매봉 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "도곡 (환승, 3호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "대치 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "학여울 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "대청 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "일원 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "수서 (환승, 3호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "가락시장 (환승, 3호선, 8호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "경찰병원 (환승, 3호선, 8호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "오금 (환승, 3호선, 5호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }




                //4호선
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "오이도 (환승, 4호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "정왕 (환승, 4호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신길온천 (환승, 4호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "안산 (환승, 4호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "초지 (환승, 4호선, 수인분당선, 서해선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "고잔 (환승, 4호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "중앙 (환승, 4호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "한대앞 (환승, 4호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "상록수 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "반월 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "대야미 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "수리산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "금정 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "범계 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "평촌 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "인덕원 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "정부과천청사 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "과천 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "대공원 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "경마공원 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "선바위 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "남태령 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "사당 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "총신대입구(이수) (환승, 4호선, 7호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "동작 (환승, 4호선, 9호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "이촌 (환승, 4호선, 경의중앙선) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신용산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "삼각지 (환승, 4호선, 6호선) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "숙대입구 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "서울역 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "회현 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "명동 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "충무로 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "동대문역사문화공원 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "동대문 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "혜화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "한성대입구 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "성신여대입구 (환승, 4호선, 우이신설선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "길음 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "미아사거리 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "미아 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "수유(강북구청) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "쌍문 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "창동 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "노원 (환승, 4호선, 7호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "상계 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "당고개 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }





                //5호선
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "방화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "김포공항 (환승, 5호선, 9호선, 공항철도, 김포골드라인)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "송정 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "마곡 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "발산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "우장산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "화곡 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "까치산 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신정 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "목동 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "오목교 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "양평 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "영등포구청 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "영등포시장 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "신길 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "여의도 (환승, 5호선, 9호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "여의나루 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "마포 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "공덕 (환승, 5호선,6호선, 공항철도, 경의중상선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "애오개 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "충정로 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "서대문 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "광화문 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "종로3가 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "을지로4가 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "동대문역사문화공원 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "청구 (환승, 5호선, 6호선) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신금호 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "행당 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "왕십리 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "마장 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "답십리 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "장한성 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "군자 (환승, 5호선, 7호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "아차산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "광나루 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "천호 (환승, 5호선, 8호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "강동 (환승, 5호선, 5호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "길동 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "굽은다리 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "명일 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "고덕 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "상일동 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "강일 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "미사 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "하남풍산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "하남시청(덕풍, 신장) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "하남검단산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "둔촌동 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "올림픽공원 (환승, 5호선, 9호선) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "방이 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "오금 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개롱 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "거여 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "마천 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }








                //6호선
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신내 (환승, 6호선, 경춘선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "봉화산(서울의료원) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "화랑대 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "태릉입구 (환승, 6호선, 7호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "석계 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "돌곶이 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "상월곡 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "월곡 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "고려대 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "안암 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "보문 (환승, 6호선, 우이신설선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "창신 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "동묘앞 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "신당 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "청구 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "약수 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "버티고개 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "한강진 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "이태원 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "녹사평(용산구청) 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "삼각지 (환승, 6호선)터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "효창공원앞 (환승, 6호선, 경의중앙선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "공덕 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "대흥 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "광흫창 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "상수 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "합정 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "망원 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "마포구청 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "원드컵경기장 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "디지털미디어시티 (환승, 6호선, 공항철도, 경의중앙선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "증산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "새절 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "응암 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "역촌 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "불광 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "독바위 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "연신내 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "구산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }










                //7호선
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "석남(거북시장) (환승, 7호선, 인천2호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "산곡 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "부평구청 (환승, 7호선, 인천2호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "굴포천 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "삼산체육관 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "상동 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "부천시청 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신중동 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "춘의 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "부천종합운동장 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "까치울 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "온수 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "천왕 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "광명사거리 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "철산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "가산디지털단지 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "남구로 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "대립 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신풍 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "보라매 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신대방삼거리 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "장승배기 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "상도 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "숭실대입구 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "남성 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "총신대입구(이수) 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "내방 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "고속터미널 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "반포 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "논현 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "학동 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "강남구청 (환승, 7호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "청담 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "뚝섬유원지 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "건대입구 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "어린이대공원 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "군자 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "중곡 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "용마산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "사가정 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "면목 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18) {
                    Toast.makeText(getApplicationContext(), "상봉 (한승, 7호선, 경의중앙선, 경춘선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "중화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "먹골 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "태릉입구 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "공릉 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "하계 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "중계 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "노원 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "마들 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "수락산 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "도봉산 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "장암 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }








                //8호선
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "암사 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "천호 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "강동구청 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "몽촌토성 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "잠실 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "석촌 (환승, 8호선, 9호선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "송파 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
//                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
//                    Toast.makeText(getApplicationContext(), "가락시장 터치", Toast.LENGTH_SHORT).show();
//                    System.out.println("true");
//                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "문정 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "장지 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "복정 (환승, 8호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "남위례 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "산성 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "남한산성입구 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "단대오거리 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "신흥 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "수진 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "모란 (환승, 8호선, 수인분당선)터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }









                //9호선
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }
                if(500 - 18<curx && curx< 500 +18 && 1386 - 18<= cury && cury <= 1386 + 18){
                    Toast.makeText(getApplicationContext(), "개화 터치", Toast.LENGTH_SHORT).show();
                    System.out.println("true");
                }

































































                return true;
            }
        });



        //스레드간 데이터 전달을 위한 번들 생성
        final Bundle bundle = new Bundle();

        //웹크롤링 전용 스레드 생성
        new Thread(){
            @Override
            public void run(){
                Document doc ;
                try {
                    //웹 사이트 데이터 설정
                    doc = Jsoup.connect(temURL).get();

                    //온도정보
                    Elements temperature = doc.select(".temperature_text");
                    //미세먼지, 초미세먼지 정보
                    Elements dust = doc.select(".report_card_wrap");
                    //날씨
                    Elements wt = doc.select(".weather_main");

                    //웹 사이트 데이터 설정
                    doc = Jsoup.connect(covidURL).get();

                    Elements covid = doc.select(".status_info");

                    //문자열 변환 및 자르기
                    tem = temperature.get(0).text().substring(5);
                    allDust = dust.get(0).text();
                    String[] array = allDust.split(" ");
                    fineDust = array[1];
                    ultraFineDust = array[3];
                    weather = wt.get(0).text();
                    array = covid.get(0).text().split(" ");
                    covidNum = array[2];

                    //번들에 문자열 포장
                    bundle.putString("temperature",tem);
                    bundle.putString("fine",fineDust);
                    bundle.putString("ultra",ultraFineDust);
                    bundle.putString("weather",weather);
                    bundle.putString("covid",covidNum);
                    Message msg = handler.obtainMessage();

                    //메세지내용 번들로 지정
                    msg.setData(bundle);

                    //핸들러에 메세지 전달
                    handler.sendMessage(msg);

                    //10분마다 한번씩 실행
                    Thread.sleep(600000);
                } catch (IOException | InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();
            drawer_input();
        }
    };

    private void drawer_input()
    {
        temText.setText(tem+"C");

        switch (fineDust)
        {
            case "좋음":
                fineText.setTextColor(getResources().getColor(R.color.teal_200));
                break;
            case "보통":
                fineText.setTextColor(getResources().getColor(R.color.green));
                break;
            case "나쁨":
                fineText.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "매우나쁨":
                fineText.setTextColor(getResources().getColor(R.color.red));
                break;
            default:
                fineText.setTextColor(getResources().getColor(R.color.black));
                break;
        }

        switch (ultraFineDust)
        {
            case "좋음":
                ultraText.setTextColor(getResources().getColor(R.color.teal_200));
                break;
            case "보통":
                ultraText.setTextColor(getResources().getColor(R.color.green));
                break;
            case "나쁨":
                ultraText.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "매우나쁨":
                ultraText.setTextColor(getResources().getColor(R.color.red));
                break;
            default:
                ultraText.setTextColor(getResources().getColor(R.color.black));
                break;
        }

        switch (weather)
        {
            case "맑음":
                weatherImage.setImageResource(R.drawable.sunny);
                break;
            case "흐림":
                weatherImage.setImageResource(R.drawable.cloud);
                break;
            case "눈":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case "비":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            default:
                weatherImage.setImageResource(R.drawable.ic_baseline_block);
                Log.d("날씨 명", weather);
        }

        covidText.setText("전일 코로나 확진자 수 '"+covidNum+"' 명");
    }

    //드로어 이벤트 리스너
    DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            System.out.println("opening");
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
            System.out.println("open");
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
            System.out.println("close");
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
    protected ArrayList setTextList(){

        ArrayList<String> itemList = new ArrayList();
        itemList.add("Page 1");
        itemList.add("Page 2");
        itemList.add("Page 3");
        itemList.add("Page 4");
        itemList.add("Page 5");

        return itemList;
    }

}