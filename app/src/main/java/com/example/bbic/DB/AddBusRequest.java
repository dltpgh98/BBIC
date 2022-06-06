package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddBusRequest extends StringRequest{

    final static private String URL = "http://3.85.238.108/bus.php";
    private Map<String, String> map;

    public AddBusRequest(int b_buskey, int b_stationcode, long k_code, String b_num, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("k_code",k_code +"");
        map.put("b_buskey", b_buskey + "");
        map.put("b_stationcode",b_stationcode + "");
        map.put("b_num", b_num);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
