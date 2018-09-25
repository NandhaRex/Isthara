package com.tao.isthara.Activities.Payment.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tao.isthara.R;

public class InvoiceFragement extends Fragment {

        public InvoiceFragement() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Invoice");
        return rootView;
    }
}
