package com.example.bbic.FP;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.bbic.Data.Friend;
import com.example.bbic.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Promise_write extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    TextView close;
    int max = 999999;
    int min = 100000;
    PopupMenu popupMenu;
    ImageView imageIcon;
    ImageButton friendBtn;
    String friendlist = null;
    long userKakaoCode = 0;
    int friendCount = 0;
    List<String> menuitem = new ArrayList<String>();
    TextView promiseFriend;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promise_write);

        int ranpartyCode = random(min, max);



        Intent intent = getIntent();
        friendlist = intent.getStringExtra("friendlist");
        userKakaoCode = intent.getLongExtra("userCode", 0);
        System.out.println("약속 작성 화면에서 친구 리스트 확인" + friendlist);

        try {
            JSONObject jsonObject = new JSONObject(friendlist);
            System.out.println("받은 친구 리스트" + friendlist);
            System.out.println(friendlist);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            long userCode;
            long friendCode;
            int friendStatus;
            String friendName;
            String friendEmail;
            String friendProfile;
            double friendLong;
            double friendLat;
            int friendGhost;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                userCode = object.getLong("F.K_code1");
                friendCode = object.getLong("F.K_code2");
                friendStatus = object.getInt("F.F_status");
                friendName = object.getString("K.K_name");
                friendEmail = object.getString("K.K_email");
                friendProfile = object.getString("K.K_profile");
                friendLong = object.getDouble("K.K_long");
                friendLat = object.getDouble("K.K_lat");
                friendGhost = object.getInt("K.K_ghost");

                if (userCode == userKakaoCode) {
                    if (friendStatus == 1) {
                        menuitem.add(friendName);
                    }
                }
//                friendList.add(friend); //원본용
//                userFriendlist.add(friend);//코드 검색용
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        close = (TextView) findViewById(R.id.promise_write_close_tv);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int num = (int) (Math.random() * 999999) + 100000;

        EditText promiseTitle = (EditText) findViewById(R.id.promise_pen_title_tv);
        TextView promiseTime = (TextView) findViewById(R.id.promise_pen_time_tv);
        promiseFriend = (TextView) findViewById(R.id.promise_pen_member_tv);
        EditText promisePlace = (EditText) findViewById(R.id.promise_pen_place_tv);
        EditText promiseText = (EditText) findViewById(R.id.promise_pen_txt_tv);

        imageIcon = findViewById(R.id.image_icon);
        friendBtn = findViewById(R.id.image_button);
        friendBtn.setOnClickListener(this);

        TextView saveBtn = (TextView) findViewById(R.id.schedule_pen_save_tv);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                promiseTime.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, mYear, mMonth, mDay);


        promiseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (promiseTime.isClickable()) {
                datePickerDialog.show();
                //}
            }
        });

        popupMenu = new PopupMenu(this, friendBtn);
        for (int i = 0; i < menuitem.size(); i++){
            popupMenu.getMenu().add(0, i, 0, menuitem.get(i).toString());
            System.out.println("친구팝업메뉴 수:" + menuitem.size());
        }
//        popupMenu.getMenu().add(0, 0, 0, "리스트 첫번째");
//        popupMenu.getMenu().add(0, 1, 0, "리스트 두번째");
//        popupMenu.getMenu().add(0, 2, 0, "리스트 세번째");
        popupMenu.setOnMenuItemClickListener(this);
        friendCount = menuitem.size();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int partycode = ranpartyCode;
                String title = promiseTitle.getText().toString();
                String time = promiseTime.getText().toString();


            }
        });

    }

    public static int random(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public void onClick(View v) {
        if (v == friendBtn) {
            popupMenu.show();
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case 0: //리스트 첫번째 클릭시 호출
//                if(!menuitem.get(0)){
//
//                }
                promiseFriend.setText(menuitem.get(0).toString());
                break;
            case 1: //리스트 두번째 클릭시 호출
                break;
            case 2: //리스트 세번째 클릭시 호출
                break;

        }
        return false;
    }
}