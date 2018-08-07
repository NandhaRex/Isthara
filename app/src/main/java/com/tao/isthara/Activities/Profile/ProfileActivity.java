package com.tao.isthara.Activities.Profile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.tao.isthara.Activities.HelpDeskCreateNew.Activity.CategoriesRecyclerViewActivity;
import com.tao.isthara.Activities.HelpDeskCreateNew.Activity.HelpDeskCreateNewActivity;
import com.tao.isthara.Activities.HelpDeskCreateNew.Adapters.SingleSelectionAdapter;
import com.tao.isthara.Activities.Login.Activity.LoginActivity;
import com.tao.isthara.Activities.Splash.Activity.SplashActivity;
import com.tao.isthara.Model.Categories;
import com.tao.isthara.Model.ProfileRecords;
import com.tao.isthara.Model.ProfileResponse;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.AppPreferences;
import com.tao.isthara.Utils.Global;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private ConstraintLayout mCoordinatorLayout;
    Context mContext;
    private AppPreferences _appPrefs;

    private ProgressBar mProgressView;
    private View mFormView;

    TextView txtUserName, txtRoomBlockInfo, txtProperty, txtMobile;
    Button btnLogout;
    private EditText txtSecMobile,txtEmail;
    private ImageButton btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mContext =this;

        getSupportActionBar().setTitle("MY PROFILE");
        getSupportActionBar().setElevation(0);

        _appPrefs = new AppPreferences(getApplicationContext());
        mCoordinatorLayout = (ConstraintLayout) findViewById(R.id.coordinatorLayout);

        mFormView = findViewById(R.id.form);
        mProgressView = (ProgressBar) findViewById(R.id.progress);

        txtUserName = (TextView) findViewById(R.id.user_profile_name);
        txtRoomBlockInfo = (TextView) findViewById(R.id.room_block_info);
        txtProperty = (TextView) findViewById(R.id.property);
        txtMobile = (TextView) findViewById(R.id.mobile);
        txtSecMobile = (EditText) findViewById(R.id.secmobile);
        txtEmail = (EditText) findViewById(R.id.emailId);

        btnLogout =(Button) findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        btnEditProfile = (ImageButton)findViewById((R.id.btn_edit));

        showProgress(true);
        getProfileDetails();


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
        if (id == R.id.action_close) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getProfileDetails() {
        final String API_KEY = Global.BASE_URL + "GetResidentDetailsByUserId?userId="+_appPrefs.getUserID();

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileResponse> call = apiService.getProfile(API_KEY);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                int statusCode = response.code();
                String responsMsg = response.body().getMessageText();
                showProgress(false);
                if (responsMsg.equals("Success")) {
                    ProfileRecords mProfile = response.body().getRecords();
                    txtUserName.setText(mProfile.getName());
                    txtRoomBlockInfo.setText(mProfile.getBedName());
                    txtProperty.setText(mProfile.getProperty());
                    txtMobile.setText(mProfile.getMobileNo());
                    txtSecMobile.setText(mProfile.getSecMobileNumber());

                } else {
                    showSnackbar(responsMsg);
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                showSnackbar("Can't connect to server.");
                showProgress(false);
                //showSnackbar("No Records.");
            }
        });
    }

    private void showSnackbar(String msg) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "" + msg, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
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

            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

