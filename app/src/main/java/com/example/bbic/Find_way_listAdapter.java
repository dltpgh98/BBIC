package com.example.bbic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Find_way_listAdapter extends RecyclerView.Adapter<Find_way_listAdapter.FindWayListViewHolder> {
    private ArrayList<Find_way_Data> arrayList;

    public Find_way_listAdapter(ArrayList<Find_way_Data> arrayList){this.arrayList = arrayList;}

    @NonNull
    @Override
    public FindWayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_way_item,parent,false);
        FindWayListViewHolder holder = new FindWayListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FindWayListViewHolder holder, int position) {
        holder.object.put(arrayList.get(position).getObject());
        holder.onFoot.setText(arrayList.get(position).getOnFoot_time());
        holder.bus_iv.setImageResource(arrayList.get(position).getBus_iv());
        holder.bus_time.setText(arrayList.get(position).getBus_time());
        holder.sub_iv.setImageResource(arrayList.get(position).getSub_iv());
        holder.sub_time.setText(arrayList.get(position).getSub_time());
        holder.total_time.setText(arrayList.get(position).getTotal_time());
        holder.expansion_iv.setImageResource(arrayList.get(position).getExpansion_iv());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void setArrayList(ArrayList<Find_way_Data> list){
        this.arrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
    }

    public class FindWayListViewHolder extends RecyclerView.ViewHolder{
        protected JSONArray object;
        protected TextView onFoot;
        protected ImageView bus_iv;
        protected TextView bus_time;
        protected ImageView sub_iv;
        protected TextView sub_time;
        protected TextView total_time;
        protected ImageView expansion_iv;
        public FindWayListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.onFoot = (TextView) itemView.findViewById(R.id.find_way_on_foot);
            this.bus_iv = (ImageView) itemView.findViewById(R.id.find_way_itemTransit_firstIv_iv);
            this.bus_time = (TextView) itemView.findViewById(R.id.find_way_busOrSub);
            this.sub_iv = (ImageView) itemView.findViewById(R.id.find_way_itemTransit_twoIv_iv);
            this.sub_time = (TextView) itemView.findViewById(R.id.find_way_subOrBus);
            this.total_time = (TextView) itemView.findViewById(R.id.find_way_itemTime_tv);
            this.expansion_iv = (ImageView) itemView.findViewById(R.id.find_way_itemExpansion_iv);
        }
    }
}
