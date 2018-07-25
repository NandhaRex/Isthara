package com.tao.isthara.Activities.FoodMenu.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tao.isthara.Activities.HelpDeskCreateNew.Activity.HelpDeskCreateNewActivity;
import com.tao.isthara.Activities.HelpDeskList.Adapters.IssueListAdapter;
import com.tao.isthara.Activities.HelpDeskList.Adapters.RecyclerItemClickListener;
import com.tao.isthara.Activities.HelpDeskView.Activity.HelpDeskViewActivity;
import com.tao.isthara.Model.IssueListResponse;
import com.tao.isthara.Model.IssueListResponseRecords;
import com.tao.isthara.Model.Issue_list_item;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.AppPreferences;
import com.tao.isthara.Utils.Global;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodMenuActivity extends AppCompatActivity {
    private AppPreferences _appPrefs;
    private CoordinatorLayout mCoordinatorLayout;

    TextView btnBFast, btnDinner;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        getSupportActionBar().setTitle("FOOD MENU");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);

        _appPrefs = new AppPreferences(getApplicationContext());
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        imageView = (ImageView) findViewById(R.id.imageView);

        btnBFast = (TextView) findViewById(R.id.tab_bfast);
        btnBFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.clearAnimation();
                imageView.setImageResource(R.mipmap.breakfast);

                btnBFast.setBackground(getDrawable(R.drawable.bottom_line));
                btnDinner.setBackground(null);

            }
        });

        btnDinner = (TextView) findViewById(R.id.tab_dinner);
        btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.clearAnimation();
                imageView.setImageResource(R.mipmap.dinner);

                btnDinner.setBackground(getDrawable(R.drawable.bottom_line));
                btnBFast.setBackground(null);

            }
        });


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

    private void showSnackbar(String msg) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "" + msg, Snackbar.LENGTH_LONG);
                  /*  View view = snackbar.getView();
                    view.setBackgroundColor(Color.WHITE);*/

        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

}
