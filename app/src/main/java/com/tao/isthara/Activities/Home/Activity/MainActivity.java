package com.tao.isthara.Activities.Home.Activity;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;


import com.onesignal.OneSignal;
import com.tao.isthara.Activities.Home.Adapters.GridAdapter;
import com.tao.isthara.Activities.Home.Adapters.PagerAdapter;
import com.tao.isthara.Activities.Home.Fragments.StickyPostFragment;
import com.tao.isthara.OneSignal.MyNotificationOpenedHandler;
import com.tao.isthara.OneSignal.MyNotificationReceivedHandler;
import com.tao.isthara.R;
import com.tao.isthara.Utils.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


public class MainActivity extends AppCompatActivity {

    public static AppCompatActivity activity;
    AutoScrollViewPager stickyPostsViewPager;
    PagerAdapter stickyPostspPagerAdapter;
    List<Fragment> stickyPostsList = new ArrayList<Fragment>();
    LinearLayout firstIndicator;
    LinearLayout secondIndicator;
    LinearLayout thirdIndicator;
    LinearLayout fourthIndicator;
    LinearLayout fifthIndicator;


    FrameLayout sliderParent;
    //FrameLayout mainToolbarParentLayout;
    NestedScrollView scrollView;

    //FrameLayout searchHolder;
    //ImageView searchImage;

    String[] sliderExcerpts = new String[]{
            "We’ve read ALL the guides to decluttering – and you probably have too. If you’re like us, they’re taking up space in the third drawer down – that famous one where you put the things",
            "With its myriad hills and spectacular bay, San Francisco beguiles with natural beauty, vibrant neighborhoods, and contagious energy",
            "I think ‘photogenic’ doesn't have to do with the way people look, but instead how they feel and behave in front of the camera,” says Adler.",
            "With its myriad hills and spectacular bay, San Francisco beguiles with natural beauty, vibrant neighborhoods, and contagious energy",
            "I think ‘photogenic’ doesn't have to do with the way people look, but instead how they feel and behave in front of the camera,” says Adler."
    };
    String[] sliderDates = new String[]{
            "FEB 14, 2018 8:30PM",
            "FEB 14, 2018 6:25PM",
            "FEB 16, 2018 6:25PM",
            "FEB 14, 2018 6:25PM",
            "FEB 16, 2018 6:25PM"
    };
    String[] sliderTags = new String[]{
            "Lifestyle",
            "events",
            "Offer",
            "events",
            "Offer"
    };

    int[] sliderThumbnails = {
            R.mipmap.banner1,
            R.mipmap.banner2,
            R.mipmap.banner3,
            R.mipmap.banner4,
            R.mipmap.banner5
    };

    GridView gv;
    private AppPreferences _appPrefs;
    Context context;
    ArrayList prgmName;
    public static String[] prgmNameList = {"HELP DESK", "FOOD MENU", "CHECK OUT", "EVENTS", "PAYMENTS", "REFERRALS", "MY PROFILE", "LOGOUT"};
    public static int[] prgmImages = {R.drawable.ic_helpdesk, R.drawable.ic_food_menu, R.drawable.ic_residents_off, R.drawable.ic_events, R.drawable.ic_payments, R.drawable.ic_referrals_off, R.drawable.ic_myprofile, R.drawable.ic_logout};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(true);
        setContentView(R.layout.activity_main);
        //InitializeToolbar();
        activity = this;


        _appPrefs = new AppPreferences(getApplicationContext());

        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                .init();
        OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);
        OneSignal.sendTag("USERID", _appPrefs.getUserID());

        stickyPostsViewPager = (AutoScrollViewPager) findViewById(R.id.main_activity_sticky_posts_viewpager_id);
        firstIndicator = (LinearLayout) findViewById(R.id.fragment_stckypost_indicator_first);
        secondIndicator = (LinearLayout) findViewById(R.id.fragment_stckypost_indicator_second);
        thirdIndicator = (LinearLayout) findViewById(R.id.fragment_stckypost_indicator_third);
        fourthIndicator = (LinearLayout) findViewById(R.id.fragment_stckypost_indicator_fourth);
        fifthIndicator = (LinearLayout) findViewById(R.id.fragment_stckypost_indicator_fifith);
        sliderParent = (FrameLayout) findViewById(R.id.main_activity_slider_parent_layout_id);
        //mainToolbarParentLayout = (FrameLayout) findViewById(R.id.main_activity_toolbar_parent_layout_id);
        //searchHolder = (FrameLayout) findViewById(R.id.search_activity_search_holder_frame_id);
        //searchImage = (ImageView) findViewById(R.id.search_activity_search_holder_image_id);

        scrollView = (NestedScrollView) findViewById(R.id.main_activity_scrollview_id);
        stickyPostsList.clear();
        for (int i = 0; i < sliderExcerpts.length; i++) {
            StickyPostFragment fragment = new StickyPostFragment(String.valueOf(i), sliderDates[i], sliderExcerpts[i], sliderTags[i], sliderThumbnails[i]);
            stickyPostsList.add(fragment);
        }
        stickyPostspPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), stickyPostsList);
        stickyPostsViewPager.setAdapter(stickyPostspPagerAdapter);
        stickyPostsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    firstIndicator.setBackgroundResource(R.drawable.indicator_circle_focus);
                    secondIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    thirdIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    fourthIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    fifthIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                } else if (position == 1) {
                    firstIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    secondIndicator.setBackgroundResource(R.drawable.indicator_circle_focus);
                    thirdIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    fourthIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    fifthIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                } else if (position == 2) {
                    firstIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    secondIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    thirdIndicator.setBackgroundResource(R.drawable.indicator_circle_focus);
                    fourthIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    fifthIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                } else if (position == 3) {
                    firstIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    secondIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    thirdIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    fourthIndicator.setBackgroundResource(R.drawable.indicator_circle_focus);
                    fifthIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                } else if (position == 4) {
                    firstIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    secondIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    thirdIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    fourthIndicator.setBackgroundResource(R.drawable.indicator_circle_unfocus);
                    fifthIndicator.setBackgroundResource(R.drawable.indicator_circle_focus);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        stickyPostsViewPager.setInterval(4000);
        stickyPostsViewPager.getSlideBorderMode();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stickyPostsViewPager.startAutoScroll();
            }
        }, 400);


        scrollView.post(new Runnable() {
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        });


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        /*final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sliderParent.getLayoutParams();
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                if(360 - scrollY / 2.9 >=0) {
                    params.height = (int) (360 - scrollY / 2.9);
                    sliderParent.setLayoutParams(params);
                }
                if(scrollY / (320) <= 1) {
                    mainToolbarParentLayout.setAlpha((float) scrollY / (320));
                }

            }


        });*/

        gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter(new GridAdapter(this, prgmNameList, prgmImages));

    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    @Override
    protected void onPause() {
        for (int i = 0; i < stickyPostsList.size(); i++) {
            stickyPostsList.get(i).onPause();
        }
        stickyPostsViewPager.stopAutoScroll();
        super.onPause();
    }

    @Override
    protected void onStop() {
        for (int i = 0; i < stickyPostsList.size(); i++) {
            stickyPostsList.get(i).onStop();
        }
        stickyPostsViewPager.stopAutoScroll();
        super.onStop();
    }

    @Override
    protected void onResume() {
        for (int i = 0; i < stickyPostsList.size(); i++) {
            stickyPostsList.get(i).onResume();
        }
        stickyPostsViewPager.startAutoScroll();
        super.onResume();
    }
}
