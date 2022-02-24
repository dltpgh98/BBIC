package com.example.bbic;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

public class Bookmark extends AppCompatActivity {
    TabLayout tabRoot;
    Bookmark_Place bookmark_place;
    Bookmark_Transit bookmark_transit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark);

        bookmark_place = new Bookmark_Place();
        bookmark_transit = new Bookmark_Transit();


        tabRoot = findViewById(R.id.tabRoot);
        tabRoot.removeAllTabs();
        tabRoot.addTab(tabRoot.newTab().setText("1번탭"));
        tabRoot.addTab(tabRoot.newTab().setText("2번탭"));


        getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, bookmark_place).commit();

        tabRoot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition())
                {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, bookmark_place).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, bookmark_transit).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}