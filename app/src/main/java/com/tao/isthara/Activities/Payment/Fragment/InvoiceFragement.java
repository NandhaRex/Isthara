package com.tao.isthara.Activities.Payment.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tao.isthara.Activities.Payment.Adapter.MonthlyInvoiceListAdapter;
import com.tao.isthara.Model.CurrentMonthDueResponse;
import com.tao.isthara.Model.LastFiveMonthDueResponse;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.AppPreferences;
import com.tao.isthara.Utils.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceFragement extends Fragment {

    private final ApiInterface apiService;
    private AppPreferences _appPrefs;
    private TextView lastMonthDue;
    private ListView monthlyInvoiceListView;

    public InvoiceFragement() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invoice, container, false);
        _appPrefs = new AppPreferences(container.getContext());
        lastMonthDue = (TextView) rootView.findViewById(R.id.due_Amount);
        monthlyInvoiceListView = (ListView) rootView.findViewById(R.id.monthlyInvoiceList);
        GetAndSetDueAmount();
        GetMonthlyInvoiceList();
        return rootView;
    }

    private void GetMonthlyInvoiceList() {
        final String API_KEY = Global.BASE_URL + "GetLastFiveMonthsDueAmountByResidentDetailsId?ResidentDetailsId=" + _appPrefs.getResidentId();
        //final String API_KEY = Global.BASE_URL + "GetLastFiveMonthsDueAmountByResidentDetailsId?ResidentDetailsId=558";

        Call<LastFiveMonthDueResponse> call = apiService.getLastFiveMonthDue(API_KEY);
        call.enqueue(new Callback<LastFiveMonthDueResponse>() {
            @Override
            public void onResponse(Call<LastFiveMonthDueResponse> call, Response<LastFiveMonthDueResponse> response) {
                if (response.body() != null) {
                    MonthlyInvoiceListAdapter adapter = new MonthlyInvoiceListAdapter(getActivity().getApplicationContext(), response.body().getResult());
                    monthlyInvoiceListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<LastFiveMonthDueResponse> call, Throwable t) {

            }
        });

    }

    private void GetAndSetDueAmount() {
        final String API_KEY = Global.BASE_URL + "GetCurrentMonthDueAmountByResidentDetailsId?ResidentDetailsId=" + _appPrefs.getResidentId();
        //final String API_KEY = Global.BASE_URL + "GetCurrentMonthDueAmountByResidentDetailsId?ResidentDetailsId=558";

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
