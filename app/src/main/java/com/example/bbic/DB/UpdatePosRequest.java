package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdatePosRequest extends StringRequest{

    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/updatepos.php";
    private Map<String, String> map;

    public UpdatePosRequest(long k_code, double k_long, double k_lat, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("K_code",k_code +"");
        map.put("K_long", k_long + "");
        map.put("K_lat",k_lat + "");
        System.out.println("업데이트 좌표 리퀘스트");
        System.out.println(k_long);
        System.out.println(k_lat);
        System.out.println(k_code);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
