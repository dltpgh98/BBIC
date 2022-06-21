package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class AddPartyRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/party.php";
    private Map<String, String> map;

    public AddPartyRequest(int p_code, long k_code, int p_status, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("p_code",p_code + "");
        map.put("k_code",k_code + "");
        map.put("p_status", p_status + "");

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}