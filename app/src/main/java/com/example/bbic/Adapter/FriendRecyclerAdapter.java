package com.example.bbic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bbic.Data.Friend;
import com.example.bbic.R;


import java.util.ArrayList;


public class FriendRecyclerAdapter extends RecyclerView.Adapter<FriendRecyclerAdapter.ItemViewHolder> {

    ArrayList<Friend> friendList = new ArrayList<Friend>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fp_friend_list_list,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.onBind(friendList.get(position));
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public void addItem(Friend friend) {
        friendList.add(friend);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView friendProfile;
        private TextView friendName;
        private String imgURL;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            friendName = itemView.findViewById(R.id.ask_name);
            friendProfile = itemView.findViewById(R.id.ask_profile_iv);
        }

        // 실제 데이터를 1개의 객체마다 1:1 대응하여 바인딩시킨다.
        void onBind(Friend friend) {
            friendName.setText(friend.getFriendName());
            String imgURL = friend.getFriendProfileURL();

            // Glide URL로 이미지 불러오기 오픈소스
            Glide.with(itemView).load(imgURL).into(friendProfile);
        }
    }
}

