package com.example.bbic.Bookmark_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.R;

import java.util.ArrayList;

public class Friend_AddAdapter extends RecyclerView.Adapter<Friend_AddAdapter.FriendaddViewHolder> {
    private ArrayList<PlaceData> arrayList;

    public Friend_AddAdapter(ArrayList<PlaceData> arrayList) {this.arrayList = arrayList;}

    @NonNull
    @Override
    public FriendaddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fp_friend_add_list,parent,false);
        FriendaddViewHolder holder = new FriendaddViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendaddViewHolder holder, int position) {

        holder.friend_profile.setImageResource(arrayList.get(position).getFriend_profile());
        holder.friend_accept.setImageResource(arrayList.get(position).getFriend_accept());
        holder.friend_name.setText(arrayList.get(position).getFriend_name());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.friend_name.getText().toString();
                Toast.makeText(view.getContext(),curName, Toast.LENGTH_SHORT).show();
            }
        });
        holder.friend_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.friend_name.getText().toString();
                Toast.makeText(view.getContext(),curName+"님에게 친구요청", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setArrayList(ArrayList<PlaceData> list){
        this.arrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
    }

    public void remove(int position){
        try {
            arrayList.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }
    public class FriendaddViewHolder extends RecyclerView.ViewHolder{
        protected ImageView friend_profile;
        protected ImageView friend_accept;
        protected TextView friend_name;

        public FriendaddViewHolder(@NonNull View itemView){
            super(itemView);
            this.friend_profile = (ImageView) itemView.findViewById(R.id.add_profile_iv);
            this.friend_accept = (ImageView) itemView.findViewById(R.id.add_friend_iv); // 수락버튼
            this.friend_name = (TextView) itemView.findViewById(R.id.add_name);
        }
    }
}
