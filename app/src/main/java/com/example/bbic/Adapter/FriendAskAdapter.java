package com.example.bbic.Adapter;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bbic.DB.AcceptFriendRequest;
import com.example.bbic.DB.AddNewKakaoFriendRequest;
import com.example.bbic.DB.deleteAskFriendRequest;
import com.example.bbic.Data.Friend;
import com.example.bbic.FP.FP_friend_ask;
import com.example.bbic.R;

import org.json.JSONObject;

import java.util.List;

public class FriendAskAdapter extends BaseAdapter {

    private Context context;
    private List<Friend> friends;
    private List<Friend> userFriends;
    private List<Friend> userFriendsStatus;
    private Fragment parentActivity;
    private long userKakaoCode;


    public FriendAskAdapter(Context context, List<Friend> friends, List<Friend> userFriends, long userKakaoCode,FP_friend_ask parentActivity) {
        this.context = context;
        this.friends = friends;
        this.userFriends = userFriends;
        //this.userFriendsStatus = userFriendsStatus;
        this.userKakaoCode = userKakaoCode;
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

        View v = View.inflate(context, R.layout.fp_friend_ask_list, null);

        ImageView profile = (ImageView) v.findViewById(R.id.ask_profile_iv);
        TextView friendName = (TextView) v.findViewById(R.id.ask_name);
        ImageView friendstatus = (ImageView) v.findViewById(R.id.ask_pro_stat_iv);

        int status = friends.get(i).getFriendGhost();
        Glide.with(context).load(friends.get(i).getFriendProfileURL()).circleCrop().into(profile); // 친구프로필
        friendName.setText(friends.get(i).getFriendName()); // 친구이름


        if (status == 0) {
            friendstatus.setBackgroundColor(Color.GREEN);
        }
        if (status == 1) {
            friendstatus.setBackgroundColor(Color.parseColor("#FF6A00"));
        }
        if (status == 2) {
            friendstatus.setBackgroundColor(Color.BLUE);
        }

        ImageButton deletebutton = (ImageButton)v.findViewById(R.id.ask_delete_ibtn);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){

                                notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                friends.remove(i);
                notifyDataSetChanged();
                deleteAskFriendRequest  deleteAskFriendRequest = new deleteAskFriendRequest(friends.get(i).getUserKakapCode(), friends.get(i).getFriendKakaoCode(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                queue.add(deleteAskFriendRequest);

            }
        });

        ImageButton acceptButton = (ImageButton) v.findViewById(R.id.ask_accept_iv);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                friends.remove(i);
                notifyDataSetChanged();
                System.out.println("수락 버튼"+friends.get(i).getUserKakapCode() + "" + friends.get(i).getFriendKakaoCode());
                AcceptFriendRequest acceptFriendRequest = new AcceptFriendRequest(friends.get(i).getUserKakapCode(), friends.get(i).getFriendKakaoCode(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                AddNewKakaoFriendRequest addNewKakaoFriendRequest = new AddNewKakaoFriendRequest(userKakaoCode, friends.get(i).getFriendKakaoCode(), 1, responseListener);
                queue.add(addNewKakaoFriendRequest);
                queue.add(acceptFriendRequest);

            }
        });

        //v.setTag(friends.get(i).getUserKakapCode());
        return v;
    }
}