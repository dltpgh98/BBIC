package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddBusStationRequest extends StringRequest{

    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/addbusstation.php";
    private Map<String, String> map;

    public AddBusStationRequest(int b_stationcode, String b_stationname, String b_direction, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("B_stationcode",b_stationcode +"");
        map.put("B_stationname", b_stationname);
        map.put("B_direction", b_direction);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}