package com.example.bbic.Bookmark_Adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.R;

import java.util.ArrayList;

public class    Friend_AskAdapter extends RecyclerView.Adapter<Friend_AskAdapter.FriendaskViewHolder> {
    private ArrayList<PlaceData> arrayList;

    public Friend_AskAdapter(ArrayList<PlaceData> arrayList) {this.arrayList = arrayList;}

    @NonNull
    @Override
    public FriendaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fp_friend_ask_list,parent,false);
        FriendaskViewHolder holder = new FriendaskViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendaskViewHolder holder, int position) {

        holder.friend_profile.setImageResource(arrayList.get(position).getFriend_profile());
        holder.friend_stat.setImageResource(arrayList.get(position).getFriend_stat());
        holder.friend_delete.setImageResource(arrayList.get(position).getFriend_delete());
        holder.friend_accept.setImageResource(arrayList.get(position).getFriend_accept());


        holder.friend_name.setText(arrayList.get(position).getFriend_name());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.friend_name.getText().toString();
                Toast.makeText(view.getContext(),curName, Toast.LENGTH_LONG).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getAdapterPosition());
                return true;
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
    public class FriendaskViewHolder extends RecyclerView.ViewHolder{
        protected ImageView friend_profile;
        protected ImageView friend_stat;
        protected ImageView friend_delete;
        protected ImageView friend_accept;
        protected TextView friend_name;

        public FriendaskViewHolder(@NonNull View itemView){
            super(itemView);
            this.friend_profile = (ImageView) itemView.findViewById(R.id.ask_profile_iv);
            this.friend_stat = (ImageView) itemView.findViewById(R.id.ask_pro_stat_iv);
            this.friend_delete = (ImageView) itemView.findViewById(R.id.ask_delete_iv);
            this.friend_accept = (ImageView) itemView.findViewById(R.id.ask_accept_iv); // 수락버튼
            this.friend_name = (TextView) itemView.findViewById(R.id.ask_name);
        }
    }
}
