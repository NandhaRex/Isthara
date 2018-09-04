package com.tao.isthara.Activities.Checkout.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.RelativeDateTimeFormatter;
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
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private EditText bankName, accName, iFSC, accNo;
    private boolean ischeckedOut;
    private ImageView imgCalender;
    private TextView lblreason;
    private LinearLayout layReasonSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        _appPrefs = new AppPreferences(getApplicationContext());
        ischeckedOut = _appPrefs.getIsCheckedOut();

        getSupportActionBar().setTitle("EXIT FORM");
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
        lblreason = (TextView) findViewById(R.id.lblreason);
        layReasonSpinner = (LinearLayout) findViewById(R.id.layReasonSpinner);
        btn_Submit = (Button) findViewById(R.id.btn_Submit);
        bankName = (EditText) findViewById(R.id.txt_BankName);
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

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_Submit.setClickable(false);

                final AlertDialog alertDialog = new AlertDialog.Builder(CheckoutActivity.this).create();
                alertDialog.setTitle("Submit");
                alertDialog.setMessage("Do you want to submit Checkout form ?");
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
        });
        layDatePicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layDatePicket.setEnabled(false);
                DatePickerDialog dialog = new DatePickerDialog(CheckoutActivity.this,
                        listener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE));
                dialog.show();
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
                String date = dayOfMonth + "/" + ((month < 10) ? "0" + month : month) + "/" + year;
                txt_datepicker.setText(date);
            }
        };
//        if (ischeckedOut) {
//            guestName.setTextColor(Color.parseColor("#A9A9A9"));
//            branchName.setTextColor(Color.parseColor("#A9A9A9"));
//            roomNo.setTextColor(Color.parseColor("#A9A9A9"));
//            txt_datepicker.setTextColor(Color.parseColor("#A9A9A9"));
//            btn_Submit.setVisibility(View.GONE);
//            imgCalender.setVisibility(View.GONE);
//            layDatePicket.setBackground(null);
//            txt_datepicker.setText(_appPrefs.getKeyCheckedoutDate());
//
//            reasonForExitSpinner.setVisibility(View.GONE);
//            lblreason.setText(_appPrefs.getKeyReason());
//            layReasonSpinner.setVisibility(View.GONE);
//
//            bankName.setBackground(null);
//            bankName.setEnabled(false);
//            bankName.setText(_appPrefs.getKeyBankName());
//
//            iFSC.setBackground(null);
//            iFSC.setEnabled(false);
//            iFSC.setText(_appPrefs.getKeyIfsc());
//
//            accName.setBackground(null);
//            accName.setEnabled(false);
//            accName.setText(_appPrefs.getAccHolderName());
//
//            accNo.setBackground(null);
//            accNo.setEnabled(false);
//            accNo.setText(_appPrefs.getAccNo());
//        }
        //else {
//            btn_Submit.setVisibility(View.VISIBLE);
//            imgCalender.setVisibility(View.VISIBLE);
//        }
//            layDatePicket.setBackgroundResource(R.drawable.border_style);
//            bankName.setBackgroundResource(R.drawable.border_style);
//            iFSC.setBackgroundResource(R.drawable.border_style);
//            accName.setBackgroundResource(R.drawable.border_style);
//            accNo.setBackgroundResource(R.drawable.border_style);
    }

    private void CheckAndSubmitForm() {
        if (TextUtils.isEmpty(txt_datepicker.getText()) || TextUtils.isEmpty(iFSC.getText())
                || TextUtils.isEmpty(bankName.getText()) || TextUtils.isEmpty(accName.getText())
                || TextUtils.isEmpty(accNo.getText())) {
            showSnackbar("All fields are mandatory");

        } else {
            showProgress(true);
            btn_Submit.setClickable(false);
            CheckOutRequest req = new CheckOutRequest();
            req.setAccountHolderName(accName.getText().toString());
            //req.setAccountNo(getAccountNumber(accNo.getText().toString()));
            req.setAccountNo(accNo.getText().toString());
            req.setBankName(bankName.getText().toString());
            req.setCheckoutDate(txt_datepicker.getText().toString());
            req.setIFSC(iFSC.getText().toString());
            req.setRequestedBy(_appPrefs.getResidentId());
            req.setResidentDetailsId(_appPrefs.getResidentId());
            req.setRequestedVia("AndriodMobileApp");
            req.setReasonId(reasonForExitSpinner.getSelectedItemPosition() + 1);

            final String API_KEY = Global.BASE_URL + "ResidentCheckoutRequest";
            final ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<CheckOutResponse> call = apiService.residentCheckOutRequest(API_KEY, req);

            call.enqueue(new Callback<CheckOutResponse>() {
                @Override
                public void onResponse(Call<CheckOutResponse> call, Response<CheckOutResponse> response) {
                    _appPrefs.saveCheckOutReqDate(txt_datepicker.getText().toString());
                    _appPrefs.saveBankName(bankName.getText().toString());
                    _appPrefs.saveAccHolderName(accName.getText().toString());
                    _appPrefs.saveAccNo(accNo.getText().toString());
                    _appPrefs.saveIFSC(iFSC.getText().toString());
                    _appPrefs.saveReason(reasonForExitSpinner.getSelectedItem().toString());
                    int code = response.code();
                    if (response.body() != null) {
                        _appPrefs.saveIsCheckOut(response.body().getIsValid());
                        showSnackbar(response.body().getResult());
                        _appPrefs.saveIsCheckOut(true);
                    }
//                    else if (response.errorBody().toString() != null)
//                    {
//                        String val = response.errorBody().toString();
//                    }
                    else {
//                                JSONObject jObjError = new JSONObject(response.errorBody());
                        showSnackbar("Error in Submit");
                        _appPrefs.saveIsCheckOut(false);
                        btn_Submit.setClickable(true);
                    }
                }

                @Override
                public void onFailure(Call<CheckOutResponse> call, Throwable t) {
                    showSnackbar("Something went wrong");
                    _appPrefs.saveIsCheckOut(false);
                    btn_Submit.setClickable(true);
                }
            });
            showProgress(false);
        }
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