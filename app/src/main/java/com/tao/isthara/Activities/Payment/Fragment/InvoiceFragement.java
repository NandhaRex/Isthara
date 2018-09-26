package com.tao.isthara.Activities.Payment.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tao.isthara.Model.CurrentMonthDueResponse;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.AppPreferences;
import com.tao.isthara.Utils.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceFragement extends Fragment {

    private AppPreferences _appPrefs;
    private TextView lastMonthDue;

    public InvoiceFragement() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invoice, container, false);
        _appPrefs = new AppPreferences(container.getContext());
        lastMonthDue = (TextView) rootView.findViewById(R.id.due_Amount);
        GetAndSetDueAmount();
        return rootView;
    }

    private void GetAndSetDueAmount() {
        final String API_KEY = Global.BASE_URL + "GetCurrentMonthDueAmountByResidentDetailsId?ResidentDetailsId=" + _appPrefs.getResidentId();
        //final String API_KEY = Global.BASE_URL + "GetCurrentMonthDueAmountByResidentDetailsId?ResidentDetailsId=558";

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CurrentMonthDueResponse> call = apiService.getLastMonthDue(API_KEY);
        call.enqueue(new Callback<CurrentMonthDueResponse>() {
            @Override
            public void onResponse(Call<CurrentMonthDueResponse> call, Response<CurrentMonthDueResponse> response) {
                if (response != null && response.body().getIsValid()) {
                    lastMonthDue.setText(response.body().getResult());
                } else {
                    lastMonthDue.setText("00.0");
                }
            }

            @Override
            public void onFailure(Call<CurrentMonthDueResponse> call, Throwable t) {

            }
        });
    }
}
