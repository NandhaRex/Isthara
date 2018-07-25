package com.tao.isthara.Activities.Events.Adapter;

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
import com.tao.isthara.Activities.Events.Activity.EventDetailsActivity;
import com.tao.isthara.Activities.Events.Activity.EventListActivity;
import com.tao.isthara.Activities.FoodMenu.Activity.FoodMenuActivity;
import com.tao.isthara.Activities.HelpDeskCreateNew.Activity.HelpDeskCreateNewActivity;
import com.tao.isthara.Activities.HelpDeskList.Activity.HelpDeskListActivity;
import com.tao.isthara.Activities.Home.Activity.MainActivity;
import com.tao.isthara.Activities.Login.Activity.LoginActivity;
import com.tao.isthara.Activities.Profile.ProfileActivity;
import com.tao.isthara.R;
import com.tao.isthara.Utils.AppPreferences;

public class GridAdapter extends BaseAdapter{
    private AppPreferences _appPrefs;
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public GridAdapter(EventListActivity elActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=elActivity;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _appPrefs = new AppPreferences(elActivity.getApplicationContext());

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

        rowView = inflater.inflate(R.layout.item_event_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(result[position].equals("ZUMBA")){
                    Intent i = new Intent(context, EventDetailsActivity.class);
                    context.startActivity(i);
                }else if(result[position].equals("YOGA")){

                }else {
                    //Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
                }
            }
        });

        return rowView;
    }

}
