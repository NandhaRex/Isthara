package com.tao.isthara.Activities.Home.Adapters;

/**
 * Created by SDeivasigamani on 2/19/2018.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.onesignal.OneSignal;
import com.tao.isthara.Activities.Checkout.Activity.CheckoutActivity;
import com.tao.isthara.Activities.Events.Activity.EventListActivity;
import com.tao.isthara.Activities.FoodMenu.Activity.FoodMenuActivity;
import com.tao.isthara.Activities.HelpDeskList.Activity.HelpDeskListActivity;
import com.tao.isthara.Activities.Login.Activity.LoginActivity;
import com.tao.isthara.Activities.Home.Activity.MainActivity;
import com.tao.isthara.Activities.Profile.ProfileActivity;
import com.tao.isthara.R;
import com.tao.isthara.Utils.AppPreferences;

public class GridAdapter extends BaseAdapter{
    private AppPreferences _appPrefs;
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public GridAdapter(MainActivity mainActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _appPrefs = new AppPreferences(mainActivity.getApplicationContext());

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.item_main_menu, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(result[position].equals("HELP DESK")){
                    Intent i = new Intent(context, HelpDeskListActivity.class);
                    context.startActivity(i);
                }else if(result[position].equals("FOOD MENU")){
                    Intent i = new Intent(context, FoodMenuActivity.class);
                    context.startActivity(i);
                }else if(result[position].equals("EVENTS")){
                    Intent i = new Intent(context, EventListActivity.class);
                    context.startActivity(i);
                }else if(result[position].equals("LOGOUT")){
                    FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
                            .setTextTitle("LOGOUT")
                            .setTextSubTitle("Do you really want to logout?")
                            .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                                @Override
                                public void OnClick(View view, Dialog dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButtonText("NO")
                            .setPositiveButtonText("YES")
                            .setPositiveColor(R.color.colorPrimaryDark)
                            .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                                @Override
                                public void OnClick(View view, Dialog dialog) {
                                    _appPrefs.saveUserIsVerified(false);
                                    _appPrefs.saveUserID("");
                                    OneSignal.deleteTag("USERID");
                                    Intent i = new Intent(context, LoginActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(i);

                                }
                            })
                            .setBodyGravity(FancyAlertDialog.TextGravity.LEFT)
                            .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                            .setCancelable(true)
                            .build();
                    alert.show();
                   /* _appPrefs.saveUserIsVerified(false);
                    _appPrefs.saveUserID("");
                    Intent i = new Intent(context, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(i);*/
                }else if(result[position].equals("MY PROFILE")){
                    Intent i = new Intent(context, ProfileActivity.class);
                    context.startActivity(i);
                }else if(result[position].equals("CHECK OUT")){
                    Intent i = new Intent(context, CheckoutActivity.class);
                    context.startActivity(i);
                }else {
                    //Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
                }
            }
        });

        return rowView;
    }

}
