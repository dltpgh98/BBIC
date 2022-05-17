package com.example.bbic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.Bookmark_Adapter.Friend_AddAdapter;
import com.example.bbic.Bookmark_Adapter.Friend_ListAdapter;
import com.example.bbic.Bookmark_Adapter.PlaceData;

import java.util.ArrayList;

public class FP_friend_add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank);
    }
}