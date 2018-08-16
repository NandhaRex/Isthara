package com.tao.isthara.Activities.Checkout.Activity;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.icu.text.RelativeDateTimeFormatter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.tao.isthara.R;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;

public class CheckoutActivity extends AppCompatActivity {

    private TextView lbl_bankText;
    private EditText txt_datepicker;
    private DatePickerDialog.OnDateSetListener listener;
    private Calendar calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        calender = Calendar.getInstance();
        lbl_bankText = (TextView) findViewById(R.id.lbl_bankName);
//        String bank_text = "Bank Details (for refund if any)";
//        SpannableString spannableString = new SpannableString(bank_text);
//        spannableString.setSpan(new StyleSpan(Typeface.BOLD),0,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new ,0,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new StyleSpan(Typeface.NORMAL),12,31,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txt_datepicker.setText(dayOfMonth+"-"+month+"-"+year);
            }
        };

        lbl_bankText.setText(Html.fromHtml("<b>Bank Details</b> (for refund if any)"));
        txt_datepicker = (EditText) findViewById(R.id.txt_Date);
        txt_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(CheckoutActivity.this,
                        listener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DATE));
                dialog.show();
            }
        });
    }
}
