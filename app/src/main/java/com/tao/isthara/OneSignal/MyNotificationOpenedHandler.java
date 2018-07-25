package com.tao.isthara.OneSignal;

import android.content.Intent;
import android.util.Log;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.tao.isthara.Activities.HelpDeskView.Activity.HelpDeskViewActivity;
import com.tao.isthara.Activities.Home.Activity.MainActivity;
import com.tao.isthara.Activities.Splash.Activity.SplashActivity;
import com.tao.isthara.MyApplication;

import org.json.JSONObject;

public class MyNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
    // This fires when a notification is opened by tapping on it.
    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        OSNotificationAction.ActionType actionType = result.action.type;
        JSONObject data = result.notification.payload.additionalData;

        //While sending a Push notification from OneSignal dashboard
        // you can send an addtional data named "activityToBeOpened" and retrieve the value of it and do necessary operation
        //If key is "activityToBeOpened" and value is "AnotherActivity", then when a user clicks
        //on the notification, AnotherActivity will be opened.
        //Else, if we have not set any additional data MainActivity is opened.
        if (data != null) {
            try {
                int mHelpDeskId = data.optInt("HelpDeskId",0);
                String mStatus = data.optString("status");
                String mUserType = data.optString("userType");

                 if (mHelpDeskId>0) {
                    Intent intent = new Intent(MyApplication.getContext(), HelpDeskViewActivity.class);
                     if(mUserType.equals("Resident")) {
                         if(mStatus.equals("New")){
                             intent.putExtra("ISFROM","Sent");
                         }else if(mStatus.equals("Resolved")){
                             intent.putExtra("ISFROM","Available");
                         }else{
                             intent.putExtra("ISFROM","Completed");
                         }
                     }else{
                         intent.putExtra("ISFROM","Resource");
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

        //If we send notification with action buttons we need to specidy the button id's and retrieve it to
        //do the necessary operation.
        if (actionType == OSNotificationAction.ActionType.ActionTaken) {
            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
            if (result.action.actionID.equals("ActionOne")) {
               // Toast.makeText(IntercomChatApplication.getContext(), "ActionOne Button was pressed", Toast.LENGTH_LONG).show();
            } else if (result.action.actionID.equals("ActionTwo")) {
             //   Toast.makeText(IntercomChatApplication.getContext(), "ActionTwo Button was pressed", Toast.LENGTH_LONG).show();
            }
        }
    }
}