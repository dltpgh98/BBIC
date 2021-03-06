package com.example.bbic.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bbic.DB.deletePromissRequest;
import com.example.bbic.Data.Promise;
import com.example.bbic.FP.FP_promise_list;
import com.example.bbic.FP.FP_promise_list;
import com.example.bbic.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PromissListAdapter extends BaseAdapter {

    private Context context;
    private List<Promise> promises;
    private List<Promise> userPromises;
    private Fragment parentActivity;
    private long userCode;
    List<String> menuitem = new ArrayList<String>();


    public PromissListAdapter(Context context, List<Promise> promises, List<Promise> userPromises, long userCode, FP_promise_list parentActivity) {
        this.context = context;
        this.promises = promises;
        this.userPromises = userPromises;
        //this.userFriendsStatus = userFriendsStatus;
        this.userCode = userCode;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return promises.size();
    }

    @Override
    public Object getItem(int i) {
        return promises.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

//        if (view == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.fp_friend_list_list, viewGroup, false);
//        }

        View v = View.inflate(context, R.layout.fp_promise_list_list, null);

        //?????? ?????? ????????????
        ImageView profileImage1 = (ImageView) v.findViewById(R.id.pro_list_profile1_iv);
        ImageView profileImage2 = (ImageView) v.findViewById(R.id.pro_list_profile2_iv);
        ImageView profileImage3 = (ImageView) v.findViewById(R.id.pro_list_profile3_iv);
        ImageView deleteBtn = (ImageView) v.findViewById(R.id.pro_ask_setting_iv);

        TextView promiseTitle = (TextView) v.findViewById(R.id.pro_list_title_tv);//?????? ??????
        TextView promiseAddress = (TextView) v.findViewById(R.id.pro_list_address_tv);//?????? ??????
        TextView promiseDate = (TextView) v.findViewById(R.id.pro_list_settime_tv);// ?????? ??????

        promiseTitle.setText(promises.get(i).getPartyName());
        System.out.println("?????? ????????? ??????" + promises.get(i).getPartyName());
        promiseAddress.setText(promises.get(i).getPromiseAddress());
        promiseDate.setText(promises.get(i).getPromissTime());

        int partycode = promises.get(i).getPartyCode();

        String friendProfileArr = promises.get(i).getFriendProfile();//?????? ????????? ??????


        String[] friendProfile = new String[5];

        String[] strArr = friendProfileArr.split(",");
        for (int j = 0; j < strArr.length; j++) {
            friendProfile[j] = strArr[j];

            if (j == 0) {
                if (friendProfile[j] == null) {
                    profileImage2.setVisibility(view.GONE);
                }
                Glide.with(context).load(friendProfile[j]).circleCrop().into(profileImage1);
            }
            if (j == 1) {
                if (friendProfile[j] == null) {
                    profileImage2.setVisibility(view.GONE);
                }
                Glide.with(context).load(friendProfile[j]).circleCrop().into(profileImage2);
            }
            if (j == 2) {
                if (friendProfile[j] == null) {
                    System.out.println("????????? ????????? ?????? ????????? ?????? : " + friendProfile[j]);
                    profileImage2.setVisibility(view.INVISIBLE);
                }
                Glide.with(context).load(friendProfile[j]).circleCrop().into(profileImage3);
            }
        }


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("??????????????????", "onClick: ");

                PopupMenu popupMenu = new PopupMenu(context, deleteBtn);
                popupMenu.getMenu().add(0, 0, 0, "??????");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("????????????").setMessage("?????? ?????????????????????????");
                        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            boolean success = jsonObject.getBoolean("success");
                                            if (success) {
                                                promises.remove(i);
                                                notifyDataSetChanged();
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                promises.remove(i);
                                notifyDataSetChanged();
                                deletePromissRequest deletePromissRequest = new deletePromissRequest(partycode, userCode, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                                queue.add(deletePromissRequest);
                            }
                        });
                        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
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