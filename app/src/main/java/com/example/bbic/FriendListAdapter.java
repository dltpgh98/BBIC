package com.example.bbic;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FriendListAdapter extends BaseAdapter {

private Context context;
private List<Friend> friends;

    public FriendListAdapter(Context context, List<Friend> friends) {
        this.context = context;
        this.friends = friends;
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

        View v = View.inflate(context, R.layout.fp_friend_list_list, null);

        ImageView profile = (ImageView) v.findViewById(R.id.ask_profile_iv);
        TextView friendName = (TextView)v.findViewById(R.id.ask_name);
        ImageView friendstatus = (ImageView) v.findViewById(R.id.ask_pro_stat_iv);
        int status = friends.get(i).getFriendGhost();
        Glide.with(context).load(friends.get(i).getFriendProfileURL()).circleCrop().into(profile); // 친구프로필
        friendName.setText(friends.get(i).getFriendName()); // 친구이름

        if(status == 0 ){
            friendstatus.setBackgroundColor(Color.GREEN);
        }
        if(status == 1){
            friendstatus.setBackgroundColor(Color.parseColor("FF6A00"));
        }
        if(status == 2){
            friendstatus.setBackgroundColor(Color.BLUE);
        }

        return v;
    }
}
