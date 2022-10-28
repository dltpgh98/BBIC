package com.example.bbic;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.bbic.Adapter.ViewPager_Item_Adapter;
import com.example.bbic.Bookmark.Bookmark;
import com.example.bbic.FP.FP;
//import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

public class Setting_Activity extends AppCompatActivity {


    //버튼 클릭 리스너 클래스
    class BtnOnClickListener implements View.OnClickListener{
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view){
            switch (view.getId()){
                //case를 통해 id에 따른 클릭이벤트 실행
                case R.id.menu_ibtn:
                    drawerLayout.openDrawer(drawerView);
                    break;
                case R.id.drawer_menu_1:
                    Log.d("클릭", "onClick: ");
                    Intent intent1 = new Intent(getApplicationContext(), Maps_Activity.class);
//                    intent1.putExtra("코드",k_code);
//                    intent1.putExtra("닉네임", name);
//                    intent1.putExtra("프로필", address);
//                    intent1.putExtra("미세먼지", fineDust);
//                    intent1.putExtra("초미세먼지", ultraFineDust);
//                    intent1.putExtra("온도", tem);
//                    intent1.putExtra("날씨", weather);
//                    intent1.putExtra("도", area);
//                    intent1.putExtra("시", city);
//                    intent1.putExtra("코로나",covidNum);
//                    intent1.putExtra("friendlist",friendlist);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.drawer_menu_2:
                    Intent intent2 = new Intent(getApplicationContext(), Subway.class);
                    intent2.putExtra("코드",k_code);
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
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.drawer_menu_3:
                    System.out.println("click");
                    Intent intent3 = new Intent(getApplicationContext(), Bookmark.class);
                    intent3.putExtra("코드",k_code);
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
                    Intent intent5 = new Intent(getApplicationContext(), FP.class);
                    intent5.putExtra("코드",k_code);
                    intent5.putExtra("닉네임", name);
                    intent5.putExtra("프로필", address);
                    intent5.putExtra("미세먼지", fineDust);
                    intent5.putExtra("초미세먼지", ultraFineDust);
                    intent5.putExtra("온도", tem);
                    intent5.putExtra("날씨", weather);
                    intent5.putExtra("도", area);
                    intent5.putExtra("시", city);
                    intent5.putExtra("코로나",covidNum);
                    intent5.putExtra("friendlist",friendlist);
                    startActivity(intent5);
                    finish();
                    break;
                case R.id.drawer_menu_6:
                    drawerLayout.closeDrawer(drawerView);
                    break;
                case R.id.home_btn:
                    Intent intent = new Intent(getApplicationContext(), Maps_Activity.class);
//
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.setting_personal_btn:
                    startPersonalDialog();
                    break;

//                case R.id.setting_terms_button:
//                    startTermsDialog();
//                    break;
//
//                case R.id.setting_spons_btn:
//                    startSponsDialog();
//                    break;
//
//                case R.id.setting_help_btn:
//                    startHelpDialog();
//                    break;
            }
        }
    }

    //참조를 위한 각 객체 생성
    private DrawerLayout drawerLayout;
    private View drawerView;
    private ImageButton menuIbtn, homeBtn, personalBtn, helpBtn, sponsBtn, termsBtn;
    private TextView
            temText, fineText, ultraText, covidText, nickName, areaText;
    private ImageView weatherImage, profile;

    private Button[] drawerMenu = new Button[6];

    private final String temURL = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=경기도부천시날씨"; //웹크롤링 할 주소(1)
    private final String covidURL = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&qvt=0&query=코로나19"; //웹크롤링 할 주소(2)
    private String weather, tem, fineDust, ultraFineDust, covidNum, name, address, area, city,friendlist;
    private long k_code;

    private ImageButton diaBackBtn;
    private Dialog dialog;
    private TextView diaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

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
        profile = (ImageView)findViewById(R.id.drawer_profile_img); // 카카오톡 프로파일 이미지
        nickName = (TextView)findViewById(R.id.drawer_profile_name); // 카카오톡 닉네임
        areaText = (TextView) findViewById(R.id.drawer_area_text);

        homeBtn = (ImageButton) findViewById(R.id.home_btn);
        personalBtn = (ImageButton) findViewById(R.id.setting_personal_btn);
//        helpBtn =  (ImageButton) findViewById(R.id.setting_help_btn);
//        termsBtn = (ImageButton) findViewById(R.id.setting_terms_button);
//        sponsBtn = (ImageButton) findViewById(R.id.setting_spons_btn);

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

        homeBtn.setOnClickListener(onClickListener);
        personalBtn.setOnClickListener(onClickListener);
//        helpBtn.setOnClickListener(onClickListener);
//        termsBtn.setOnClickListener(onClickListener);
//        sponsBtn.setOnClickListener(onClickListener);

        Intent intent = getIntent();
        k_code = intent.getLongExtra("코드",0);
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
        drawer_input();

        nickName.setText(name); // 카카오톡 프로필 닉네임
        Glide.with(this).load(address).circleCrop().into(profile); // 카카오톡 프로필 이미지
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

    private void startPersonalDialog(){

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.personal_processing);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams)layoutParams);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        diaBackBtn = dialog.findViewById(R.id.personal_back_btn);
        diaBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        diaText = dialog.findViewById(R.id.personal_text);
        diaText.setMovementMethod(new ScrollingMovementMethod());

        dialog.show();
    }

    private void startTermsDialog(){

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.terms_conditions);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams)layoutParams);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        diaBackBtn = dialog.findViewById(R.id.terms_back_btn);
        diaBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        diaText = dialog.findViewById(R.id.terms_text);
        diaText.setMovementMethod(new ScrollingMovementMethod());

        dialog.show();
    }

    private void startSponsDialog(){

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.spons);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = 500;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams)layoutParams);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        diaBackBtn = dialog.findViewById(R.id.spons_back_btn);
        diaBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void startHelpDialog(){

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.help);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams)layoutParams);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        diaBackBtn = dialog.findViewById(R.id.help_back_btn);
        diaBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        diaText = dialog.findViewById(R.id.help_text);
        diaText.setMovementMethod(new ScrollingMovementMethod());

        dialog.show();
    }
}