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
    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/promissadd.php";
    private Map<String, String> map;

    public AddPromissRequest(int promiseCodse, long K_code,String p_name, String p_time, String p_address, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("P_code",promiseCodse + "");
        map.put("K_code",K_code + "");
        map.put("P_name",p_name);
        map.put("P_time",p_time);
        map.put("P_address", p_address);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}