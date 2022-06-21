package com.example.bbic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bbic.DB.KakaoRequest;
import com.example.bbic.DB.UpdateRequest;
import com.example.bbic.DB.ValidateRequest;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Login_Activity extends AppCompatActivity {
    private Button login_btn, logout_btn;
    private TextView nickname;
    private ImageView profileImage;
    private static final String TAG = "Login_Activity";
    private String str,promiselist, userlist;
    private int ghost = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login_btn = (Button) findViewById(R.id.login_btn);
        logout_btn = (Button) findViewById(R.id.logout);
        nickname = (TextView) findViewById(R.id.nickname);
        profileImage = (ImageView) findViewById(R.id.profile);

        new BackgroundTask().execute();//파싱
        new BackgroundTask_Promise().execute();
        new BackgroundTask_User().execute();

        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if (oAuthToken != null) {
                    Log.i("user", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
                }
                if (throwable != null) {
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                updateKakaoLoginUi();
                return null;
            }
        };


        //로그인
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(Login_Activity.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(Login_Activity.this, callback);
                }else {
                    UserApiClient.getInstance().loginWithKakaoAccount(Login_Activity.this, callback);
                }
            }
        });
        //로그아웃
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                    @Override
                    public Unit invoke(User user, Throwable throwable) {
                        updateKakaoLoginUi();
                        return null;
                    }
                });
            }
        });
        updateKakaoLoginUi();

    }

    private  void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 로그인이 되어있으면
                if (user!=null){

                    // 유저의 아이디
                    Log.d(TAG,"invoke: id " + user.getId());
                    // 유저의 어카운트정보에 이메일
                    Log.d(TAG,"invoke: nickname " + user.getKakaoAccount().getProfile().getNickname());
                    // 유저의 어카운트 정보의 프로파일에 닉네임
                    Log.d(TAG,"invoke: email " + user.getKakaoAccount().getEmail());
                    // 유저의 어카운트 파일의 성별
                    Log.d(TAG,"invoke: gerder " + user.getKakaoAccount().getGender());
                    // 유저의 어카운트 정보에 나이
                    Log.d(TAG,"invoke: age " + user.getKakaoAccount().getAgeRange());

                    long k_code = user.getId();
                    String k_name = user.getKakaoAccount().getProfile().getNickname();
                    String k_email = user.getKakaoAccount().getEmail();

                    if(k_email == null){
                        k_email = "";
                    }
                    String k_profile = user.getKakaoAccount().getProfile().getProfileImageUrl();
                    Validate(k_code, k_name, k_email, k_profile, 0.0, 0.0, ghost);

                    login_btn.setVisibility(View.GONE);
                    //logout_btn.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(getApplicationContext(), Maps_Activity.class);
                    intent.putExtra("닉네임",user.getKakaoAccount().getProfile().getNickname());
                    intent.putExtra("코드", user.getId());
                    intent.putExtra("프로필", user.getKakaoAccount().getProfile().getProfileImageUrl());
                    intent.putExtra("friendlist", str);// 친구목록 배열 전달
                    intent.putExtra("promiselist", promiselist); //약속 리스트 전달
                    System.out.println("로그인 액티비티 친구목록: " + str);
                    intent.putExtra("userlist", userlist); //약속 리스트 전달
                    startActivity(intent);
                    //finish();

                }else {
                    // 로그인이 되어 있지 않다면 위와 반대로
                    nickname.setText(null);
                    profileImage.setImageBitmap(null);
                    login_btn.setVisibility(View.VISIBLE);
                    //logout_btn.setVisibility(View.GONE);
                }
                return null;
            }
        });
    }

    void Update(Long k_code, String k_name, String k_email, String k_profile){

        Response.Listener<String> responseListener2 = new Response.Listener<String>() {// ************회원가입********************
            @Override
            public void onResponse(String response) {

                try {
                    System.out.println("업데이트" + response);
                    JSONObject jsonObject = new JSONObject( response );
                    boolean success = jsonObject.getBoolean( "success" );
                    //boolean success = Boolean.parseBoolean(response);
                    System.out.println(success);
                    //업데이트 성공시
                    if (success) {
                        System.out.println("업데이트 성공");
                        //업데이트 실패시
                    } else {
                        System.out.println("업데이트 실패");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        UpdateRequest updateRequest = new UpdateRequest(k_code, k_name, k_email, k_profile, responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue( Login_Activity.this );
        queue2.add(updateRequest);
    }

    void Validate(Long k_code, String k_name, String K_email, String K_profile, double K_long, double K_lat, int K_ghost){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("코드 중복체크");
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        System.out.println("테이블 이미 존재함");
                        Update(k_code, k_name, K_email, K_profile);
                    }
                    else {
                        System.out.println("존재하지 않음 테이블 생성해야함");
                        MakeTable(k_code, k_name, K_email, K_profile, K_long, K_lat, K_ghost);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ValidateRequest validateRequest = new ValidateRequest(k_code, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Login_Activity.this);
        queue.add(validateRequest);
    }

    void MakeTable(Long k_code, String k_name, String k_email, String k_profile, double K_long, double K_lat, int K_ghost){

        Response.Listener<String> responseListener1 = new Response.Listener<String>() {// ************회원가입********************
            @Override
            public void onResponse(String response) {

                try {
                    System.out.println("테이블 생성" + response);
                    JSONObject jsonObject = new JSONObject( response );
                    boolean success = jsonObject.getBoolean( "success" );
                    //회원가입 성공시

                    if (success) {
                        System.out.println("회원가입 성공");
                        //회원가입 실패시
                    } else {
                        System.out.println("회원가입 실패");
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //ghost = 0 기본(공개)
        KakaoRequest kakaoRequest = new KakaoRequest( k_code, k_name, k_email, k_profile, K_long, K_lat, K_ghost,responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue( Login_Activity.this );
        queue1.add(kakaoRequest);
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            target = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/friendlist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try{
                URL url = new URL(target);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;

                StringBuilder stringBuilder = new StringBuilder();

                while((temp = bufferedReader.readLine()) != null){

                    stringBuilder.append(temp + "\n");

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
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
            System.out.println("========result======="+result);
            str = result;
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

    class BackgroundTask_User extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            target = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/userlist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try{
                URL url = new URL(target);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;

                StringBuilder stringBuilder = new StringBuilder();

                while((temp = bufferedReader.readLine()) != null){

                    stringBuilder.append(temp + "\n");

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
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
            System.out.println("========result======="+result);
            userlist = result;
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