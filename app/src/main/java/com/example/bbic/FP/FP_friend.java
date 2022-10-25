package com.example.bbic.FP;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bbic.KakaoFriend;
import com.example.bbic.NewKakaoFriend;
import com.example.bbic.R;

import com.example.bbic.test;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kakao.sdk.talk.TalkApiClient;
import com.kakao.sdk.talk.model.Friend;
import com.kakao.sdk.talk.model.Friends;
import com.kakao.sdk.talk.model.FriendsContext;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FP_friend extends Fragment {
    FP_friend_list fp_friend_list;
    FP_friend_ask fp_friend_ask;
    FloatingActionButton fab;
    Bundle bundlelist;
    Bundle bundleask;
    long userCode = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_friend, container, false);

        //TalkApiClient.getInstance().friends(null, null, null);

        fp_friend_list = new FP_friend_list(); //목록 버튼
        fp_friend_ask = new FP_friend_ask(); // 요청 버튼//벌써 여기서 문제
        fab = rootView.findViewById(R.id.fb_fab_btn);


        String friendlist = null;


        if (getArguments() != null) {
            friendlist = getArguments().getString("friendlist");
            userCode = getArguments().getLong("userCode");
            System.out.println("FP_friend에서 받은 friendllist 확인 : " + friendlist);
            System.out.println("프렌드에서 유저의 카카오코드 확인" + userCode);
        }//여기까지도 잘됨


        bundlelist = new Bundle();
        bundlelist.putLong("userCode", userCode);
        bundlelist.putString("friendlist", friendlist);
        fp_friend_list.setArguments(bundlelist);
        fp_friend_ask.setArguments(bundlelist);
        getChildFragmentManager().beginTransaction().replace(R.id.fp_container, fp_friend_list).commit();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(getActivity(), NewKakaoFriend.class);
//                startActivity(intent);


                test test = new test();
                test.test1();
                System.out.println("getFriendList1 : " + test.getFriendList());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<KakaoFriend> kakaoFriend = test.getFriendList();

//                        for (int i = 0;; i++) {
//                            System.out.println("루프 : " + i);
//                            if (kakaoFriend != null) {
//                                System.out.println("루프 : " + i);
//                                break;
//                            }
//
//                        }


                        System.out.println("getFriendList2 : " + test.getFriendList());
                        ArrayList<KakaoFriend> kakaoFriendArrayList = new ArrayList<KakaoFriend>();
                        kakaoFriendArrayList = (ArrayList<KakaoFriend>) kakaoFriend;

                        System.out.println("리스트 출력" + kakaoFriend.toString());
                        int arrint = kakaoFriend.size();

                        if (kakaoFriend.isEmpty()) {
                            Log.d("코틀린 리턴값", "Empty");
                        } else {
                            Log.d("코틀린 리턴값", "Size = " + kakaoFriend.size());
                        }


                        ArrayList<KakaoFriend> finalKakaoFriendArrayList = kakaoFriendArrayList;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                System.out.println("getFriendList3 : " + test.getFriendList());
                                Intent intent = new Intent(getContext(), NewKakaoFriend.class);
                                intent.putExtra("key", finalKakaoFriendArrayList);
                                intent.putExtra("usercode", userCode);
                                startActivity(intent);
                            }
                        },900);





                    }
                }, 2000);


                //KakaoFriend[] kakaoFriend = Arrays.copyOf(test.test1(), test.test1().length);

//                test.getArray();
//
//                System.out.println("가져온 카카오톡 친구목록" + Arrays.toString(test.getArray()));

                new Thread() {
                    @Override
                    public void run() {
                        Document doc;
                        try {
                            Thread.sleep(4000);


                            //System.out.println("카카오톡 친구 목록 확인하기" + test.test1().toString());
                            //System.out.println("카카오톡 친구 목록 확인하기" + test.test1().length);


                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                    }
                }.start();


//                RequestQueue queue = Volley.newRequestQueue(rootView.getContext());
//                String url = "https://kapi.kakao.com/v1/api/talk/friends";
//                StringRequest getRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // response
//                                Log.d("Response", response);
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // TODO Auto-generated method stub
//                                Log.d("ERROR", "error => " + error.toString());
//                            }
//                        }
//                ) {
//                    @Override
//                    publ
//                    ic String getBodyContentType() {
//                        return "application/json; charset=utf-8";
//                    }
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("Authorization","Bearer " + "fed0a3b742a6c362284f565bbe4eb6c2");
//                        //params.put("Accept-Language", "fr");
//
//                        return params;
//                    }
//                };
//                queue.add(getRequest);
            }
        });


        Button btn = rootView.findViewById(R.id.fp_leftTab_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fp_friend_list == null){
                    getChildFragmentManager().beginTransaction().replace(R.id.fp_container, fp_friend_list).commit();
                    getChildFragmentManager().beginTransaction().hide(fp_friend_ask).commit();
                }
                else {
                    getChildFragmentManager().beginTransaction().show(fp_friend_list).commit();
                    getChildFragmentManager().beginTransaction().hide(fp_friend_ask).commit();
                }

            }
        });

        btn = rootView.findViewById(R.id.fp_rightTap_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fp_friend_ask == null){
                    getChildFragmentManager().beginTransaction().replace(R.id.fp_container, fp_friend_ask).commit();
                    getChildFragmentManager().beginTransaction().hide(fp_friend_list).commit();
                }else {
                    getChildFragmentManager().beginTransaction().show(fp_friend_ask).commit();
                    getChildFragmentManager().beginTransaction().hide(fp_friend_list).commit();
                }

            }
        });


        return rootView;
    }
}