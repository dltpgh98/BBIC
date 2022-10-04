package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class deleteLocationRequest extends StringRequest{

    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/locationdelete.php";
    private Map<String, String> map;

    public deleteLocationRequest(String locationName, long K_code, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("L_name",locationName);//
        map.put("K_code", K_code + "");// 유저 코드

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}