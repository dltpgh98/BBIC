package com.example.bbic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PathOverlay;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

//ver 0.0.1
public class Maps_Activity extends AppCompatActivity implements OnMapReadyCallback {

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
                    drawerLayout.closeDrawer(drawerView);
                    break;
                case R.id.drawer_menu_2:
                    break;
                case R.id.drawer_menu_3:
                    System.out.println("click");
                    Intent intent3 = new Intent(getApplicationContext(), Bookmark.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.drawer_menu_4:
                    break;
                case R.id.drawer_menu_5:
                    Intent intent5 = new Intent(getApplicationContext(), FP.class);
                    startActivity(intent5);
                    finish();
                    break;
                case R.id.drawer_menu_6:
                    Intent intent6 = new Intent(getApplicationContext(), Setting_Activity.class);
                    startActivity(intent6);
                    finish();
                    break;
                case R.id.main_search_ibtn:
//                    Intent intent = new Intent(getApplicationContext(), Bookmark.class);
//                    startActivity(intent);
                    break;
            }
        }
    }

    //참조를 위한 각 객체 생성
    private DrawerLayout drawerLayout;
    private View drawerView;
    private ImageButton menuIbtn, searchIbtn;
    private TextView
            temText, fineText, ultraText, covidText;
    private ImageView weatherImage;

    private Button[] drawerMenu = new Button[6];
    private FusedLocationSource locationSource;

    private NaverMap naverMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final String[] PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    List<LatLng> lstLatLng = new ArrayList<>();



    private final String temURL = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=경기도부천시날씨"; //웹크롤링 할 주소(1)
    private final String covidURL = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&qvt=0&query=코로나19"; //웹크롤링 할 주소(2)
    private String allDust, weather, tem, fineDust, ultraFineDust, covidNum;
    @Override
    public void onMapReady(@NonNull NaverMap naverMap){
        this.naverMap = naverMap;

        naverMap.setLocationSource(locationSource); // 현재위치
        ActivityCompat.requestPermissions(this,PERMISSION, LOCATION_PERMISSION_REQUEST_CODE); //현재위치 표시할떄 권한 확인

        naverMap.getUiSettings().setLocationButtonEnabled(true);
        LatLng initialPosition = new LatLng(37.506855, 127.066242);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);


    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)){
            if(!locationSource.isActivated()){
                        naverMap.setLocationTrackingMode(LocationTrackingMode.None);
                        return;
            }else{
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //도성대
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);




        //이세호
        //버튼 클릭 리스너 클래스 객체 생성(클릭 이벤트를 위함)
        BtnOnClickListener onClickListener = new BtnOnClickListener();

        //각 객체의 참조값을 넣어줌
        drawerLayout = (DrawerLayout) findViewById(R.id.main_activity);
        drawerView = (View) findViewById(R.id.drawer_main);
        menuIbtn = (ImageButton) findViewById(R.id.menu_ibtn);
        temText = (TextView) findViewById(R.id.drawer_tem_text);
        fineText = (TextView) findViewById(R.id.drawer_fine_text);
        ultraText = (TextView) findViewById(R.id.drawer_ultra_text);
        covidText = (TextView) findViewById(R.id.drawer_covid_text);
        weatherImage = (ImageView) findViewById(R.id.drawer_weather_img);
        searchIbtn = (ImageButton)findViewById(R.id.main_search_ibtn);

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
        drawerMenu[0].setOnClickListener(onClickListener);
        drawerMenu[1].setOnClickListener(onClickListener);
        drawerMenu[2].setOnClickListener(onClickListener);
        drawerMenu[3].setOnClickListener(onClickListener);
        drawerMenu[4].setOnClickListener(onClickListener);
        drawerMenu[5].setOnClickListener(onClickListener);

        searchIbtn.setOnClickListener(onClickListener); // 검색 버튼 리스너


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

        //뷰페이저 설정
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        ViewpagerAdapter adapter = new ViewpagerAdapter(setTextList());
        viewPager.setAdapter(adapter);


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
            case "구름많음":
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