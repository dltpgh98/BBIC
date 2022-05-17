package com.example.bbic;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.List;

public class ViewpagerAdapter extends RecyclerView.Adapter<ViewpagerAdapter.ViewHolder>{

    private final List<String> Items;
    private final int mode;
    private String name, code, address;

    public ViewpagerAdapter(List<String> Items) {
        this.Items = Items;
        this.mode = 0;
    }

    public ViewpagerAdapter(List<String> Items, String name, String code, String address) {
        this.Items = Items;
        this.mode = 1;
        this.name = name;
        this.code = code;
        this.address = address;
    }

    @NonNull
    @Override
    public ViewpagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewpagerAdapter.ViewHolder holder, int position) {

        if (mode == 1) {
            Glide.with(holder.itemView.getContext()).load(address).circleCrop().into(holder.profile);
            holder.name.setText(name);
            holder.code.setText(code);
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("holder", "");
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pagerText;
        TextView name;
        TextView code;
        ImageView profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            if (mode==1){
                name = itemView.findViewById(R.id.view_header_name);
                code = itemView.findViewById(R.id.view_header_code);
                profile = itemView.findViewById(R.id.view_header_profile);
            }
        }
    }
}