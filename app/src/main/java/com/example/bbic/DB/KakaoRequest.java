package com.example.bbic.DB;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.toolbox.StringRequest;

import org.jsoup.Connection;

import java.util.HashMap;
import java.util.Map;

public class KakaoRequest extends StringRequest{

    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/register.php";
    private Map<String, String> map;

    public KakaoRequest(long k_code, String k_name, String k_email, String k_profile, double K_long, double K_lat, int k_ghost, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);


        K_long = 0.111;
        K_lat = 0.111;
        k_ghost = 0;
        System.out.println("로그인 리퀘스트에서 유저 이름" + k_name);
        map = new HashMap<>();
        map.put("K_code",k_code +"");
        map.put("K_name", k_name);
        map.put("K_email",k_email);
        map.put("K_profile", k_profile);
        map.put("K_long", K_long + "");
        map.put("K_lat", K_lat +"");
        map.put("K_ghost", k_ghost + "");

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}