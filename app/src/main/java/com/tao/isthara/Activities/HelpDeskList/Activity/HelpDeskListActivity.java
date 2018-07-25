package com.tao.isthara.Activities.HelpDeskList.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tao.isthara.Activities.HelpDeskCreateNew.Activity.HelpDeskCreateNewActivity;
import com.tao.isthara.Activities.HelpDeskList.Adapters.IssueListAdapter;
import com.tao.isthara.Activities.HelpDeskList.Adapters.RecyclerItemClickListener;
import com.tao.isthara.Activities.HelpDeskView.Activity.HelpDeskViewActivity;
import com.tao.isthara.Activities.Home.Activity.MainActivity;
import com.tao.isthara.Model.IssueListResponse;
import com.tao.isthara.Model.IssueListResponseRecords;
import com.tao.isthara.Model.Issue_list_item;
import com.tao.isthara.Model.RecordsCount;
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

public class HelpDeskListActivity extends AppCompatActivity {
    private AppPreferences _appPrefs;
    private CoordinatorLayout mCoordinatorLayout;
    //a list to store all the products
    List<Issue_list_item> issuesList;
    TextView btnLatest, btnResolved, btnClosed;
    RelativeLayout emptyState ;

    private ProgressBar mProgressView;


    //the recyclerview
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    String mStatusFlag="Sent";
    // variable to track event time
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_desk_list);

        getSupportActionBar().setTitle("HELP DESK");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);

        _appPrefs = new AppPreferences(getApplicationContext());
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        emptyState = (RelativeLayout) findViewById(R.id.rl_empty_state);

        mProgressView = (ProgressBar) findViewById(R.id.progress);

        btnLatest = (TextView) findViewById(R.id.tab_latest);
        btnLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                issuesList.clear();
                mStatusFlag = "Sent";
                getList("Sent");
                //addListItem();
                //setAdapterValues();

                btnLatest.setBackground(getDrawable(R.drawable.bottom_line));
                btnResolved.setBackground(null);
                btnClosed.setBackground(null);


            }
        });

        btnResolved = (TextView) findViewById(R.id.tab_resolved);
        btnResolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


                issuesList.clear();

                mStatusFlag = "Available";
                getList("Available");
                //setAdapterValues();

                btnResolved.setBackground(getDrawable(R.drawable.bottom_line));
                btnClosed.setBackground(null);
                btnLatest.setBackground(null);

            }
        });


        btnClosed = (TextView) findViewById(R.id.tab_closed);
        btnClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


                issuesList.clear();
                mStatusFlag = "Completed";
                getList("Completed");

                //setAdapterValues();

                btnClosed.setBackground(getDrawable(R.drawable.bottom_line));
                btnLatest.setBackground(null);
                btnResolved.setBackground(null);



            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HelpDeskListActivity.this, HelpDeskCreateNewActivity.class);
                startActivity(i);
            }
        });

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Issue_list_item list = issuesList.get(position);
                        Intent i = new Intent(getApplicationContext(), HelpDeskViewActivity.class);
                        i.putExtra("TOKEN",list.getTicketNo());
                        i.putExtra("STATUS",list.getStatus());
                        i.putExtra("DEPARTMENT",list.getCategory());
                        i.putExtra("SERVICE",list.getSubcategory());
                        i.putExtra("TITLE",list.getTitle());
                        i.putExtra("DESC",list.getDesc());
                        i.putExtra("ISSUEDATE",list.getDate());
                        i.putExtra("ISFROM",mStatusFlag);
                        i.putExtra("HELPDESKID",list.getId());
                        i.putExtra("PROPERTY",list.getProperty());
                        i.putExtra("ROOMNO",list.getRoomno());
                        i.putExtra("BEDNAME",list.getBedname());
                        i.putExtra("RATING",list.getRating());
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        btnLatest.setBackground(getDrawable(R.drawable.bottom_line));
        btnResolved.setBackground(null);
        btnClosed.setBackground(null);

      /*  issuesList = new ArrayList<>();
        getList("Sent");*/


    }

    void refreshItems() {
        if (isNetworkConnected()) {
            issuesList = new ArrayList<>();
            getList(mStatusFlag);
        } else {
            showSnackbar("No Internet Connection!");
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }



    /*private void addListItem(){
        //adding some items to our list
        issuesList.add(
                new Issue_list_item(
                        1,
                        "Request Electrical Work.",
                        "Electronics - TV",
                        "10 APRIL 2018",
                        "CP10699",
                        "Resolved"));

        issuesList.add(
                new Issue_list_item(
                        2,
                        "Need a Plumber.",
                        "Plumbing, Hose Clamps",
                        "05 APRIL 2018",
                        "CP10698",
                        "Resolved"));


        issuesList.add(
                new Issue_list_item(
                        3,
                        "Need a Plumber.",
                        "Plumbing - Water leakage",
                        "02 MAR 2018",
                        "CP10697",
                        "Resolved"));


        issuesList.add(
                new Issue_list_item(
                        4,
                        "Request Electrical Work.",
                        "Electronics - AC",
                        "25 FEB 2018",
                        "CP10696",
                        "Resolved"));

        issuesList.add(
                new Issue_list_item(
                        3,
                        "Request Electrical Work.",
                        "Electronics - TV",
                        "02 MAR 2018",
                        "CP10695",
                        "Resolved"));


        issuesList.add(
                new Issue_list_item(
                        4,
                        "Request Electrical Work.",
                        "Electronics - AC",
                        "25 FEB 2018",
                        "CP10694",
                        "Resolved"));

    }*/

    private void setAdapterValues(){

        //creating recyclerview adapter
        IssueListAdapter adapter = new IssueListAdapter(this, issuesList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
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


    private void getList(String mTabStatus) {
        showProgress(true);
        final String API_KEY = Global.BASE_URL + "HelpDeskList?status="+mTabStatus+"&userId="+_appPrefs.getUserID();

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<IssueListResponse> call = apiService.getIssueListResponse(API_KEY);
        call.enqueue(new Callback<IssueListResponse>() {
            @Override
            public void onResponse(Call<IssueListResponse> call, Response<IssueListResponse> response) {
                int statusCode = response.code();
                //showProgress(false);
                String responsMsg = response.body().getMessageText();
                if (responsMsg.equals("Success")) {
                    RecordsCount rCount = response.body().getRecordsCount();
                    btnLatest.setText("LATEST ("+rCount.getSent()+")");
                    btnResolved.setText("RESOLVED ("+rCount.getAvailable()+")");
                    btnClosed.setText("CLOSED ("+rCount.getCompleted()+")");

                    List<IssueListResponseRecords> list = response.body().getRecords();
                    if(list.size()>0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        emptyState.setVisibility(View.GONE);
                        for (int i = 0; i < list.size(); i++) {
                            IssueListResponseRecords ilr = list.get(i);
                            issuesList.add(
                                    new Issue_list_item(
                                            ilr.getHelpDeskId(),
                                            ilr.getTitle(),
                                            ilr.getCategoryName(),
                                            getFormattedDate(ilr.getIssueDate()),
                                            ilr.getIssueNumber(),
                                            ilr.getStatus(),
                                            ilr.getSubCategoryName(),
                                            ilr.getDescription(),
                                            ilr.getProperty(),
                                            ilr.getBedName(),
                                            ilr.getRoomNo(),
                                            ilr.getRating()));
                        }
                        setAdapterValues();

                    }else{
                        recyclerView.setVisibility(View.GONE);
                        emptyState.setVisibility(View.VISIBLE);
                    }
                    showProgress(false);
                } else {
                    showProgress(false);
                    showSnackbar(responsMsg);
                }
                //LoginResponseRecords resposeResult = response.body().getRecords();


            }

            @Override
            public void onFailure(Call<IssueListResponse> call, Throwable t) {
                //showProgress(false);
                showProgress(false);
                recyclerView.setVisibility(View.GONE);
                emptyState.setVisibility(View.VISIBLE);
                showSnackbar("No Records.");
            }
        });
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

    public String getFormattedDate(String inputDateStr) {
        String outputDateStr = "";
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
            outputDateStr = outputFormat.format(date);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateStr;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (isNetworkConnected()) {
            if(mStatusFlag.equals("Sent")){
                btnLatest.setBackground(getDrawable(R.drawable.bottom_line));
                btnResolved.setBackground(null);
                btnClosed.setBackground(null);

                issuesList = new ArrayList<>();
                getList("Sent");
            }else  if(mStatusFlag.equals("Available")){
                btnLatest.setBackground(null);
                btnResolved.setBackground(getDrawable(R.drawable.bottom_line));
                btnClosed.setBackground(null);

                issuesList = new ArrayList<>();
                getList("Available");
            }

          /*  addListItem();
            setAdapterValues();*/
        } else {
            showSnackbar("No Internet Connection!");
        }

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSwipeRefreshLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mSwipeRefreshLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSwipeRefreshLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSwipeRefreshLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
