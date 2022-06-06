package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddBusStationRequest extends StringRequest{

    final static private String URL = "http://3.85.238.108/busstation.php";
    private Map<String, String> map;

    public AddBusStationRequest(int b_stationcode, String b_stationname, String b_direction, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("b_stationcode",b_stationcode +"");
        map.put("b_stationname", b_stationname);
        map.put("b_direction", b_direction);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
