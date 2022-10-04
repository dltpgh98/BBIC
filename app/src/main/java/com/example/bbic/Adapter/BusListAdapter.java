package com.example.bbic.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bbic.DB.deleteBusRequest;
import com.example.bbic.DB.deleteBusStationRequest;
import com.example.bbic.DB.deleteLocationRequest;
import com.example.bbic.Data.Bus;
import com.example.bbic.Data.Subway;
import com.example.bbic.R;

import org.json.JSONObject;

import java.util.List;

public class BusListAdapter extends BaseAdapter {

    private Context context;
    private List<Bus> busList;

    public BusListAdapter(Context context, List<Bus> busList) {
        this.context = context;
        this.busList = busList;
    }

    @Override
    public int getCount() {
        return busList.size();
    }

    @Override
    public Object getItem(int i) {
        return busList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.bookmark_transit_bus_list, null);


        TextView busStationName = (TextView) v.findViewById(R.id.bus_station_name_tv);
        TextView busStationDirection = (TextView) v.findViewById(R.id.bus_to_station_name_tv);
        TextView busNum = (TextView) v.findViewById(R.id.bus1_num_tv);
        ImageButton deleteBook = (ImageButton) v.findViewById(R.id.bus_bookmark_ibtn);


        busStationName.setText(String.valueOf(busList.get(i).getBusStationName()));
        busStationDirection.setText(busList.get(i).getBusDirction());
        busNum.setText(String.valueOf(busList.get(i).getBusNum()));

        deleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("버스 삭제").setMessage("정말 삭제하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success){
                                        busList.remove(i);
                                        notifyDataSetChanged();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        busList.remove(i);
                        notifyDataSetChanged();
//                        deleteBusStationRequest deleteBusStationRequest = new deleteBusStationRequest(busList.get(i).getBusStationName(), busList.get(i).getUserCode(), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(v.getContext());
//                        queue.add(deleteBusStationRequest);

                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
//                return false;
            }
        });

        return v;
    }
}