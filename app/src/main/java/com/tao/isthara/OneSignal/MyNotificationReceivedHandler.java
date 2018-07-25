package com.tao.isthara.OneSignal;

import android.content.Intent;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OneSignal;
import com.tao.isthara.Activities.HelpDeskView.Activity.HelpDeskViewActivity;
import com.tao.isthara.Activities.Splash.Activity.SplashActivity;
import com.tao.isthara.MyApplication;

import org.json.JSONObject;

/**
 * Created by androidbash on 12/14/2016.
 */

//This will be called when a notification is received while your app is running.
public class MyNotificationReceivedHandler  implements OneSignal.NotificationReceivedHandler {
    @Override
    public void notificationReceived(OSNotification notification) {
        JSONObject data = notification.payload.additionalData;
        String customKey;

        if (data != null) {
            try {
                JSONObject  jsonObject = data.getJSONObject("Data");
                int mHelpDeskId = jsonObject.optInt("HelpDeskId",0);
                String mStatus = jsonObject.optString("status");

                if (mHelpDeskId>0) {
                    Intent intent = new Intent(MyApplication.getContext(), HelpDeskViewActivity.class);
                    if(mStatus.equals("New")){
                        intent.putExtra("ISFROM","Sent");
                    }else if(mStatus.equals("Resolved")){
                        intent.putExtra("ISFROM","Available");
                    }else{
                        intent.putExtra("ISFROM","Completed");
                    }

                    intent.putExtra("HELPDESKID",mHelpDeskId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication.getContext().startActivity(intent);

                }else{
                    Intent intent = new Intent(MyApplication.getContext(), SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication.getContext().startActivity(intent);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }else{
            Intent intent = new Intent(MyApplication.getContext(), SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getContext().startActivity(intent);
        }
    }
}