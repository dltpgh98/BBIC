package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddLocationRequest extends StringRequest{

    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/addlocation.php";
    private Map<String, String> map;

    public AddLocationRequest(String locationName, long userCode, String locationAddress, double locationLong, double locationLat, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("L_name",locationName);
        map.put("K_code", userCode + "");
        map.put("L_address", locationAddress);
        map.put("L_long", locationLong +"");
        map.put("L_lat", locationLat + "");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}