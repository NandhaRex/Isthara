package com.tao.isthara.Activities.HelpDeskCreateNew.Activity;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tao.isthara.Activities.HelpDeskCreateNew.Adapters.SingleSelectionAdapter;
import com.tao.isthara.Model.ItemModel;
import com.tao.isthara.Model.SubCategories;
import com.tao.isthara.Model.SubCategoriesItem;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.Global;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoriesRecyclerViewActivity extends AppCompatActivity{
    Context mContext;
    RecyclerView mRecyclerView;

    Button selected;

    private String tag;
    String names[];

    SingleSelectionAdapter adapterSingle;

    private ConstraintLayout mCoordinatorLayout;
    List<SubCategoriesItem> mSubCategoriesList = new ArrayList<>();

    int mCategoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories_recyclerview);
        mContext =this;

        initView();

        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedClick();
            }
        });
    }

    private void initView() {

        getSupportActionBar().setTitle("Select a Service");
        getSupportActionBar().setElevation(0);

        mCoordinatorLayout = (ConstraintLayout) findViewById(R.id.coordinatorLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selected = (Button) findViewById(R.id.selected);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tag = bundle.getString("TAG");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mCategoryID = getIntent().getExtras().getInt("CATEGORYID", 0);

        getSubCategories(mCategoryID);


    }

    private List getList() {
        List list = new ArrayList<>();
        for(int i=0;i<mSubCategoriesList.size();i++){
            ItemModel model = new ItemModel();
            model.setName(mSubCategoriesList.get(i).getText());
            model.setId(mSubCategoriesList.get(i).getValue());
            list.add(model);
        }
        return list;
    }

    public void selectedClick() {
        if (tag.equalsIgnoreCase("Single")) {
            if (adapterSingle.selectedPosition() != -1) {
                ItemModel itemModel = adapterSingle.getSelectedItem();
                String cityName = itemModel.getName();
                showToast("Selected SubCategories is: " + cityName);
            } else {
                showToast("Please select any subcategory");
            }
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getSubCategories(int mCategoryID) {
        final String API_KEY = Global.BASE_URL + "GetSubCategoryListByCategoryId?CategoryId="+mCategoryID;

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<SubCategories> call = apiService.getSubCategories(API_KEY);
        call.enqueue(new Callback<SubCategories>() {
            @Override
            public void onResponse(Call<SubCategories> call, Response<SubCategories> response) {
                int statusCode = response.code();
                String responsMsg = response.body().getMessageText();
                if (responsMsg.equals("Success")) {
                    mSubCategoriesList = response.body().getRecords();
                    names= new String[mSubCategoriesList.size()];
                    for(int i=0;i<mSubCategoriesList.size();i++){
                        names[i] = mSubCategoriesList.get(i).getText();
                    }

                    List list = getList();
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    adapterSingle = new SingleSelectionAdapter(mContext, list, SubCategoriesRecyclerViewActivity.this);
                    mRecyclerView.setAdapter(adapterSingle);

                } else {
                    showSnackbar(responsMsg);
                }
                //LoginResponseRecords resposeResult = response.body().getRecords();


            }

            @Override
            public void onFailure(Call<SubCategories> call, Throwable t) {
                //showSnackbar("Can't connect to server.");
                showSnackbar("No Records.");
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
}

