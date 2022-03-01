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

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.CustomViewHolder> {
    private ArrayList<PlaceData> arrayList;

    public PlaceAdapter(ArrayList<PlaceData> arrayList) {this.arrayList = arrayList;}

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_place_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.CustomViewHolder holder, int position) {
        holder.place_iv.setImageResource(arrayList.get(position).getPlace_iv());
        holder.place_name.setText(arrayList.get(position).getPlace_name());
        holder.place_address.setText(arrayList.get(position).getPlace_address());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName =holder.place_name.getText().toString();
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
    public int getItemCount() {return (null != arrayList ? arrayList.size():0);}

    public void remove(int position){
        try {
            arrayList.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        protected ImageView place_iv;
        protected TextView place_name;
        protected TextView place_address;

        public CustomViewHolder(@NonNull View itemView){
            super(itemView);
            this.place_address =(TextView) itemView.findViewById(R.id.place_address);
            this.place_iv =(ImageView)itemView.findViewById(R.id.place_setting_iv);
            this.place_name = (TextView) itemView.findViewById(R.id.place_name);
        }
//        void onBind(PlaceData item){
//            this.place_address.setText(item.getPlace_address());
//            this.place_name.setText(item.getPlace_name());
//            this.place_iv.setImageResource(item.getPlace_iv());
//        }

    }
}
