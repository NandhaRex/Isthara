package com.tao.isthara.Activities.HelpDeskView.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;
import com.tao.isthara.Activities.HelpDeskCreateNew.Activity.CategoriesRecyclerViewActivity;
import com.tao.isthara.Activities.HelpDeskCreateNew.Adapters.SingleSelectionAdapter;
import com.tao.isthara.Model.Categories;
import com.tao.isthara.Model.CloseTicketRequest;
import com.tao.isthara.Model.CloseTicketResponse;
import com.tao.isthara.Model.DeclinedTicketRequest;
import com.tao.isthara.Model.DeclinedTicketResponse;
import com.tao.isthara.Model.IssueDetailsResponse;
import com.tao.isthara.Model.IssueListResponse;
import com.tao.isthara.Model.IssueListResponseRecords;
import com.tao.isthara.Model.Issue_list_item;
import com.tao.isthara.Model.RecordsCount;
import com.tao.isthara.Model.ResolveTicketRequest;
import com.tao.isthara.Model.ResolveTicketResponse;
import com.tao.isthara.Model.SubmitUserRatingResponse;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.AppPreferences;
import com.tao.isthara.Utils.Global;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpDeskViewActivity extends AppCompatActivity implements RatingDialogListener {
    private AppPreferences _appPrefs;
    private ConstraintLayout mCoordinatorLayout;

    TextView txtToken, txtIssueDate, txtStatus, txtDepartment, txtService, txtTitle, txtDesc, txtProperty, txtRoomNo,txtBedName ;
    Button btnResolved, btnCompleted, btnNotOurScope;

    RatingBar ratingBar;
    TextView txtRating;

    String isFrom="";
    int mHelpDeskID, mRating;
    private ProgressBar mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_desk_view);

        getSupportActionBar().setTitle("View Ticket");
        getSupportActionBar().setElevation(0);
        _appPrefs = new AppPreferences(getApplicationContext());

        mCoordinatorLayout = (ConstraintLayout) findViewById(R.id.coordinatorLayout);
        mProgressView  =(ProgressBar) findViewById(R.id.progress);

        isFrom = getIntent().getExtras().getString("ISFROM");
        mHelpDeskID = getIntent().getExtras().getInt("HELPDESKID", 0);
        //mRating = getIntent().getExtras().getInt("RATING", 0);


        txtToken = (TextView) findViewById(R.id.token);
        txtIssueDate = (TextView) findViewById(R.id.issue_date);
        txtStatus = (TextView) findViewById(R.id.status);
        txtDepartment = (TextView) findViewById(R.id.department);
        txtService = (TextView) findViewById(R.id.service);
        txtTitle = (TextView) findViewById(R.id.title);
        txtDesc = (TextView) findViewById(R.id.desc);
        txtProperty = (TextView) findViewById(R.id.property);
        txtRoomNo = (TextView) findViewById(R.id.roomno);
        txtBedName = (TextView) findViewById(R.id.bedname);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRating = (TextView) findViewById(R.id.txt_rating);

        btnCompleted = (Button) findViewById(R.id.btn_completed);
        btnCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    closeTicket();
                } else {
                    showSnackbar("No Internet Connection!");
                }

            }
        });

        btnResolved = (Button) findViewById(R.id.btn_resolved);
        btnResolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    resolveTicket();
                } else {
                    showSnackbar("No Internet Connection!");
                }
            }
        });

        btnNotOurScope = (Button) findViewById(R.id.btn_not_our_scope);
        btnNotOurScope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    declinedTicket();
                } else {
                    showSnackbar("No Internet Connection!");
                }
            }
        });



        getHelpDeskDetails();





    }

    private void showDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Later")
                .setNoteDescriptions(Arrays.asList("Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setDescriptionTextColor(R.color.descriptionTextColor)
                .setTitle("Rate this service.")
                .setDefaultRating(3)
                .setStarColor(R.color.starColor)
                .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                .setTitleTextColor(R.color.titleTextColor)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .create(HelpDeskViewActivity.this)
                .show();



        /*
        app_rate_dialog_comment_edit_text
                .setDescription("Please select some stars and give your feedback")
                .setDefaultRating(3)
                .setDefaultComment("This app is pretty cool !")
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.colorPrimary)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setNegativeButtonText("Cancel")
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.hintTextColor)*/
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
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


    private void closeTicket() {

        final String API_KEY = Global.BASE_URL + "CompleteTicket";

        CloseTicketRequest closeTicketRequest = new CloseTicketRequest();
        closeTicketRequest.setUserId(Integer.parseInt(_appPrefs.getUserID()));
        closeTicketRequest.setHelpDeskId(mHelpDeskID);
        closeTicketRequest.setClosingComments("");
      /*  newTokenRequest.setTitle(mTitle);
        newTokenRequest.setDescription(mDesc);
        newTokenRequest.setUserId(Integer.parseInt(_appPrefs.getUserID()));
        newTokenRequest.setBlockId(Integer.parseInt(_appPrefs.getBlockID()));
        newTokenRequest.setCategoryMasterId(mCategoryID);
        newTokenRequest.setSubCategoryMasterId(mSubCategoryID);*/


        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CloseTicketResponse> call = apiService.getCloseTicketResponse(API_KEY, closeTicketRequest);
        call.enqueue(new Callback<CloseTicketResponse>() {
            @Override
            public void onResponse(Call<CloseTicketResponse> call, Response<CloseTicketResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        String responsMsg = response.body().getMessageText();
                        if (responsMsg.equals("Success")) {
                            FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(HelpDeskViewActivity.this)
                                    .setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black))
                                    .setTextTitle("TICKET CLOSED")
                                    .setBody("")
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
                                    .setSubtitleGravity(FancyAlertDialog.TextGravity.RIGHT)
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
            public void onFailure(Call<CloseTicketResponse> call, Throwable t) {
                showSnackbar("Can't connect to server.");
            }
        });
    }

    private void resolveTicket() {

        final String API_KEY = Global.BASE_URL + "ResolveTicket";

        ResolveTicketRequest resolveTicketRequest = new ResolveTicketRequest();
        resolveTicketRequest.setUserId(Integer.parseInt(_appPrefs.getUserID()));
        resolveTicketRequest.setHelpDeskId(mHelpDeskID);
        resolveTicketRequest.setResolution("");


        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ResolveTicketResponse> call = apiService.getResolveTicketResponse(API_KEY, resolveTicketRequest);
        call.enqueue(new Callback<ResolveTicketResponse>() {
            @Override
            public void onResponse(Call<ResolveTicketResponse> call, Response<ResolveTicketResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        String responsMsg = response.body().getMessageText();
                        if (responsMsg.equals("Success")) {
                            FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(HelpDeskViewActivity.this)
                                    .setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black))
                                    .setTextTitle("TICKET RESOLVED")
                                    .setBody("")
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
                                    .setSubtitleGravity(FancyAlertDialog.TextGravity.RIGHT)
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
            public void onFailure(Call<ResolveTicketResponse> call, Throwable t) {
                showSnackbar("Can't connect to server.");
            }
        });
    }

    private void declinedTicket() {

        final String API_KEY = Global.BASE_URL + "ResolveTicketDeclined";

        DeclinedTicketRequest declinedTicketRequest = new DeclinedTicketRequest();
        declinedTicketRequest.setUserId(Integer.parseInt(_appPrefs.getUserID()));
        declinedTicketRequest.setHelpDeskId(mHelpDeskID);



        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DeclinedTicketResponse> call = apiService.getDeclinedTicketRequest(API_KEY, declinedTicketRequest);
        call.enqueue(new Callback<DeclinedTicketResponse>() {
            @Override
            public void onResponse(Call<DeclinedTicketResponse> call, Response<DeclinedTicketResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        String responsMsg = response.body().getMessageText();
                        if (responsMsg.equals("Success")) {
                            FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(HelpDeskViewActivity.this)
                                    .setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black))
                                    .setTextTitle("TICKET DECLINED")
                                    .setBody("")
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
                                    .setSubtitleGravity(FancyAlertDialog.TextGravity.RIGHT)
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
            public void onFailure(Call<DeclinedTicketResponse> call, Throwable t) {
                showSnackbar("Can't connect to server.");
            }
        });
    }

    private void submitRating(int mHelpDeskID, int mUserRating) {
        final String API_KEY = Global.BASE_URL + "SaveRatingByHelpDeskId?HelpDeskId="+mHelpDeskID+"&Rating="+mUserRating+"";

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<SubmitUserRatingResponse> call = apiService.SubmitRatingResponse(API_KEY);
        call.enqueue(new Callback<SubmitUserRatingResponse>() {
            @Override
            public void onResponse(Call<SubmitUserRatingResponse> call, Response<SubmitUserRatingResponse> response) {
                int statusCode = response.code();
                String responsMsg = response.body().getMessageText();
                if (responsMsg.equals("Success")) {


                } else {
                    showSnackbar(responsMsg);
                }

            }

            @Override
            public void onFailure(Call<SubmitUserRatingResponse> call, Throwable t) {
                //showSnackbar("Can't connect to server.");
                showSnackbar("No Records.");
            }
        });
    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {
        submitRating(mHelpDeskID, i);
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    private void getHelpDeskDetails() {
        showProgress(true);
        final String API_KEY = Global.BASE_URL + "GetHelpDeskDetailsByHelpDeskId?HelpDeskId="+mHelpDeskID;

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<IssueDetailsResponse> call = apiService.getIssueDetailsResponse(API_KEY);
        call.enqueue(new Callback<IssueDetailsResponse>() {
            @Override
            public void onResponse(Call<IssueDetailsResponse> call, Response<IssueDetailsResponse> response) {
                int statusCode = response.code();
                //showProgress(false);
                String responsMsg = response.body().getMessageText();
                if (responsMsg.equals("Success")) {

                    IssueListResponseRecords ilr = response.body().getRecordDetails();


                            txtToken.setText(ilr.getIssueNumber());
                            txtIssueDate.setText(getFormattedDate(ilr.getIssueDate()));
                            txtStatus.setText(ilr.getStatus());
                            txtDepartment.setText(ilr.getCategoryName());
                            txtService.setText(ilr.getSubCategoryName());
                            txtTitle.setText(ilr.getTitle());
                            txtDesc.setText(ilr.getDescription());
                            txtProperty.setText(ilr.getProperty());
                            txtRoomNo.setText(ilr.getRoomNo());
                            txtBedName.setText(ilr.getBedName());

                            mRating = ilr.getRating();

                            if(mRating>0) {
                                ratingBar.setVisibility(View.VISIBLE);
                                txtRating.setVisibility(View.VISIBLE);
                                ratingBar.setRating(mRating);
                                if (mRating == 1) {
                                    txtRating.setText("Bad");
                                } else if (mRating == 2) {
                                    txtRating.setText("Not good");
                                } else if (mRating == 3) {
                                    txtRating.setText("Quite ok");
                                } else if (mRating == 4) {
                                    txtRating.setText("Very Good");
                                } else if (mRating == 5) {
                                    txtRating.setText("Excellent !!!");
                                }
                            }else{
                                ratingBar.setVisibility(View.GONE);
                                txtRating.setVisibility(View.GONE);
                            }

                            if(isFrom.equals("Resource")){
                                btnCompleted.setVisibility(View.GONE);
                                btnResolved.setVisibility(View.VISIBLE);
                                btnNotOurScope.setVisibility(View.GONE);
                                try {
                                    txtStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                }catch (Exception e){

                                }

                            }else if(isFrom.equals("Available")){
                                if(mRating<=0){
                                    showDialog();
                                }
                                btnCompleted.setVisibility(View.GONE);
                                btnResolved.setVisibility(View.GONE);
                                btnNotOurScope.setVisibility(View.GONE);
                                try {
                                    txtStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                                }catch (Exception e){

                                }
                            }else{
                                btnCompleted.setVisibility(View.GONE);
                                btnResolved.setVisibility(View.GONE);
                                btnNotOurScope.setVisibility(View.GONE);
                                try {
                                    txtStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                }catch (Exception e){

                                }
                            }

                    showProgress(false);
                } else {
                    showProgress(false);
                    showSnackbar(responsMsg);
                }
                //LoginResponseRecords resposeResult = response.body().getRecords();


            }

            @Override
            public void onFailure(Call<IssueDetailsResponse> call, Throwable t) {
                //showProgress(false);
                showProgress(false);
                showSnackbar("No Records.");
            }
        });
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
