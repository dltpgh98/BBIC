package com.example.bbic.FP;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.bbic.Bookmark.Bookmark;
import com.example.bbic.Maps_Activity;
import com.example.bbic.R;
import com.example.bbic.Setting_Activity;
import com.example.bbic.Subway;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FP extends AppCompatActivity {
    TabLayout tabRoot;
    FP_friend fp_friend;
    FP_promise fp_promise;
    Bundle bundle;

    //참조를 위한 각 객체 생성
    private DrawerLayout drawerLayout;
    private View drawerView;
    private ImageButton menuIbtn, homeIbtn;
    private TextView
            temText, fineText, ultraText, covidText, nickName, areaText;
    private ImageView weatherImage, profile;

    private Button[] drawerMenu = new Button[6];

    private String weather, tem, fineDust, ultraFineDust, covidNum, name, address, area, city, friendlist, promiselist, locationlist, buslist, subwaylist;
    private long userCode;
    //버튼 클릭 리스너 클래스
    class BtnOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                //case를 통해 id에 따른 클릭이벤트 실행
                case R.id.menu_ibtn:
                    drawerLayout.openDrawer(drawerView);
                    break;
                case R.id.drawer_menu_1://지도
                    Intent intent1 = new Intent(getApplicationContext(), Maps_Activity.class);
//                    intent1.putExtra("닉네임", name);
//                    intent1.putExtra("프로필", address);
//                    intent1.putExtra("미세먼지", fineDust);
//                    intent1.putExtra("초미세먼지", ultraFineDust);
//                    intent1.putExtra("온도", tem);
//                    intent1.putExtra("날씨", weather);
//                    intent1.putExtra("도", area);
//                    intent1.putExtra("시", city);
//                    intent1.putExtra("코로나",covidNum);

                    intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.drawer_menu_2:
                    Intent intent2 = new Intent(getApplicationContext(), Subway.class);
                    intent2.putExtra("코드",userCode);
                    intent2.putExtra("닉네임", name);
                    intent2.putExtra("프로필", address);
                    intent2.putExtra("미세먼지", fineDust);
                    intent2.putExtra("초미세먼지", ultraFineDust);
                    intent2.putExtra("온도", tem);
                    intent2.putExtra("날씨", weather);
                    intent2.putExtra("도", area);
                    intent2.putExtra("시", city);
                    intent2.putExtra("코로나",covidNum);
                    intent2.putExtra("friendlist",friendlist);
                    intent2.putExtra("promiselist", promiselist);
                    intent2.putExtra("locationlist", locationlist);
                    intent2.putExtra("subwaylist", subwaylist);
                    intent2.putExtra("buslist", buslist);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.drawer_menu_3:
                    Intent intent3 = new Intent(getApplicationContext(), Bookmark.class);
                    intent3.putExtra("코드",userCode);
                    intent3.putExtra("닉네임", name);
                    intent3.putExtra("프로필", address);
                    intent3.putExtra("미세먼지", fineDust);
                    intent3.putExtra("초미세먼지", ultraFineDust);
                    intent3.putExtra("온도", tem);
                    intent3.putExtra("날씨", weather);
                    intent3.putExtra("도", area);
                    intent3.putExtra("시", city);
                    intent3.putExtra("코로나",covidNum);
                    intent3.putExtra("friendlist",friendlist);
                    intent3.putExtra("locationlist", locationlist);
                    intent3.putExtra("subwaylist", subwaylist);
                    intent3.putExtra("buslist", buslist);
                    intent3.putExtra("promiselist", promiselist);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.drawer_menu_4:
                    Intent intent4 = new Intent(getApplicationContext(), Maps_Activity.class);
                    intent4.putExtra("openFindWay",1);
                    intent4.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent4);
                    finish();
                    break;
                case R.id.drawer_menu_5:
                    drawerLayout.closeDrawer(drawerView);
                    break;
                case R.id.drawer_menu_6:
                    Intent intent6 = new Intent(getApplicationContext(), Setting_Activity.class);
                    intent6.putExtra("코드",userCode);
                    intent6.putExtra("닉네임", name);
                    intent6.putExtra("프로필", address);
                    intent6.putExtra("미세먼지", fineDust);
                    intent6.putExtra("초미세먼지", ultraFineDust);
                    intent6.putExtra("온도", tem);
                    intent6.putExtra("날씨", weather);
                    intent6.putExtra("도", area);
                    intent6.putExtra("시", city);
                    intent6.putExtra("코로나",covidNum);
                    intent6.putExtra("friendlist",friendlist);
                    intent6.putExtra("promiselist", promiselist);
                    intent6.putExtra("locationlist", locationlist);
                    intent6.putExtra("subwaylist", subwaylist);
                    intent6.putExtra("buslist", buslist);
                    startActivity(intent6);
                    finish();
                    break;
                case R.id.home_btn:
                    Intent home = new Intent(getApplicationContext(), Maps_Activity.class);
                    home.putExtra("코드",userCode);
                    home.putExtra("닉네임", name);
                    home.putExtra("프로필", address);
                    home.putExtra("미세먼지", fineDust);
                    home.putExtra("초미세먼지", ultraFineDust);
                    home.putExtra("온도", tem);
                    home.putExtra("날씨", weather);
                    home.putExtra("도", area);
                    home.putExtra("시", city);
                    home.putExtra("코로나",covidNum);
                    home.putExtra("friendlist",friendlist);
                    home.putExtra("promiselist", promiselist);
                    home.putExtra("locationlist", locationlist);
                    home.putExtra("subwaylist", subwaylist);
                    home.putExtra("buslist", buslist);
                    home.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(home);
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fp);


        fp_promise = new FP_promise();
        fp_friend = new FP_friend();

        tabRoot = findViewById(R.id.fp_tab_root);
        tabRoot.removeAllTabs();
        tabRoot.addTab(tabRoot.newTab().setText("친구"));
        tabRoot.addTab(tabRoot.newTab().setText("약속"));


        tabRoot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition())
                {
                    case 0:
                        new BackgroundTask_Friend().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        bundle = new Bundle();
                        bundle.putString("friendlist",friendlist);
                        bundle.putString("promiselist",promiselist);
                        bundle.putLong("userCode", userCode);
                        System.out.println("fp에서 유저코드 확인" + userCode);
                        bundle.putString("도", area);
                        bundle.putString("시", city);
                        bundle.putString("날씨", weather);
                        bundle.putString("온도", tem);
                        bundle.putString("미세먼지", fineDust);
                        bundle.putString("초미세먼지", ultraFineDust);
                        bundle.putString("코로나", covidNum);
                        bundle.putString("locationlist", locationlist);
                        bundle.putString("buslist", buslist);
                        bundle.putString("subwaylist", subwaylist);
                        bundle.putString("닉네임", name);
                        bundle.putString("프로필", address);
                        //System.out.println("친구 목록확인 " + friendlist);
                        fp_friend.setArguments(bundle);
                        if(fp_friend == null){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fp_tab_container, fp_friend).commit();
                            //getSupportFragmentManager().beginTransaction().hide(fp_promise).commit();
                        }else {
                            getSupportFragmentManager().beginTransaction().show(fp_friend).commit();
                            getSupportFragmentManager().beginTransaction().remove(fp_promise).commit();
                        }
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fp_tab_container, fp_friend).commit();

                        break;
                    case 1:
                        new BackgroundTask_Promise().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        bundle = new Bundle();
                        bundle.putString("promiselist",promiselist);
                        bundle.putString("friendlist",friendlist);
                        bundle.putLong("userCode", userCode);
                        bundle.putString("도", area);
                        bundle.putString("시", city);
                        bundle.putString("날씨", weather);
                        bundle.putString("온도", tem);
                        bundle.putString("미세먼지", fineDust);
                        bundle.putString("초미세먼지", ultraFineDust);
                        bundle.putString("코로나", covidNum);
                        bundle.putString("locationlist", locationlist);
                        bundle.putString("buslist", buslist);
                        bundle.putString("subwaylist", subwaylist);
                        bundle.putString("닉네임", name);
                        bundle.putString("프로필", address);
                        System.out.println("fp에서 유저코드 확인" + userCode);
                        //System.out.println("친구 목록확인 " + friendlist);
                        fp_promise.setArguments(bundle);
                        if(fp_promise == null){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fp_tab_container, fp_promise).commit();
                            //getSupportFragmentManager().beginTransaction().hide(fp_friend).commit();
                        }else {
                            getSupportFragmentManager().beginTransaction().add(R.id.fp_tab_container,fp_promise).commit();
                            getSupportFragmentManager().beginTransaction().hide(fp_friend).commit();
                        }
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fp_tab_container, fp_promise).commit();

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
        areaText = (TextView) findViewById(R.id.drawer_area_text);

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
        userCode = intent.getLongExtra("코드",0);
        name = intent.getStringExtra("닉네임");
        address = intent.getStringExtra("프로필");
        area = intent.getStringExtra("도");
        city = intent.getStringExtra("시");
        weather = intent.getStringExtra("날씨");
        tem = intent.getStringExtra("온도");
        fineDust = intent.getStringExtra("미세먼지");
        ultraFineDust = intent.getStringExtra("초미세먼지");
        covidNum = intent.getStringExtra("코로나");
        friendlist = intent.getStringExtra("friendlist");
        promiselist = intent.getStringExtra("promiselist");
        locationlist = intent.getStringExtra("locationlist");
        buslist = intent.getStringExtra("buslist");
        subwaylist = intent.getStringExtra("subwaylist");
        System.out.println(weather + " " + tem);
        drawer_input();

        nickName.setText(name); // 카카오톡 프로필 닉네임
        Glide.with(this).load(address).circleCrop().into(profile); // 카카오톡 프로필 이미지

        bundle = new Bundle();
        bundle.putString("friendlist",friendlist);
        bundle.putLong("userCode", userCode);
        bundle.putString("promiselist", promiselist);
        //System.out.println("fp에서 유저코드 확인" + userCode);
        System.out.println("FP에서 친구 리스트 확인 :" + friendlist);
        System.out.println("FP에서 약속 리스트 확인 :" + promiselist);
        fp_friend.setArguments(bundle);
        fp_promise.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fp_tab_container, fp_friend).commit();//여긴 잘됨
    }

    private void drawer_input() {
        temText.setText(tem + "C");
        areaText.setText(city);
        covidText.setText("전일 코로나 확진자 수 '" + covidNum + "' 명");

        switch (fineDust) {
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

        switch (ultraFineDust) {
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

        switch (weather) {
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

    class BackgroundTask_Friend extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/friendlist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String temp;

                StringBuilder stringBuilder = new StringBuilder();

                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("파싱 부분 : " + result);
            friendlist = result;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    class BackgroundTask_Promise extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/promisslist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String temp;

                StringBuilder stringBuilder = new StringBuilder();

                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("파싱 부분 : " + result);
            promiselist = result;

        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }

}