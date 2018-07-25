package com.tao.isthara.Activities.HelpDeskCreateNew.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.tao.isthara.Model.CategoriesItem;
import com.tao.isthara.Model.NewTokenRequest;
import com.tao.isthara.Model.NewTokenResponse;
import com.tao.isthara.Model.SubCategoriesItem;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.AppPreferences;
import com.tao.isthara.Utils.Global;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpDeskCreateNewActivity extends AppCompatActivity {
    private AppPreferences _appPrefs;
    private ConstraintLayout mCoordinatorLayout;
    private EditText edtTitle, edtDesc, edtCategories, edtSubCategories;
    Button btnRegister;

    private View mProgressView;
    private View mFormView;

    List<CategoriesItem> mCategoriesList = new ArrayList<>();
    List<SubCategoriesItem> mSubCategoriesList = new ArrayList<>();

    private static final int CATEGORY_REQUEST_CODE = 101;
    private static final int SUBCATEGORY_REQUEST_CODE = 102;

    // variable to track event time
    private long mLastClickTime = 0;

    int mCategoryID, mSubCategoryID;
    String mCategoryName="", mSubCategoryName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_desk_create_new);

        getSupportActionBar().setTitle("New Ticket");
        getSupportActionBar().setElevation(0);
        _appPrefs = new AppPreferences(getApplicationContext());

        edtTitle = (EditText) findViewById(R.id.edit_title);
        edtDesc = (EditText) findViewById(R.id.edit_desc);

        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Preventing multiple clicks, using threshold of 1 second
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                registerNewToken();
            }
        });

        mCoordinatorLayout = (ConstraintLayout) findViewById(R.id.coordinatorLayout);

        edtCategories = (EditText) findViewById(R.id.categories);
        edtCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpDeskCreateNewActivity.this, CategoriesRecyclerViewActivity.class);
                startActivityForResult(intent, CATEGORY_REQUEST_CODE);
            }
        });

        edtSubCategories = (EditText) findViewById(R.id.sub_categories);
        edtSubCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCategoryID>0) {
                    Intent intent = new Intent(HelpDeskCreateNewActivity.this, SubCategoriesRecyclerViewActivity.class);
                    intent.putExtra("CATEGORYID", mCategoryID);
                    startActivityForResult(intent, SUBCATEGORY_REQUEST_CODE);
                }
            }
        });




        mFormView = findViewById(R.id.form);
        mProgressView = findViewById(R.id.login_progress);

        //getCategories();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (CATEGORY_REQUEST_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    mCategoryID = data.getIntExtra("SELECTION_ID",0);
                    mCategoryName = data.getStringExtra("SELECTION_NAME");

                    edtCategories.setText(mCategoryName);
                    edtSubCategories.setText("");
                }
                break;
            }
            case (SUBCATEGORY_REQUEST_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    mSubCategoryID = data.getIntExtra("SELECTION_ID",0);
                    mSubCategoryName = data.getStringExtra("SELECTION_NAME");

                    edtSubCategories.setText(mSubCategoryName);
                }
                break;
            }
        }
    }


    private void registerNewToken() {
        if (!validate()) {
            return;
        }

        showProgress(true);
        String mTitle = edtTitle.getText().toString();
        String mDesc = edtDesc.getText().toString();


        final String API_KEY = Global.BASE_URL + "SaveOrUpdateHelpDesk";
        NewTokenRequest newTokenRequest = new NewTokenRequest();
        newTokenRequest.setTitle(mTitle);
        newTokenRequest.setDescription(mDesc);
        newTokenRequest.setUserId(Integer.parseInt(_appPrefs.getUserID()));
        newTokenRequest.setBlockId(Integer.parseInt(_appPrefs.getBlockID()));
        newTokenRequest.setCategoryMasterId(mCategoryID);
        newTokenRequest.setSubCategoryMasterId(mSubCategoryID);


        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<NewTokenResponse> call = apiService.getNewTokenRegister(API_KEY, newTokenRequest);
        call.enqueue(new Callback<NewTokenResponse>() {
            @Override
            public void onResponse(Call<NewTokenResponse> call, Response<NewTokenResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        showProgress(false);
                        String responsMsg = response.body().getMessageText();
                        if (responsMsg.equals("Success")) {
                            FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(HelpDeskCreateNewActivity.this)
                                    .setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black))
                                    .setTextTitle("REGISTERED NEW TICKET")
                                    .setTextSubTitle("Ticket Number: "+ response.body().getRecords().getIssueNumber())
                                    .setBody("Sorry for the inconvenience. We will let you know once the issue is resolved.")
                                    .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                                        @Override
                                        public void OnClick(View view, Dialog dialog) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButtonText("OK")
                                    .setPositiveColor(R.color.colorPrimaryDark)
                                    .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                                        @Override
                                        public void OnClick(View view, Dialog dialog) {
                                            finish();
                                        }
                                    })
                                    .setBodyGravity(FancyAlertDialog.TextGravity.LEFT)
                                    .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                                    .setSubtitleGravity(FancyAlertDialog.TextGravity.CENTER)
                                    .setCancelable(false)
                                    .build();
                            alert.show();
                        } else {
                            showSnackbar(responsMsg);
                        }
                    }
                } catch (Exception e) {
                    // Appropriate error handling code
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<NewTokenResponse> call, Throwable t) {
                showProgress(false);
                showSnackbar("Can't connect to server.");
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String mTitle = edtTitle.getText().toString();
        String mDepartment = edtCategories.getText().toString();
        String mService = edtSubCategories.getText().toString();
        String mDesc = edtDesc.getText().toString();

        if (mTitle.isEmpty()) {
            edtTitle.setError("Please enter issue title.");
            valid = false;
        } else {
            edtTitle.setError(null);
        }

        if (mDepartment.isEmpty()) {
            edtCategories.setError("Please select a department.");
            valid = false;
        } else {
            edtCategories.setError(null);
        }
        if (mCategoryID<=0) {
            edtCategories.setError("Please select a valid department.");
            valid = false;
        } else {
            edtCategories.setError(null);
        }

        if (mService.isEmpty()) {
            edtSubCategories.setError("Please select a service.");
            valid = false;
        } else {
            edtSubCategories.setError(null);
        }

        if (mSubCategoryID<=0) {
            edtCategories.setError("Please select a valid service.");
            valid = false;
        } else {
            edtCategories.setError(null);
        }

        if (mDesc.isEmpty()) {
            edtDesc.setError("Please enter the description.");
            valid = false;
        } else {
            edtDesc.setError(null);
        }

        return valid;
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

            btnRegister.setVisibility(show ? View.GONE : View.VISIBLE);
            btnRegister.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    btnRegister.setVisibility(show ? View.GONE : View.VISIBLE);
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
            btnRegister.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void showSnackbar(String msg) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "" + msg, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
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

}
