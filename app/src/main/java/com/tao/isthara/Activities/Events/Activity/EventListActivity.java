package com.tao.isthara.Activities.Events.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;


import com.tao.isthara.Activities.Events.Adapter.GridAdapter;
import com.tao.isthara.Model.EventsHeaderImageResponse;
import com.tao.isthara.Model.ProfileResponse;
import com.tao.isthara.Model.Records;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.AppPreferences;
import com.tao.isthara.Utils.Global;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventListActivity extends AppCompatActivity {

    public static AppCompatActivity activity;


    GridView gv;
    Context context;
    ArrayList prgmName;
    public static String[] prgmNameList = {"ZUMBA", "YOGA"};
    //  public static int [] prgmImages={R.drawable.img_zumba, R.drawable.img_yoga};
    public String[] prgmImages;
    private AppPreferences _appPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        //InitializeToolbar();
        activity = this;

        _appPrefs = new AppPreferences(getApplicationContext());


        getSupportActionBar().setTitle("Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);

        gv = (GridView) findViewById(R.id.gridView);
       // final ListAdapter grid = new GridAdapter(this, recordsList);


        final String API_KEY = Global.BASE_URL + "GetEventDetailsHeaderImage";

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<EventsHeaderImageResponse> call = apiService.getEventsHeaderImage(API_KEY);
        call.enqueue(new Callback<EventsHeaderImageResponse>() {
            @Override
            public void onResponse(Call<EventsHeaderImageResponse> call, Response<EventsHeaderImageResponse> response) {
                int res = response.code();
                if (response.body().getTotalRecord() > 0) {
                    gv.setAdapter(new GridAdapter(EventListActivity.activity,response.body().getRecords()));
                }
            }

            @Override
            public void onFailure(Call<EventsHeaderImageResponse> call, Throwable t) {

            }
        });
        //gv.setAdapter(grid);
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
