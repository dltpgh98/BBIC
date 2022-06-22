package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddSubwayRequest extends StringRequest{

    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/addsubway.php";
    private Map<String, String> map;

    public AddSubwayRequest(int s_code, long k_code, String s_stationname, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("S_code",s_code +"");
        map.put("K_code", k_code + "");
        map.put("S_stationname", s_stationname);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
