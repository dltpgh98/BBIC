package com.example.bbic.DB;

import android.media.TimedMetaData;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class AddPromissRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://3.85.238.108/addpromiss.php";
    private Map<String, String> map;

    public AddPromissRequest(String p_name, String p_time, String p_text, int c_code, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("p_name",p_name);
        map.put("p_time",p_time);
        map.put("p_text", p_text);
        map.put("c_code", c_code + "");

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}