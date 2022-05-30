package com.example.bbic.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bbic.DB.AcceptFriendRequest;
import com.example.bbic.DB.deleteAskFriendRequest;
import com.example.bbic.Data.Friend;
import com.example.bbic.FP_friend_ask;
import com.example.bbic.FP_friend_list;
import com.example.bbic.R;

import org.json.JSONObject;

import java.util.List;

public class FriendAskAdapter extends BaseAdapter {

    private Context context;
    private List<Friend> friends;
    private List<Friend> userFriends;
    private List<Friend> userFriendsStatus;
    private Fragment parentActivity;


    public FriendAskAdapter(Context context, List<Friend> friends, List<Friend> userFriends, FP_friend_ask parentActivity) {
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
            friendstatus.setBackgroundColor(Color.parseColor("FF6A00"));
        }
        if (status == 2) {
            friendstatus.setBackgroundColor(Color.BLUE);
        }
        ImageButton deletebutton = (ImageButton)v.findViewById(R.id.ask_delete_iv);
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
                                friends.remove(i);
                                notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
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

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                AcceptFriendRequest acceptFriendRequest = new AcceptFriendRequest(friends.get(i).getUserKakapCode(), friends.get(i).getFriendKakaoCode(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                queue.add(acceptFriendRequest);
                friends.remove(i);
                notifyDataSetChanged();
            }
        });

        v.setTag(friends.get(i).getUserKakapCode());
        return v;
    }
}
