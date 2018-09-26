package com.tao.isthara.Activities.Payment.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tao.isthara.Activities.Payment.Activity.PaymentActivity;
import com.tao.isthara.Activities.Payment.Fragment.InvoiceFragement;
import com.tao.isthara.Activities.Payment.Fragment.PaymentFragment;

public class PaymentPagerAdapter extends FragmentPagerAdapter {

    private final Context context;
    private String[] tabTitles = new String[]{"Invoices", "Payments",};

    public PaymentPagerAdapter(Context cntxt, FragmentManager fm) {
        super(fm);
        context = cntxt;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position) {
            case 0:
                return new InvoiceFragement();
            case 1:
                return new PaymentFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
