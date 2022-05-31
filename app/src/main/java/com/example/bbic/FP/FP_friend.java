package com.example.bbic.FP;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.bbic.R;

import com.example.bbic.test;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kakao.sdk.talk.TalkApiClient;
import com.kakao.sdk.talk.model.Friend;
import com.kakao.sdk.talk.model.Friends;
import com.kakao.sdk.talk.model.FriendsContext;

import org.w3c.dom.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FP_friend extends Fragment {
    FP_friend_list fp_friend_list;
    FP_friend_ask fp_friend_ask;
    FloatingActionButton fab;
    Bundle bundlelist;
    Bundle bundleask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_friend, container, false);

        //TalkApiClient.getInstance().friends(null, null, null);

        fp_friend_list = new FP_friend_list(); //목록 버튼
        fp_friend_ask = new FP_friend_ask(); // 요청 버튼//벌써 여기서 문제
        fab = rootView.findViewById(R.id.fb_fab_btn);

        String friendlist = "";
        long userCode = 0;

        if(getArguments() != null){
            friendlist = getArguments().getString("friendlist");
            userCode = getArguments().getLong("userCode");
            System.out.println("FP_friend에서 받은 friendllist 확인 : " + friendlist);
            System.out.println("프렌드에서 유저의 카카오코드 확인" + userCode);
        }//여기까지도 잘됨

        bundlelist = new Bundle();
        bundlelist.putString("friendlist", friendlist);
        bundlelist.putLong("userCode", userCode);
        FragmentTransaction transactionlist =getActivity().getSupportFragmentManager().beginTransaction();
        fp_friend_list.setArguments(bundlelist);
        fp_friend_ask.setArguments(bundlelist);
        transactionlist.replace(R.id.fp_container, fp_friend_list);
        transactionlist.commit();//


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test test = new test();
                test.test1();


//                test.getArray();
//
//                System.out.println("가져온 카카오톡 친구목록" + Arrays.toString(test.getArray()));

                new Thread() {
                    @Override
                    public void run() {
                        Document doc;
                        try {
                            Thread.sleep(2000);
                            test.getArray();
                            System.out.println("가져온 카카오톡 친구목록" + Arrays.toString(test.getArray()));
                            System.out.println("가져온 카카오톡 친구목록" + test.getArray()[0].getFriend_id());

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


        getChildFragmentManager().beginTransaction().replace(R.id.fp_container, fp_friend_list).commit();

        Button btn = rootView.findViewById(R.id.fp_leftTab_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.fp_container, fp_friend_list).commit();
            }
        });

        btn = rootView.findViewById(R.id.fp_rightTap_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.fp_container, fp_friend_ask).commit();
            }
        });


        return rootView;
    }
}