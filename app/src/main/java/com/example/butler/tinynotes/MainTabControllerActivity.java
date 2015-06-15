package com.example.butler.tinynotes;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;


public class MainTabControllerActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab_controller);

        TabHost tabHost = getTabHost();


        // Tab for Daily timetable
        TabHost.TabSpec registerspec = tabHost.newTabSpec("New Note");//tag for the tab
        // setting Title and Icon for the Tab
        registerspec.setIndicator("New Note");//Title of the tab
        Intent registerIntent = new Intent(this, Add_Notes.class);
        registerspec.setContent(registerIntent);

        // Tab for Add course
        TabHost.TabSpec listspec = tabHost.newTabSpec("View Notes");
        listspec.setIndicator("View Notes");
        Intent listIntent = new Intent(this, View_Notes.class);
        listspec.setContent(listIntent);


        // Adding all TabSpec to TabHost
        tabHost.addTab(registerspec); // Adding register tab
        tabHost.addTab(listspec); // Adding list tab


    }
}

