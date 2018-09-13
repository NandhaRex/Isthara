package com.tao.isthara.Activities.Checkout.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.util.LocaleData;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tao.isthara.Activities.Profile.ProfileActivity;
import com.tao.isthara.Model.CheckOutRequest;
import com.tao.isthara.Model.CheckOutResponse;
import com.tao.isthara.Model.CheckoutReasonResponse;
import com.tao.isthara.Model.ProfileRecords;
import com.tao.isthara.Model.ProfileResponse;
import com.tao.isthara.Model.Records;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.AppPreferences;
import com.tao.isthara.Utils.Global;

import org.json.JSONObject;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private EditText txt_datepicker;
    private DatePickerDialog.OnDateSetListener listener;
    private Calendar calender;
    private TextView guestName, roomNo, branchName, lbl_bankText;
    private ProgressBar mProgressView;
    private AppPreferences _appPrefs;
    private RelativeLayout mRelativeLayout;
    private Spinner reasonForExitSpinner;
    private ArrayList<String> reasons;
    private ArrayAdapter<String> adapter;
    private ArrayList<Records> reasonAndValueList;
    private Button btn_Submit;
    private LinearLayout layDatePicket;
    private EditText bankName, accName, iFSC, accNo, feedBack;
    private boolean ischeckedOut;
    private ImageView imgCalender;
    private TextView lblreason;
    private LinearLayout layReasonSpinner;
    private TextView nameheader, reasonheader,
            branchHeader, roomNoHeader,
            dateHeader, bankNameHeader,
            accNameHeader, accNoHeader, ifscHeader;
    private TextView lblfeedBack;
    private int selectedYear, selectedMonth, selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        _appPrefs = new AppPreferences(getApplicationContext());
        ischeckedOut = getIntent().getExtras().getBoolean("isCheckedout");

        getSupportActionBar().setTitle("CHECKOUT FORM");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        calender = Calendar.getInstance();
        lbl_bankText = (TextView) findViewById(R.id.lbl_bankName);
        nameheader = (TextView) findViewById(R.id.nameHeader);
        branchHeader = (TextView) findViewById(R.id.branchHeader);
        roomNoHeader = (TextView) findViewById(R.id.roomNoHeader);
        reasonheader = (TextView) findViewById(R.id.reasonheader);
        dateHeader = (TextView) findViewById(R.id.dateHeader);
        bankNameHeader = (TextView) findViewById(R.id.bankNameHeader);
        accNameHeader = (TextView) findViewById(R.id.accNameHeader);
        accNoHeader = (TextView) findViewById(R.id.accNoHeader);
        ifscHeader = (TextView) findViewById(R.id.ifscHeader);
        guestName = (TextView) findViewById(R.id.lbl_Name);
        branchName = (TextView) findViewById(R.id.lbl_branch);
        roomNo = (TextView) findViewById(R.id.lbl_roomNo);
        lblfeedBack = (TextView) findViewById(R.id.lbl_feedback);
        mProgressView = (ProgressBar) findViewById(R.id.progress);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.layCheckOut);
        lblreason = (TextView) findViewById(R.id.lblreason);
        layReasonSpinner = (LinearLayout) findViewById(R.id.layReasonSpinner);
        btn_Submit = (Button) findViewById(R.id.btn_Submit);
        bankName = (EditText) findViewById(R.id.txt_BankName);
        feedBack = (EditText) findViewById(R.id.txt_feedback);
        accName = (EditText) findViewById(R.id.txt_accntHolderName);
        iFSC = (EditText) findViewById(R.id.txt_ifsccode);
        accNo = (EditText) findViewById(R.id.txt_accntNo);
        imgCalender = (ImageView) findViewById(R.id.imgCalender);
        reasons = new ArrayList<String>();
        getProfileDetails();
        getReasonforLeaving();

//        String bank_text = "Bank Details (for refund if any)";
//        SpannableString spannableString = new SpannableString(bank_text);
//        spannableString.setSpan(new StyleSpan(Typeface.BOLD),0,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new ,0,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new StyleSpan(Typeface.NORMAL),12,31,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        lbl_bankText.setText(Html.fromHtml("<b>Bank Details</b> (for refund if any)"));
        txt_datepicker = (EditText) findViewById(R.id.txt_Date);
        layDatePicket = (LinearLayout) findViewById(R.id.layDatePicket);

        reasonForExitSpinner = (Spinner) findViewById(R.id.spnr_reasonForExit);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, reasons);
        reasonForExitSpinner.setAdapter(adapter);


        if (ischeckedOut) {
            guestName.setTextColor(Color.parseColor("#A9A9A9"));
            branchName.setTextColor(Color.parseColor("#A9A9A9"));
            roomNo.setTextColor(Color.parseColor("#A9A9A9"));
            txt_datepicker.setTextColor(Color.parseColor("#A9A9A9"));
            btn_Submit.setVisibility(View.GONE);
            imgCalender.setVisibility(View.GONE);
            layDatePicket.setBackground(null);

            nameheader.setTextColor(Color.BLACK);
            reasonheader.setTextColor(Color.BLACK);
            branchHeader.setTextColor(Color.BLACK);
            roomNoHeader.setTextColor(Color.BLACK);
            dateHeader.setTextColor(Color.BLACK);
            bankNameHeader.setTextColor(Color.BLACK);
            accNameHeader.setTextColor(Color.BLACK);
            accNoHeader.setTextColor(Color.BLACK);
            ifscHeader.setTextColor(Color.BLACK);
            lblfeedBack.setTextColor(Color.BLACK);

            reasonForExitSpinner.setVisibility(View.GONE);
            lblreason.setText(_appPrefs.getKeyReason());
            lblreason.setVisibility(View.VISIBLE);
            layReasonSpinner.setVisibility(View.GONE);

            bankName.setBackground(null);
            bankName.setEnabled(false);

            iFSC.setBackground(null);
            iFSC.setEnabled(false);

            accName.setBackground(null);
            accName.setEnabled(false);
            accName.setText(_appPrefs.getAccHolderName());

            feedBack.setBackground(null);
            feedBack.setEnabled(false);

            accNo.setBackground(null);
            accNo.setEnabled(false);
            GetcheckedOutData();
        } else {
            btn_Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(txt_datepicker.getText()) || TextUtils.isEmpty(iFSC.getText())
                            || TextUtils.isEmpty(bankName.getText()) || TextUtils.isEmpty(accName.getText())
                            || TextUtils.isEmpty(accNo.getText())) {
                        showSnackbar("All fields are mandatory");

                    } else {
                        final AlertDialog alertDialog = new AlertDialog.Builder(CheckoutActivity.this).create();
                        alertDialog.setTitle("Submit");
                        if (CheckOutDueDate(selectedDate, selectedMonth, selectedYear)) {
                            alertDialog.setMessage("Your requested exit date is less than the notice period of " +
                                    "15 days and you might be charged for these  non-notice period days \nDo you want to proceed?");
                        } else {
                            alertDialog.setMessage("Do you want to submit Checkout form ?");
                        }
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CheckAndSubmitForm();
                            }
                        });
                        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                        showProgress(false);
                        btn_Submit.setClickable(true);
                    }
                }
            });
            layDatePicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layDatePicket.setEnabled(false);
                    DatePickerDialog dialog = new DatePickerDialog(CheckoutActivity.this,
                            listener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE));
                    dialog.show();
                    dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            layDatePicket.setEnabled(true);
                        }
                    });
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                }
            });
            listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date = ((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth) + "/" + ((month < 10) ? "0" + month : month) + "/" + year;
                    txt_datepicker.setText(date);
                    selectedYear = year;
                    selectedMonth = month;
                    selectedDate = dayOfMonth;
                    layDatePicket.setEnabled(true);
                }
            };
        }
    }

    private boolean CheckOutDueDate(int dayOfMonth, int month, int year) {

        Calendar startDate = Calendar.getInstance();
        startDate.set(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE));
        long startDateMillis = startDate.getTimeInMillis();

        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, dayOfMonth);
        long endDateMillis = endDate.getTimeInMillis();

        long differenceMillis = endDateMillis - startDateMillis;
        int daysDifference = (int) (differenceMillis / (1000 * 60 * 60 * 24));
        return daysDifference < 15 ? true : false;
    }

    private void GetcheckedOutData() {
        final String API_KEY = Global.BASE_URL + "GetCheckoutDetailsByResidentDetailsId?ResidentDetailsId=" + _appPrefs.getResidentId();
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CheckOutRequest> call = apiService.getCheckOutDetails(API_KEY);
        call.enqueue(new Callback<CheckOutRequest>() {
            @Override
            public void onResponse(Call<CheckOutRequest> call, Response<CheckOutRequest> response) {
                if (response.body() != null) {
                    lblreason.setText(response.body().getReason());
                    txt_datepicker.setText(response.body().getCheckoutDate());
                    bankName.setText(response.body().getBankName());
                    accName.setText(response.body().getAccountHolderName());
                    accNo.setText(response.body().getAccountNo());
                    iFSC.setText(response.body().getIFSC());
                    if (TextUtils.isEmpty(response.body().getResidentFeedBack()) || response.body().getResidentFeedBack().length() < 1) {
                        lblfeedBack.setVisibility(View.GONE);
                        feedBack.setVisibility(View.GONE);
                    } else {
                        feedBack.setText(response.body().getResidentFeedBack());
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckOutRequest> call, Throwable t) {

            }
        });
    }

    private void CheckAndSubmitForm() {
        showProgress(true);
        btn_Submit.setClickable(false);
        CheckOutRequest req = new CheckOutRequest();
        req.setAccountHolderName(accName.getText().toString());
        req.setAccountNo(accNo.getText().toString());
        req.setBankName(bankName.getText().toString());
        req.setCheckoutDate(txt_datepicker.getText().toString());
        req.setIFSC(iFSC.getText().toString());
        req.setRequestedBy(_appPrefs.getResidentId());
        req.setResidentDetailsId(_appPrefs.getResidentId());
        req.setRequestedVia("AndriodMobileApp");
        req.setReasonId(reasonForExitSpinner.getSelectedItemPosition() + 1);
        req.setResidentFeedBack(feedBack.getText().toString());


        final String API_KEY = Global.BASE_URL + "ResidentCheckoutRequest";
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<CheckOutResponse> call = apiService.residentCheckOutRequest(API_KEY, req);

        call.enqueue(new Callback<CheckOutResponse>() {
            @Override
            public void onResponse(Call<CheckOutResponse> call, Response<CheckOutResponse> response) {
                if (response.body() != null) {
                    showSnackbar(response.body().getResult());
                    finish();
                    Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                    intent.putExtra("isCheckedout", true);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Your checkout form has been successfully submitted.",
                            Toast.LENGTH_LONG).show();
                } else {
                    showSnackbar("Error in Submit");
                    btn_Submit.setClickable(true);
                }
            }

            @Override
            public void onFailure(Call<CheckOutResponse> call, Throwable t) {
                showSnackbar("Something went wrong");
                btn_Submit.setClickable(true);
            }
        });
        showProgress(false);
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

    private int getAccountNumber(String text) {

        char ch[] = text.toCharArray();
        int sum = 0;
        //get ascii value for zero
        int zeroAscii = (int) '0';
        for (char c : ch) {
            int tmpAscii = (int) c;
            sum = (sum * 10) + (tmpAscii - zeroAscii);
        }
        return sum;
    }

    private void getReasonforLeaving() {
        final String API_KEY = Global.BASE_URL + "GetReasonListForResidentLeaving";
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CheckoutReasonResponse> call = apiService.getReason(API_KEY);
        call.enqueue(new Callback<CheckoutReasonResponse>() {
            @Override
            public void onResponse(Call<CheckoutReasonResponse> call, Response<CheckoutReasonResponse> response) {
                if (response != null) {
                    reasonAndValueList = response.body().getRecords();
                    for (Records rec : reasonAndValueList) {
                        reasons.add(rec.getText());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CheckoutReasonResponse> call, Throwable t) {

            }
        });
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
                if (response.body() == null) {
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