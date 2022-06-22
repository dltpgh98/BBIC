package com.example.bbic.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bbic.Bookmark.Bookmark;
import com.example.bbic.FP.FP;
import com.example.bbic.GpsTracker;
import com.example.bbic.Maps_Activity;
import com.example.bbic.R;
import com.example.bbic.Setting_Activity;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

public class ViewPager_Item_Adapter extends RecyclerView.Adapter<ViewPager_Item_Adapter.PagerHolder> {

    class BtnOnClickListener implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int count;
            int index=0;
            Intent intent = new Intent(view.getContext(), Maps_Activity.class);
            switch (view.getId()) {
                //case를 통해 id에 따른 클릭이벤트 실행
                case R.id.view_detail_way_ibtn:

                    intent.putExtra("fFlag",1);
                    intent.putExtra("fName",friend_name.get(index));
                    intent.putExtra("fCode",friend_code.get(index));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    dialog.dismiss();
                    view.getContext().startActivity(intent);
                    break;

                case R.id.view_detail_location_ibtn:
                    intent.putExtra("fFlag",2);
                    intent.putExtra("fName",friend_name.get(index));
                    intent.putExtra("fCode",friend_code.get(index));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    dialog.dismiss();
                    view.getContext().startActivity(intent);
                    break;

                case R.id.view_detail_back_ibtn:
                    dialog.dismiss();
                    break;

                case R.id.view_item_ibtn1:
                    index = 0;
                    startDialog(0);
                    break;
                case R.id.view_item_ibtn2:
                    index = 1;
                    startDialog(1);
                    break;
                case R.id.view_item_ibtn3:
                    index = 2;
                    startDialog(2);
                    break;
                case R.id.view_item_ibtn4:
                    index = 3;
                    startDialog(3);
                    break;
                case R.id.view_item_ibtn5:
                    index = 4;
                    startDialog(4);
                    break;
                case R.id.view_item_ibtn6:
                    index = 5;
                    startDialog(5);
                    break;
                case R.id.view_item_ibtn7:
                    index = 6;
                    startDialog(6);
                    break;
                case R.id.view_item_ibtn8:
                    index = 7;
                    startDialog(7);
                    break;
            }
        }
    }

    private final List<String> friend_name, friend_profile;
    private final List<Long> friend_code;
    private List<Integer> friend_status;
    private Context context;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    private boolean event;

    private ImageView detail_thumbnail;
    private ImageButton detail_back_ibtn, detail_add_ibtn, detail_list_ibtn, detail_location_ibtn, detail_way_ibtn, detail_addFriend_ibtn, detail_cancelFriend_ibtn;
    private TextView detail_name;

    private final static int pageItemsCount = 8;

    public ViewPager_Item_Adapter(Context context, List<String> friend_name, List<String> friend_profile, List<Long> friend_code, List<Integer> friend_status) {
        this.context = context;
        this.friend_name = friend_name;
        this.friend_profile = friend_profile;
        this.friend_code = friend_code;
        this.friend_status = friend_status;
    }

    @NonNull
    @Override
    public PagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item, parent, false);
        return new PagerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagerHolder holder, int position) {
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        int i;
        for(i = 0; i < friend_name.size(); i++)
        {
            Log.d("test", "onBindViewHolder: "+friend_name.get(i)+"("+i+")");
            Log.d("test", "onBindViewHolder: "+friend_profile.get(i));
            holder.nameHolder[i].setText(friend_name.get(i));
            //holder.nameHolder[i].setCompoundDrawableTintList(Color.);
            Glide.with(context).load(friend_profile.get(i)).fitCenter().circleCrop().into(holder.profileHolder[i]);
            holder.profileHolder[i].setOnClickListener(onClickListener);
        }
        for(;i<8;i++){
            Glide.with(context).load("https://cdn.discordapp.com/attachments/885795271454384128/984121267068223568/5a22b25fd0d5271f.png").fitCenter().circleCrop().into(holder.profileHolder[i]);
        }
    }

    @Override
    public int getItemCount() {
        int page = friend_name.size()/pageItemsCount;
        if(page==0){
            page=1;
        }
        Log.d("itemSize ", "Size : " + page);
        return page;
    }

    public Dialog getDialog(){
        return dialog;
    }

    public boolean getEvent(){
        return event;
    }

    private void startDialog(int index){
        BtnOnClickListener onClickListener = new BtnOnClickListener();

        builder = new AlertDialog.Builder(context);
        dialog = new Dialog(context,R.style.Theme_TransparentBackground);
        dialog.setContentView(R.layout.view_detail);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams)layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        detail_thumbnail = dialog.findViewById(R.id.view_detail_thumbnail);
        Glide.with(context).load(friend_profile.get(index)).circleCrop().into(detail_thumbnail);

        detail_name = dialog.findViewById(R.id.view_detail_name);
        detail_name.setText(friend_name.get(index));

        detail_back_ibtn = dialog.findViewById(R.id.view_detail_back_ibtn);
        detail_back_ibtn.setOnClickListener(onClickListener);

        detail_add_ibtn = dialog.findViewById(R.id.view_detail_add_ibtn);
        detail_add_ibtn.setOnClickListener(onClickListener);

        detail_list_ibtn = dialog.findViewById(R.id.view_detail_list_ibtn);
        detail_list_ibtn.setOnClickListener(onClickListener);

        detail_location_ibtn = dialog.findViewById(R.id.view_detail_location_ibtn);
        detail_location_ibtn.setOnClickListener(onClickListener);

        detail_way_ibtn = dialog.findViewById(R.id.view_detail_way_ibtn);
        detail_way_ibtn.setOnClickListener(onClickListener);

        detail_addFriend_ibtn = dialog.findViewById(R.id.view_detail_add_friend_ibtn);
        detail_addFriend_ibtn.setOnClickListener(onClickListener);

        detail_cancelFriend_ibtn = dialog.findViewById(R.id.view_detail_cancel_friend_ibtn);
        detail_cancelFriend_ibtn.setOnClickListener(onClickListener);

        dialog.show();
        event = true;
    }

    public class PagerHolder extends RecyclerView.ViewHolder {

        ImageView[] profileHolder = new ImageView[8];
        TextView[] nameHolder = new TextView[8];

        public PagerHolder(@NonNull View itemView) {
            super(itemView);

            profileHolder[0] = itemView.findViewById(R.id.view_item_ibtn1);
            profileHolder[1] = itemView.findViewById(R.id.view_item_ibtn2);
            profileHolder[2] = itemView.findViewById(R.id.view_item_ibtn3);
            profileHolder[3] = itemView.findViewById(R.id.view_item_ibtn4);
            profileHolder[4] = itemView.findViewById(R.id.view_item_ibtn5);
            profileHolder[5] = itemView.findViewById(R.id.view_item_ibtn6);
            profileHolder[6] = itemView.findViewById(R.id.view_item_ibtn7);
            profileHolder[7] = itemView.findViewById(R.id.view_item_ibtn8);

            nameHolder[0] = itemView.findViewById(R.id.view_item_name1);
            nameHolder[1] = itemView.findViewById(R.id.view_item_name2);
            nameHolder[2] = itemView.findViewById(R.id.view_item_name3);
            nameHolder[3] = itemView.findViewById(R.id.view_item_name4);
            nameHolder[4] = itemView.findViewById(R.id.view_item_name5);
            nameHolder[5] = itemView.findViewById(R.id.view_item_name6);
            nameHolder[6] = itemView.findViewById(R.id.view_item_name7);
            nameHolder[7] = itemView.findViewById(R.id.view_item_name8);

            /*for (int i = 0; i < 8; i++) {
                int profileId = itemView.getResources().getIdentifier("view_item_ibtn" + i, "id", context.getPackageName());
                int nameId = itemView.getResources().getIdentifier("view_item_name" + i, "id", context.getPackageName());
                profileHolder[i] = (ImageView) itemView.findViewById(profileId);
                nameHolder[i] = (TextView) itemView.findViewById(nameId);
            }*/
        }
    }
}