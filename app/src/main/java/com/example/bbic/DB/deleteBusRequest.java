package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class deleteBusRequest extends StringRequest{

    final static private String URL = "http://3.85.238.108/deletebus.php";
    private Map<String, String> map;

    public deleteBusRequest(int b_stationcode, int b_buskey, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("B_stationcode",b_stationcode +"");
        map.put("B_buskey",b_buskey +"");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
