package com.example.bbic.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
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
import com.example.bbic.DB.deleteAskFriendRequest;
import com.example.bbic.DB.deletePromissRequest;
import com.example.bbic.Data.Friend;
import com.example.bbic.FP.FP_friend_list;
import com.example.bbic.R;

import org.json.JSONObject;

import java.util.List;

public class FriendListAdapter extends BaseAdapter {

    private Context context;
    private List<Friend> friends;
    private List<Friend> userFriends;
    private List<Friend> userFriendsStatus;
    private Fragment parentActivity;



    public FriendListAdapter(Context context, List<Friend> friends, List<Friend> userFriends, FP_friend_list parentActivity) {
        this.context = context;
        this.friends = friends;
        this.userFriends = userFriends;
        //this.userFriendsStatus = userFriendsStatus;

        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int i) {
        return friends.get(i);
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

        View v = View.inflate(context, R.layout.fp_friend_list_list, null);

        ImageView profile = (ImageView) v.findViewById(R.id.ask_profile_iv);
        TextView friendName = (TextView) v.findViewById(R.id.ask_name);
        ImageView friendstatus = (ImageView) v.findViewById(R.id.ask_pro_stat_iv);
        ImageView deleteBtn = (ImageView) v.findViewById(R.id.ask_setting_iv);

        int status = friends.get(i).getFriendGhost();
        Glide.with(context).load(friends.get(i).getFriendProfileURL()).circleCrop().into(profile); // 친구프로필
        friendName.setText(friends.get(i).getFriendName()); // 친구이름

        long myCode = friends.get(i).getUserKakapCode();
        long friendCode = friends.get(i).getFriendKakaoCode();


        if (status == 0) {
            friendstatus.setBackgroundColor(Color.GREEN);
        }
        if (status == 1) {
            friendstatus.setBackgroundColor(Color.parseColor("#FF6A00"));
        }
        if (status == 2) {
            friendstatus.setBackgroundColor(Color.BLUE);
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, deleteBtn);
                popupMenu.getMenu().add(0, 0, 0, "삭제");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("친구삭제").setMessage("정말 삭제하시겠습니까?");
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
                                                friends.remove(i);
                                                notifyDataSetChanged();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                friends.remove(i);
                                notifyDataSetChanged();
                                deleteAskFriendRequest deleteAskFriendRequest = new deleteAskFriendRequest(myCode, friendCode, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(v.getContext());
                                queue.add(deleteAskFriendRequest);
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

        v.setTag(friends.get(i).getUserKakapCode());
        return v;
    }
}