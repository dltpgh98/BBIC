package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.jsoup.Connection;

import java.util.HashMap;
import java.util.Map;

public class KakaoRequest extends StringRequest{

    final static private String URL = "http://3.85.238.108/login.php";
    private Map<String, String> map;

    public KakaoRequest(long k_code, String k_name, String k_email, String k_profile, double k_long, double k_lat, int k_ghost, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("k_code",k_code +"");
        map.put("k_name", k_name);
        map.put("k_email",k_email);
        map.put("k_profile", k_profile);
        map.put("k_long", k_long + "");
        map.put("k_lat", k_lat + "");
        map.put("k_ghost", k_ghost + "");

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
