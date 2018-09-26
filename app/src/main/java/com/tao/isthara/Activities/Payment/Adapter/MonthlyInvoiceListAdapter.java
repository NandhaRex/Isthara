package com.tao.isthara.Activities.Payment.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tao.isthara.Activities.Home.Adapters.GridAdapter;
import com.tao.isthara.Model.Result;
import com.tao.isthara.R;
import com.tao.isthara.Utils.AppPreferences;

import java.util.List;

public class MonthlyInvoiceListAdapter extends BaseAdapter {

    private final List<Result> invoiceList;
    private final Context context;
    private final LayoutInflater inflater;
    private final AppPreferences _appPrefs;

    public MonthlyInvoiceListAdapter(Context context, List<Result> invoiceList) {
        this.context = context;
        this.invoiceList = invoiceList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _appPrefs = new AppPreferences(context);
    }

    @Override
    public int getCount() {
        return invoiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Holder holder = new Holder();
        View rootView;
        rootView = inflater.inflate(R.layout.invoice_list_item, null);
        TextView dueMonth = (TextView) rootView.findViewById(R.id.dueMonth);
        TextView dueAmount = (TextView) rootView.findViewById(R.id.dueAmount);
        TextView dueYear = (TextView) rootView.findViewById(R.id.dueYear);

        try {
            dueYear.setText(String.valueOf(invoiceList.get(position).getInvoiceDueYear()));
            dueMonth.setText(invoiceList.get(position).getInvoiceDueMonthName());
            dueAmount.setText("Rs. "+ invoiceList.get(position).getInvoiceDueAmount());
        } catch (Exception ec) {
            Log.e("eererer", ec.getMessage());
        }

        return rootView;
    }

    public class Holder {
        TextView dueMonth;
        TextView dueYear;
        Button downloadPDF;
        TextView dueAmount;
    }
}
