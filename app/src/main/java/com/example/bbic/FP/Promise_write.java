package com.example.bbic.FP;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bbic.DB.AddPartyRequest;
import com.example.bbic.DB.AddPromissRequest;
import com.example.bbic.Data.Friend;
import com.example.bbic.Data.Promise;
import com.example.bbic.Login_Activity;
import com.example.bbic.Maps_Activity;
import com.example.bbic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
    String promiselist = null;
    long userKakaoCode = 0;
    int friendCount = 0;
    long friendKakaoCode = 0;
    List<String> menuitem = new ArrayList<String>();
    List<Long> menucodeitem = new ArrayList<Long>();
    TextView promiseFriend;
    private String weather, tem, fineDust, ultraFineDust, covidNum, name, address, area, city, locationlist, buslist, subwaylist;
    private String newFriendlist, newPromiselist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promise_write);

        int ranpartyCode = random(min, max);

        Intent intent = getIntent();
        friendlist = intent.getStringExtra("friendlist");
        promiselist = intent.getStringExtra("promiselist");
        userKakaoCode = intent.getLongExtra("userCode", 0);
        locationlist = intent.getStringExtra("locationlist");
        buslist = intent.getStringExtra("buslist");
        subwaylist = intent.getStringExtra("subwaylist");
        area = intent.getStringExtra("???");
        city = intent.getStringExtra("???");
        weather = intent.getStringExtra("??????");
        fineDust = intent.getStringExtra("????????????");
        covidNum = intent.getStringExtra("?????????");
        ultraFineDust = intent.getStringExtra("???????????????");
        tem = intent.getStringExtra("??????");
        name = intent.getStringExtra("?????????");
        address = intent.getStringExtra("?????????");
        System.out.println("?????? ???????????? ?????? ??????" + tem);
        System.out.println("?????? ?????? ???????????? ?????? ????????? ??????" + friendlist);

        try {
            JSONObject jsonObject = new JSONObject(friendlist);
            System.out.println("?????? ?????? ?????????" + friendlist);
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

            String friendStatuslist;

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
                        menucodeitem.add(friendCode);
                    }
                }

                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        int mHour = c.get(Calendar.HOUR);
        int mMinute = c.get(Calendar.MINUTE);

        close = (TextView) findViewById(R.id.promise_write_close_tv);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("???????????? ??????");
                new BackgroundTask_Promise().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new BackgroundTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1 = new Intent(getApplicationContext(), FP.class);
                        intent1.putExtra("promiselist", newPromiselist);
                        intent1.putExtra("friendlist", newFriendlist);
                        intent1.putExtra("??????", userKakaoCode);
                        intent1.putExtra("locationlist", locationlist);
                        intent1.putExtra("buslist", buslist);
                        intent1.putExtra("subwaylist", subwaylist);
                        intent1.putExtra("???", area);
                        intent1.putExtra("???", city);
                        intent1.putExtra("??????", weather);
                        intent1.putExtra("?????????", covidNum);
                        intent1.putExtra("????????????", fineDust);
                        intent1.putExtra("???????????????", ultraFineDust);
                        intent1.putExtra("?????????", name);
                        intent1.putExtra("?????????", address);
                        intent1.putExtra("??????", tem);
                        startActivity(intent1);
                        finish();
                    }
                },1000);
            }
        });




        int num = (int) (Math.random() * 999999) + 100000;

        EditText promiseTitle = (EditText) findViewById(R.id.promise_pen_title_tv);
        TextView promiseTime = (TextView) findViewById(R.id.promise_pen_time_tv);
        TextView promiseTime2 = (TextView) findViewById(R.id.promise_pen_time_tv2);
        promiseFriend = (TextView) findViewById(R.id.promise_pen_member_tv);
        EditText promisePlace = (EditText) findViewById(R.id.promise_pen_place_tv);

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

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                promiseTime2.setText(String.format("%02d-%02d", i, i1));
            }
        }, mHour, mMinute, false);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        promiseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (promiseTime.isClickable()) {
                datePickerDialog.show();
                //}
            }
        });
        promiseTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (promiseTime.isClickable()) {
                timePickerDialog.show();
                //}
            }
        });


        popupMenu = new PopupMenu(this, friendBtn);
        for (int i = 0; i < menuitem.size(); i++) {
            popupMenu.getMenu().add(0, i, 0, menuitem.get(i).toString());
            System.out.println("?????????????????? ???:" + menuitem.size());
        }

        popupMenu.setOnMenuItemClickListener(this);
        friendCount = menuitem.size();

        Response.Listener<String> responseListener_Promise = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    System.out.println("????????? ??????" + response);
                    JSONObject jsonObject = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.Listener<String> responseListener_User = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    System.out.println("????????? ??????" + response);
                    JSONObject jsonObject = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.Listener<String> responseListener_Friend = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    System.out.println("????????? ??????" + response);
                    JSONObject jsonObject = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int partycode = ranpartyCode;
                String title = promiseTitle.getText().toString();
                String time1 = promiseTime.getText().toString();
                String time2 = promiseTime2.getText().toString();
                String place = promisePlace.getText().toString();
                String friend;
                String time = time1 + "-" + time2;

                if (promiseFriend.getText().toString().length() < 2) {
                    friend = "";
                } else {
                    friend = promiseFriend.getText().toString().substring(0, promiseFriend.length() - 2);
                }

                AddPromissRequest addPromissRequest = new AddPromissRequest(partycode, userKakaoCode, title, time, place, responseListener_Promise);
                RequestQueue queue = Volley.newRequestQueue(Promise_write.this);
                queue.add(addPromissRequest);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AddPartyRequest addPartyRequest = new AddPartyRequest(partycode, userKakaoCode, 1, responseListener_User);
                        queue.add(addPartyRequest);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String[] strArr = friend.split(", ");
                        long friendCode = 0;

                        System.out.println(strArr.length);
                        System.out.println(friendCount);

                        if (strArr.length > 0) {
                            for (int i = 0; i < strArr.length; i++) {
                                for (int j = 0; j < friendCount; j++) {
                                    if (strArr[i].equals(menuitem.get(j))) {
                                        friendCode = menucodeitem.get(j);
                                        Log.d("friendCode", Long.toString(friendCode));
                                    }
                                }
                                AddPartyRequest invitePartyRequest = new AddPartyRequest(partycode, friendCode, 0, responseListener_Friend);
                                queue.add(invitePartyRequest);
                            }
                        }
                        new BackgroundTask_Promise().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        new BackgroundTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }, 300);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1 = new Intent(getApplicationContext(), FP.class);
                        intent1.putExtra("promiselist", newPromiselist);
                        intent1.putExtra("friendlist", newFriendlist);
                        intent1.putExtra("??????", userKakaoCode);
                        intent1.putExtra("locationlist", locationlist);
                        intent1.putExtra("buslist", buslist);
                        intent1.putExtra("subwaylist", subwaylist);
                        intent1.putExtra("???", area);
                        intent1.putExtra("???", city);
                        intent1.putExtra("??????", weather);
                        intent1.putExtra("?????????", covidNum);
                        intent1.putExtra("????????????", fineDust);
                        intent1.putExtra("???????????????", ultraFineDust);
                        intent1.putExtra("?????????", name);
                        intent1.putExtra("?????????", address);
                        intent1.putExtra("??????", tem);
                        startActivity(intent1);
                        finish();
                    }
                },2000);

                /*for (int i = 0; i < friendCount; i++) {
                    if (menuitem.get(i).toString().equals(strArr[i].toString())) {
                        friendKakaoCode = menucodeitem.get(i);

                        addPartyRequest = new AddPartyRequest(partycode, friendKakaoCode, 0, responseListener_Promise);
                        RequestQueue queue2 = Volley.newRequestQueue(Promise_write.this);
                        System.out.println("??????3");
                        queue2.add(addPartyRequest);

                    }
                }
                Intent intent1 = new Intent();
*/
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
//        switch (item.getItemId()) {
//            case 0: //????????? ????????? ????????? ??????
////                if(!menuitem.get(0)){
////
////                }
//                promiseFriend.setText(menuitem.get(0).toString());
//                break;
//            case 1: //????????? ????????? ????????? ??????
//                break;
//            case 2: //????????? ????????? ????????? ??????
//                break;
//
//        }

        int selectItem = item.getItemId();
        String selectFriendName = menuitem.get(selectItem).toString();
        String beforeText = promiseFriend.getText().toString();

        boolean matchString = beforeText.contains(selectFriendName);

        if (beforeText.equals("?????? ??????")) {
            beforeText = "";
        }

        Log.d("matchString", Boolean.toString(matchString));

        if (!matchString) {
            beforeText += selectFriendName + ", ";
        } else {
            beforeText = beforeText.replace(selectFriendName + ", ", "");
        }


        promiseFriend.setText(beforeText);

        return false;
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

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
            System.out.println("========result=======" + result);
            newFriendlist = result;
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

            System.out.println("?????? ?????? : " + result);
            newPromiselist = result;

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