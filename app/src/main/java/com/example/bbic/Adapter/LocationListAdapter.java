package com.example.bbic.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bbic.DB.deleteAskFriendRequest;
import com.example.bbic.DB.deleteLocationRequest;
import com.example.bbic.Data.Friend;
import com.example.bbic.Data.Location;
import com.example.bbic.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class LocationListAdapter extends BaseAdapter {

private Context context;
private List<Location> location;

    public LocationListAdapter(Context context, List<Location> location) {
        this.context = context;
        this.location = location;
    }

    @Override
    public int getCount() {
        return location.size();
    }

    @Override
    public Object getItem(int i) {
        return location.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.bookmark_place_list, null);


        TextView locationName = (TextView)v.findViewById(R.id.place_name);
        TextView locationAddress = (TextView) v.findViewById(R.id.place_address);
        ImageView menuBtn = (ImageView) v.findViewById(R.id.place_setting_iv);

        locationName.setText(location.get(i).getLocationName());
        locationAddress.setText(location.get(i).getLocationAddress());

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, menuBtn);
                popupMenu.getMenu().add(0, 0, 0, "삭제");
//                popupMenu.getMenu().add(1, 1, 1, "길찾기");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("장소삭제").setMessage("정말 삭제하시겠습니까?");
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
                                                location.remove(i);
                                                notifyDataSetChanged();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                location.remove(i);
                                notifyDataSetChanged();
                                deleteLocationRequest deleteLocationRequest = new deleteLocationRequest(location.get(i).getLocationName(), location.get(i).getUserCode(), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(v.getContext());
                                queue.add(deleteLocationRequest);
                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


        return v;
    }
}
