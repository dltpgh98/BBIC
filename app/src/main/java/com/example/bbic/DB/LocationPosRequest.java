package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LocationPosRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://3.85.238.108/locationpos.php";
    private Map<String, String> map;

    public LocationPosRequest(long k_code, int c_code, String l_name, String l_address, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("k_code",k_code + "");
        map.put("c_code",c_code + "");
        map.put("l_name", l_name);
        map.put("l_address", l_address);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}