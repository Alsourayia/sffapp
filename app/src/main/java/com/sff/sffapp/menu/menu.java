package com.sff.sffapp.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.sff.sffapp.R;

public class menu extends AppCompatActivity {
    menuAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        GridView gistView;



        adapter = new menuAdapter(this);
        gistView = findViewById(R.id.menu_grid);
        gistView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        adapter.BackPressed () ;

    }



}
