package com.example.bbic.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bbic.Data.Friend;
import com.example.bbic.FP.FP_friend_list;
import com.example.bbic.R;

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

        v.setTag(friends.get(i).getUserKakapCode());
        return v;
    }
}