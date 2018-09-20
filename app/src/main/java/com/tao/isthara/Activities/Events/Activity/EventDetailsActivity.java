package com.tao.isthara.Activities.Events.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TableRow;
import android.widget.TextView;

import com.tao.isthara.Activities.Events.Adapter.GridAdapter;
import com.tao.isthara.Model.EventData;
import com.tao.isthara.Model.EventsHeaderImageResponse;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.Global;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventDetailsActivity extends AppCompatActivity {

    public static AppCompatActivity activity;

    Context context;
    private int eventDetailsID;
    private ImageView imageView, imageViewForData;
    private ProgressBar mProgressView;
    private String eventDetailType;
    private String API_KEY;
    private ScrollView detailsForm;
    private TextView eventName, eventDate,
            eventVenue;
    private TextView eventst, eventet;
    private View line2;
    private LinearLayout eventTimeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStatusBarTranslucent(true);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                eventDetailsID = extras.getInt("eventDetaildID");
                eventDetailType = extras.getString("detailType");
            }
        } else {
            eventDetailsID = (int) savedInstanceState.getSerializable("eventDetaildID");
            eventDetailType = (String) savedInstanceState.getSerializable("detailType");
        }

        setContentView(R.layout.activity_events_details);
        activity = this;
        mProgressView = (ProgressBar) findViewById(R.id.progress);
        showProgress(true);

        /*getSupportActionBar().setTitle("Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        imageView = findViewById(R.id.banner);

        detailsForm = findViewById(R.id.form);
        imageViewForData = findViewById(R.id.bannerForData);
        eventName = (TextView) findViewById(R.id.eventName);
        eventDate = (TextView) findViewById(R.id.eventDate);
        eventVenue = (TextView) findViewById(R.id.eventVenue);

        eventst = (TextView) findViewById(R.id.eventST);
        eventet = (TextView) findViewById(R.id.eventET);

        eventTimeLayout = (LinearLayout)findViewById(R.id.eventTimeLayout);
        line2 = findViewById(R.id.lineBelowEndTime);

        if (eventDetailType.equals("Image")) {
            API_KEY = Global.BASE_URL + "GetEventDetailsImageByEventDetails_Id?EventDetails_Id=" + eventDetailsID;
            detailsForm.setVisibility(View.GONE);
            GetEventDetails(API_KEY);

        } else {
            API_KEY = Global.BASE_URL + "GetEventDetailsByEventDetails_Id?EventDetails_Id=" + eventDetailsID;
            imageView.setVisibility(View.GONE);
            GetEventDetails(API_KEY);
        }


//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setTitle("Event Details");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void GetEventDetails(String api_key) {

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<EventsHeaderImageResponse> call = apiService.getEventsHeaderImage(API_KEY);
        call.enqueue(new Callback<EventsHeaderImageResponse>() {
            @Override
            public void onResponse(Call<EventsHeaderImageResponse> call, Response<EventsHeaderImageResponse> response) {
                if (response.body().getHeaderImage().size() > 0 && response.body().getHeaderImage() != null && eventDetailType.equals("Image")) {
                    String data = response.body().getHeaderImage().get(0).getDocumentData();
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(GetEventImage(data));
                } else {
                    String data = response.body().getHeaderImage().get(0).getDocumentData();
                    imageViewForData.setImageBitmap(GetEventImage(data));
                    EventData eventData = response.body().getEventData();
                    eventName.setText(eventData.getEventName());
                    eventDate.setText(eventData.getEventDate().replace(",", "\n"));
                    eventVenue.setText(eventData.getEventVenue());
                    if (!TextUtils.isEmpty(eventData.getEventStartTime())) {
                        eventst.setText(eventData.getEventStartTime());
                    } else {
                        eventTimeLayout.setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(eventData.getEventEndTime())) {
                        eventet.setText(eventData.getEventEndTime());
                    } else {
                        line2.setVisibility(View.GONE);
                    }
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<EventsHeaderImageResponse> call, Throwable t) {

            }
        });

    }

    private Bitmap GetEventImage(String data) {
        byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help_desk_create_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close || id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

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
            // mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
