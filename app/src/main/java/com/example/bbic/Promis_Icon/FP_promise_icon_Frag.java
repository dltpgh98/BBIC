package com.example.bbic.Promis_Icon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.R;

public class FP_promise_icon_Frag extends Fragment {
    private RecyclerView view_reListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = (View) inflater.inflate(R.layout.fp_promise_icon_list, container, false);

        view_reListView =(RecyclerView) rootView.findViewById(R.id.fp_promise_icon_recV);
        Bundle b = getArguments();

        return rootView;
    }
}
