package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdatePosRequest extends StringRequest{

    final static private String URL = "http://3.85.238.108/updatepos.php";
    private Map<String, String> map;

    public UpdatePosRequest(long k_code, double k_long, double k_lat, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("k_code",k_code +"");
        map.put("k_long", k_long + "");
        map.put("k_lat",k_lat + "");

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
