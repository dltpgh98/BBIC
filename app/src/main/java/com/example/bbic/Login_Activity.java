package com.example.bbic;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bbic.DB.LogInRequest;
import com.example.bbic.DB.UpdateRequest;
import com.example.bbic.DB.ValidateRequest;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Login_Activity extends AppCompatActivity {
    private Button login_btn, logout_btn;
    private TextView nickname;
    private ImageView profileImage;
    private static final String TAG = "Login_Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login_btn = (Button) findViewById(R.id.login_btn);
        logout_btn = (Button) findViewById(R.id.logout);
        nickname = (TextView) findViewById(R.id.nickname);
        profileImage = (ImageView) findViewById(R.id.profile);

        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if (oAuthToken != null) {

                }
                if (throwable != null) {

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
                    Log.d(TAG,"invoke: id" + user.getId());
                    // 유저의 어카운트정보에 이메일
                    Log.d(TAG,"invoke: nickname" + user.getKakaoAccount().getProfile().getNickname());
                    // 유저의 어카운트 정보의 프로파일에 닉네임
                    Log.d(TAG,"invoke: email" + user.getKakaoAccount().getEmail());
                    // 유저의 어카운트 파일의 성별
                    Log.d(TAG,"invoke: gerder" + user.getKakaoAccount().getGender());
                    // 유저의 어카운트 정보에 나이
                    Log.d(TAG,"invoke: age" + user.getKakaoAccount().getAgeRange());

                    long k_code = user.getId();
                    String k_name = user.getKakaoAccount().getProfile().getNickname();
                    String k_email = user.getKakaoAccount().getEmail();
                    String k_profile = user.getKakaoAccount().getProfile().getProfileImageUrl();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                System.out.println("아이디 중복체크");
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    //이미 존재하지 않음 테이블 생성해야함
                                    Response.Listener<String> responseListener1 = new Response.Listener<String>() {// ************회원가입********************
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                System.out.println("아무이름" + response);
                                                JSONObject jsonObject = new JSONObject( response );
                                                boolean success = jsonObject.getBoolean( "success" );
                                                //회원가입 성공시

                                                if (success) {

                                                    //회원가입 실패시
                                                } else {

                                                }
                                            }
                                            catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    LogInRequest logInRequest = new LogInRequest( k_code, k_name, k_email, k_profile, responseListener1);
                                    RequestQueue queue1 = Volley.newRequestQueue( Login_Activity.this );
                                    queue1.add(logInRequest);
                                }
                                else {
                                    //이미 존재하는 테이블 업데이트 필요
                                    Response.Listener<String> responseListener2 = new Response.Listener<String>() {// ************회원가입********************
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                System.out.println("아무이름" + response);
                                                JSONObject jsonObject = new JSONObject( response );
                                                boolean success = jsonObject.getBoolean( "success" );
                                                //업데이트 성공시
                                                if (success) {

                                                    //업데이트 실패시
                                                } else {

                                                }
                                            }
                                            catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    UpdateRequest updateRequest = new UpdateRequest(k_code, k_name, k_email, k_profile, responseListener2);
                                    RequestQueue queue2 = Volley.newRequestQueue( Login_Activity.this );
                                    queue2.add(updateRequest);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ValidateRequest validateRequest = new ValidateRequest(k_code, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Login_Activity.this);
                    queue.add(validateRequest);






//                    nickname.setText(user.getKakaoAccount().getProfile().getNickname());
//
//                    Glide.with(profileImage).load(user.getKakaoAccount().
//                            getProfile().getProfileImageUrl()).circleCrop().into(profileImage);
                    login_btn.setVisibility(View.GONE);
                    //logout_btn.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(getApplicationContext(), Maps_Activity.class);
                    intent.putExtra("닉네임",user.getKakaoAccount().getProfile().getNickname());
                    intent.putExtra("프로필", user.getKakaoAccount().getProfile().getProfileImageUrl());
                    startActivity(intent);
                    finish();

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
}
