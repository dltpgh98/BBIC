package com.example.bbic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kakao.sdk.talk.TalkApiClient;
import com.kakao.sdk.talk.model.FriendOrder;
import com.kakao.sdk.talk.model.Order;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import kotlin.jvm.functions.Function2;


public class KakaoFriend_add extends AppCompatActivity {

    TextView close;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promise_write);


        close = (TextView) findViewById(R.id.promise_write_close_tv);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fb_fab_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //requestWithSomeHttpHeaders();
            }
        });

        //public void requestWithSomeHttpHeaders() {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://kapi.kakao.com/v1/api/talk/friends";
            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("ERROR", "error => " + error.toString());
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "fed0a3b742a6c362284f565bbe4eb6c2");
                    //params.put("Accept-Language", "fr");

                    return params;
                }
            };
            queue.add(getRequest);


        //}
    }
}