package com.example.bbic;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

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
                    Log.d(TAG,"invoke: nickname" + user.getKakaoAccount().getEmail());
                    // 유저의 어카운트 정보의 프로파일에 닉네임
                    Log.d(TAG,"invoke: email" + user.getKakaoAccount().getProfile().getNickname());
                    // 유저의 어카운트 파일의 성별
                    Log.d(TAG,"invoke: gerder" + user.getKakaoAccount().getGender());
                    // 유저의 어카운트 정보에 나이
                    Log.d(TAG,"invoke: age" + user.getKakaoAccount().getAgeRange());


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
