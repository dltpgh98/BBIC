package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddBusRequest extends StringRequest{

    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/addbus.php";
    private Map<String, String> map;

    public AddBusRequest(int b_buskey, int b_stationcode, long k_code, String b_num, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("B_buskey",b_buskey +"");
        map.put("B_stationcode",b_stationcode + "");
        map.put("K_code",k_code + "");
        map.put("B_num", b_num);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
