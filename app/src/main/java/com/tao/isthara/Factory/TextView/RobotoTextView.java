package com.tao.isthara.Factory.TextView;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class RobotoTextView extends TextView {

    public RobotoTextView(Context context) {
        super(context);
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyleAttr)   {
        super(context, attrs, defStyleAttr);
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTypeface(Typeface tf) {
        Typeface face = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto_regular.ttf");
        super.setTypeface(face);
    }

}