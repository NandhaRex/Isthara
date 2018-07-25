package com.tao.isthara.Activities.Splash.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tao.isthara.Activities.HelpDeskList.Activity.EMP_HelpDeskListActivity;
import com.tao.isthara.Activities.Home.Activity.MainActivity;
import com.tao.isthara.Activities.Login.Activity.LoginActivity;
import com.tao.isthara.R;
import com.tao.isthara.Utils.AppPreferences;

public class SplashActivity extends AppCompatActivity {
    private AppPreferences _appPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        _appPrefs = new AppPreferences(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                if (_appPrefs.getUserID().equals("") && !(_appPrefs.getUserIsVerified())) {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    if(_appPrefs.getUserType().equals("Resident")){
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }else {
                        Intent i = new Intent(getApplicationContext(), EMP_HelpDeskListActivity.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }

            }
        }, 2000);
    }

}
