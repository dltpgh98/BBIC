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


public class Friend_ListAdapter extends RecyclerView.Adapter<Friend_ListAdapter.FriendlistViewHolder> {
    private ArrayList<PlaceData> arrayList;

    public Friend_ListAdapter(ArrayList<PlaceData> arrayList) {this.arrayList=arrayList;}

    @NonNull
    @Override
    public FriendlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fp_friend_list_list,parent,false);
        FriendlistViewHolder holder = new FriendlistViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendlistViewHolder holder, int position) {
        holder.friend_profile.setImageResource(arrayList.get(position).getFriend_profile());
        holder.friend_stat.setImageResource(arrayList.get(position).getFriend_stat());
        holder.friend_setting.setImageResource(arrayList.get(position).getFriend_setting());
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
    public class FriendlistViewHolder extends RecyclerView.ViewHolder{
        protected ImageView friend_profile;
        protected ImageView friend_stat;
        protected ImageView friend_setting;
        protected TextView friend_name;

        public FriendlistViewHolder(@NonNull View itemView){
            super(itemView);
            this.friend_profile = (ImageView) itemView.findViewById(R.id.ask_profile_iv);
            this.friend_stat = (ImageView) itemView.findViewById(R.id.ask_pro_stat_iv);
            this.friend_setting = (ImageView) itemView.findViewById(R.id.ask_setting_iv);
            this.friend_name = (TextView) itemView.findViewById(R.id.ask_name);
        }
    }
}
