package com.tao.isthara.Activities.Home.Fragments;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tao.isthara.Factory.TextView.RobotoTextView;
import com.tao.isthara.R;

public class StickyPostFragment extends Fragment {

    String id;
    String date;
    String excerpt;
    String tag;
    int thumbnail;

    RobotoTextView dateTextview;
    RobotoTextView excerptTextView;
    RobotoTextView tagTextView;
    FrameLayout tagLayout;
    public static ImageView thumbnailImageview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(container == null){
            return null;
        }
        else {
            View view = (FrameLayout) inflater.inflate(R.layout.fragment_stickypost, container , false);
            dateTextview = (RobotoTextView) view.findViewById(R.id.fragment_stckypost_date_textview_id);
            dateTextview.setVisibility(View.GONE);

            excerptTextView = (RobotoTextView) view.findViewById(R.id.fragment_stckypost_excerpt_textview_id);
            excerptTextView.setVisibility(View.GONE);

            tagTextView = (RobotoTextView) view.findViewById(R.id.fragment_stckypost_tag_textview_id);
            tagTextView.setVisibility(View.GONE);

            thumbnailImageview = (ImageView) view.findViewById(R.id.fragment_stckypost_imageview_id);
            tagLayout = (FrameLayout) view.findViewById(R.id.fragment_stckypost_tag_layout_id);

            return view;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dateTextview.setText(date);
        if(excerpt!=null) {
            if (excerpt.length() <= 130) {
                excerptTextView.setText(excerpt);
            } else {
                String excerptSub = excerpt.substring(0, 128);
                excerptSub = excerptSub + "...";
                excerptTextView.setText(excerptSub);
            }
        }
        TypedArray colors = getResources().obtainTypedArray(R.array.mdcolor_300);
        int index = (int) (Math.random() * colors .length());
        int color = colors.getColor(index, Color.BLACK);
        tagTextView.setText(tag);
        thumbnailImageview.setImageResource(thumbnail);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(75);
        tagLayout.setBackground(gd);
        colors.recycle();

    }

    public StickyPostFragment(String mId, String mDate, String mExcerpt, String mTag, int mThumbnail) {
        id = mId;
        date = mDate;
        excerpt = mExcerpt;
        tag = mTag;
        thumbnail = mThumbnail;
    }

    public StickyPostFragment() {
    }
}
