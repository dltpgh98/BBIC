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
import com.example.bbic.DB.deleteSubwayRequest;
import com.example.bbic.Data.Subway;
import com.example.bbic.R;

import org.json.JSONObject;

import java.util.List;

public class SubwayListAdapter extends BaseAdapter {

    private Context context;
    private List<Subway> subwayList;

    public SubwayListAdapter(Context context, List<Subway> subwayList) {
        this.context = context;
        this.subwayList = subwayList;
    }

    @Override
    public int getCount() {
        return subwayList.size();
    }

    @Override
    public Object getItem(int i) {
        return subwayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.bookmark_transit_subway_list, null);


        TextView subwayName = (TextView) v.findViewById(R.id.subway_this_station_name_tv);
        ImageButton deleteSubBook = (ImageButton) v.findViewById(R.id.subway_bookmark_ibtn);
        subwayName.setText(subwayList.get(i).getStationName());

        deleteSubBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuild = new AlertDialog.Builder(context);
                dialogBuild.setTitle("지하철 삭제").setMessage("정말 삭제하시겠습니까?");
                dialogBuild.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success){
                                        subwayList.remove(i);
                                        notifyDataSetChanged();
                                    }
                                } catch (Exception e) {
                                    subwayList.remove(i);
                                    notifyDataSetChanged();
                                    e.printStackTrace();
                                }
                            }
                        };
                        deleteSubwayRequest deleteSubwayRequest = new deleteSubwayRequest(subwayList.get(i).getStationCode(), subwayList.get(i).getUserCode(), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(v.getContext());
                        queue.add(deleteSubwayRequest);

                    }
                });
                dialogBuild.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = dialogBuild.create();
                alertDialog.show();
            }
        });



        return v;
    }
}