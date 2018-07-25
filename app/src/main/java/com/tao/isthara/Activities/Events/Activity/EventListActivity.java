package com.tao.isthara.Activities.Events.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;


import com.tao.isthara.Activities.Events.Adapter.GridAdapter;
import com.tao.isthara.R;

import java.util.ArrayList;


public class EventListActivity extends AppCompatActivity {

    public static AppCompatActivity activity;


    GridView gv;
    Context context;
    ArrayList prgmName;
    public static String [] prgmNameList={"ZUMBA", "YOGA"};
    public static int [] prgmImages={R.drawable.img_zumba, R.drawable.img_yoga};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_event_list);
        //InitializeToolbar();
        activity = this;

        getSupportActionBar().setTitle("Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);

        gv=(GridView) findViewById(R.id.gridView);
        gv.setAdapter(new GridAdapter(this, prgmNameList,prgmImages));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help_desk_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
