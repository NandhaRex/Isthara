package com.tao.isthara.Activities.Login.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.msg91.sendotp.library.PhoneNumberFormattingTextWatcher;
import com.msg91.sendotp.library.PhoneNumberUtils;
import com.msg91.sendotp.library.internal.Iso2Phone;
import com.tao.isthara.Activities.Home.Activity.MainActivity;
import com.tao.isthara.Activities.Splash.Activity.SplashActivity;
import com.tao.isthara.Model.LoginResponse;
import com.tao.isthara.Model.LoginResponseRecords;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.AppPreferences;
import com.tao.isthara.Utils.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private AppPreferences _appPrefs;
    private CoordinatorLayout mCoordinatorLayout;


    public static final String INTENT_PHONENUMBER = "phonenumber";
    public static final String INTENT_COUNTRY_CODE = "code";

    private EditText mMobileNo;
    private Button mSmsButton;
    private String mCountryIso;
    private TextWatcher mNumberTextWatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _appPrefs = new AppPreferences(getApplicationContext());

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        mMobileNo = (EditText) findViewById(R.id.phoneNumber);
        mMobileNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    handled = true;
                }
                return handled;
            }
        });
        mSmsButton = (Button) findViewById(R.id.smsVerificationButton);

        mCountryIso = PhoneNumberUtils.getDefaultCountryIso(this);

        resetNumberTextWatcher(mCountryIso);
        tryAndPrefillPhoneNumber();

    }

    private void tryAndPrefillPhoneNumber() {
        if (checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            mMobileNo.setText(manager.getLine1Number());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            tryAndPrefillPhoneNumber();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "This application needs permission to read your phone number to automatically "
                        + "pre-fill it", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openActivity(String phoneNumber) {
        Intent verification = new Intent(this, OTPVerificationActivity.class);
        verification.putExtra(INTENT_PHONENUMBER, phoneNumber);
        verification.putExtra(INTENT_COUNTRY_CODE, Iso2Phone.getPhone(mCountryIso));
        startActivity(verification);
        finish();
    }

    private void setButtonsEnabled(boolean enabled) {
        mSmsButton.setEnabled(enabled);
    }

    public void onButtonClicked(View view) {
        attemptLogin();

    }

    private void resetNumberTextWatcher(String countryIso) {

        if (mNumberTextWatcher != null) {
            mMobileNo.removeTextChangedListener(mNumberTextWatcher);
        }

        mNumberTextWatcher = new PhoneNumberFormattingTextWatcher(countryIso) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                super.beforeTextChanged(s, start, count, after);
            }

            @Override
            public synchronized void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (isPossiblePhoneNumber()) {
                    setButtonsEnabled(true);
                    mMobileNo.setTextColor(Color.BLACK);
                } else {
                    setButtonsEnabled(false);
                    mMobileNo.setTextColor(Color.RED);
                }
            }
        };

        mMobileNo.addTextChangedListener(mNumberTextWatcher);
    }

    private boolean isPossiblePhoneNumber() {
        return PhoneNumberUtils.isPossibleNumber(mMobileNo.getText().toString(), mCountryIso);
    }

    private String getE164Number() {
        return mMobileNo.getText().toString().replaceAll("\\D", "").trim();
        // return PhoneNumberUtils.formatNumberToE164(mPhoneNumber.getText().toString(), mCountryIso);
    }



    private void getLoginAuth(String mMobileNo) {
        final String API_KEY = Global.BASE_URL + "GetUserByMobileNo?MobileNo=" + mMobileNo;

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<LoginResponse> call = apiService.getLoginResponse(API_KEY);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                int statusCode = response.code();
                //showProgress(false);
                String responsMsg = response.body().getMessageText();


                if (responsMsg.equals("Success")) {
                    LoginResponseRecords lr = response.body().getRecords();
                    boolean isActive = lr.isIsActive();
                    if(isActive) {
                        _appPrefs.saveUserID(lr.getUserId());
                        _appPrefs.saveBlockID("" + lr.getBlockMaster().getBlockId());
                        _appPrefs.saveUserType(lr.getUserType());

                        openActivity(getE164Number());

                        /*Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);*/
                    }else{
                        showSnackbar("You access denied.");
                    }
                } else {
                    showSnackbar(responsMsg);
                }
                //LoginResponseRecords resposeResult = response.body().getRecords();


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //showProgress(false);
                showSnackbar("Can't connect to server.");
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void showSnackbar(String msg) {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mMobileNo.getWindowToken(), 0);

        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "" + msg, Snackbar.LENGTH_LONG);
                  /*  View view = snackbar.getView();
                    view.setBackgroundColor(Color.WHITE);*/

        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        if (!validate()) {
            //onLoginFailed();
            return;
        }

        if (isNetworkConnected()) {

            //showProgress(true);
            getLoginAuth(getE164Number());

        } else {
            showSnackbar("No Internet Connection!");
        }

    }

    public boolean validate() {
        boolean valid = true;

        // Store values at the time of the login attempt.
        String mobile = getE164Number();

        if (mobile.isEmpty()) {
            mMobileNo.setError("Please enter your mobile number.");
            valid = false;
        } else {
            mMobileNo.setError(null);
        }


        return valid;
    }
}

