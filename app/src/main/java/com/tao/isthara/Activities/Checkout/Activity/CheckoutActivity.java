package com.tao.isthara.Activities.Checkout.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tao.isthara.Model.ProfileRecords;
import com.tao.isthara.Model.ProfileResponse;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.AppPreferences;
import com.tao.isthara.Utils.Global;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private EditText txt_datepicker;
    private DatePickerDialog.OnDateSetListener listener;
    private Calendar calender;
    private TextView guestName, roomNo, branchName, lbl_bankText;
    private ProgressBar mProgressView;
    private AppPreferences _appPrefs;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        _appPrefs = new AppPreferences(getApplicationContext());

        getSupportActionBar().setTitle("CHECK OUT");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        calender = Calendar.getInstance();
        lbl_bankText = (TextView) findViewById(R.id.lbl_bankName);
        guestName = (TextView) findViewById(R.id.lbl_Name);
        branchName = (TextView) findViewById(R.id.lbl_branch);
        roomNo = (TextView) findViewById(R.id.lbl_roomNo);
        mProgressView = (ProgressBar) findViewById(R.id.progress);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.layCheckOut);
        getProfileDetails();
//        String bank_text = "Bank Details (for refund if any)";
//        SpannableString spannableString = new SpannableString(bank_text);
//        spannableString.setSpan(new StyleSpan(Typeface.BOLD),0,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new ,0,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new StyleSpan(Typeface.NORMAL),12,31,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        lbl_bankText.setText(Html.fromHtml("<b>Bank Details</b> (for refund if any)"));
        txt_datepicker = (EditText) findViewById(R.id.txt_Date);
        txt_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(CheckoutActivity.this,
                        listener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE));
                dialog.show();
            }
        });
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txt_datepicker.setText(dayOfMonth + "-" + month + "-" + year);
            }
        };
    }

    private void getProfileDetails() {
        final String API_KEY = Global.BASE_URL + "GetResidentDetailsByUserId?userId=" + _appPrefs.getUserID();

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileResponse> call = apiService.getProfile(API_KEY);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                int statusCode = response.code();
                if(response.body() == null)
                {
                    showSnackbar("No Details found");
                    return;
                }
                String responsMsg = response.body().getMessageText();
                showProgress(false);
                if (responsMsg != null && responsMsg.equals("Success")) {
                    ProfileRecords mProfile = response.body().getRecords();
                    guestName.setText(mProfile.getName());
                    branchName.setText(mProfile.getProperty());
                    roomNo.setText(mProfile.getBedName());
                } else {
                    showSnackbar("No Details found");
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                showProgress(false);
                showSnackbar("Can't connect to server.");
            }
        });
    }

    private void showSnackbar(String msg) {
        Snackbar snackbar = Snackbar.make(mRelativeLayout, "" + msg, Snackbar.LENGTH_LONG);
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
        }
    }
}