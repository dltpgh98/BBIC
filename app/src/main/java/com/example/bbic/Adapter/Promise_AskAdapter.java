package com.example.bbic.Adapter;

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

public class Promise_AskAdapter extends RecyclerView.Adapter<Promise_AskAdapter.PromiseAskViewHolder>{
    private ArrayList<PlaceData> arrayList;

    public Promise_AskAdapter(ArrayList<PlaceData> arrayList) {this.arrayList = arrayList;}

    @NonNull
    @Override
    public PromiseAskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fp_promise_ask_list,parent,false);
        PromiseAskViewHolder holder = new PromiseAskViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PromiseAskViewHolder holder, int position) {
        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.left_curve);

        holder.promise_sidebar.setImageResource(arrayList.get(position).getPromise_sidebar());
        drawable.setColor(Color.parseColor("#0034EB"));        holder.promise_sidebar.setImageDrawable(drawable);
        holder.promise_profile1.setImageResource(arrayList.get(position).getPromise_profile1());
        holder.promise_profile2.setImageResource(arrayList.get(position).getPromise_profile2());
        holder.promise_profile3.setImageResource(arrayList.get(position).getPromise_profile3());
        holder.promise_title.setText(arrayList.get(position).getPromise_title());
        holder.promise_address.setText(arrayList.get(position).getPromise_address());
        holder.promise_settime.setText(arrayList.get(position).getPromise_settime());
        holder.promise_delete.setImageResource(arrayList.get(position).getPromise_delete());
        holder.promise_accept.setImageResource(arrayList.get(position).getPromise_accept());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.promise_title.getText().toString();
                Toast.makeText(view.getContext(),curName,Toast.LENGTH_LONG).show();
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

    public class PromiseAskViewHolder extends RecyclerView.ViewHolder{
        protected ImageView promise_sidebar;
        protected ImageView promise_profile1;
        protected ImageView promise_profile2;
        protected ImageView promise_profile3;
        protected TextView promise_title;
        protected TextView promise_address;
        protected TextView promise_settime;
        protected ImageView promise_delete;
        protected ImageView promise_accept;

        public PromiseAskViewHolder(@NonNull View itemView){
            super(itemView);
            this.promise_sidebar = (ImageView) itemView.findViewById(R.id.pro_list_sidebar_iv);
            this.promise_profile1 = (ImageView) itemView.findViewById(R.id.pro_list_profile1_iv);
            this.promise_profile2 = (ImageView) itemView.findViewById(R.id.pro_list_profile2_iv);
            this.promise_profile3 = (ImageView) itemView.findViewById(R.id.pro_list_profile3_iv);
            this.promise_title = (TextView) itemView.findViewById(R.id.pro_list_title_tv);
            this.promise_address = (TextView) itemView.findViewById(R.id.pro_list_address_tv);
            this.promise_settime = (TextView) itemView.findViewById(R.id.pro_list_settime_tv);
            this.promise_delete = (ImageView) itemView.findViewById(R.id.pro_ask_delete_iv);
            this.promise_accept = (ImageView) itemView.findViewById(R.id.pro_ask_accept_iv);
        }
    }
}
