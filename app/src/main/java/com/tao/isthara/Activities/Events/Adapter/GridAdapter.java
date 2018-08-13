package com.tao.isthara.Activities.Events.Adapter;

/**
 * Created by SDeivasigamani on 2/19/2018.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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
import com.tao.isthara.Model.EventsHeaderImageResponse;
import com.tao.isthara.Model.Records;
import com.tao.isthara.R;
import com.tao.isthara.Rest.ApiClient;
import com.tao.isthara.Rest.ApiInterface;
import com.tao.isthara.Utils.AppPreferences;
import com.tao.isthara.Utils.Global;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridAdapter extends BaseAdapter {
    private ArrayList<Records> recordList;
    private AppPreferences _appPrefs;
    String[] result;
    Context context;
    int[] imageId;
    private ArrayList<String> imgList;
    private static LayoutInflater inflater = null;

    public GridAdapter(EventListActivity elActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result = prgmNameList;
        context = elActivity;
        imageId = prgmImages;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _appPrefs = new AppPreferences(elActivity.getApplicationContext());
    }

    public GridAdapter(AppCompatActivity activity, ArrayList<Records> records) {
        context = activity;
        recordList = records;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _appPrefs = new AppPreferences(activity.getApplicationContext());

        for (Records val : records) {
            imgList = val.getDocumentData();
        }
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return recordList.size();
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

    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.item_event_list, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textView1);
        holder.img = (ImageView) rowView.findViewById(R.id.imageView1);

        // holder.tv.setText(result[position]);
        byte[] decodedString = Base64.decode(imgList.get(position), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.img.setImageBitmap(decodedByte);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final int imageId = recordList.get(position).getEventDetailsId();


                Intent i = new Intent(context, EventDetailsActivity.class);
                i.putExtra("image",imageId);
                context.startActivity(i);
//                final String API_KEY = Global.BASE_URL + "GetEventDetailsImageByEventDetails_Id?EventDetails_Id=" + imageId;
//
//                final ApiInterface apiService =
//                        ApiClient.getClient().create(ApiInterface.class);
//
//                Call<EventsHeaderImageResponse> call = apiService.getEventsHeaderImage(API_KEY);
//                call.enqueue(new Callback<EventsHeaderImageResponse>() {
//                    @Override
//                    public void onResponse(Call<EventsHeaderImageResponse> call, Response<EventsHeaderImageResponse> response) {
//                        int respCode = response.code();
//                        if (response.body().getHeaderImage().size() > 0) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<EventsHeaderImageResponse> call, Throwable t) {
//
//                    }
//                });
//                    Intent i = new Intent(context, EventDetailsActivity.class);
//                    i.putExtra(,"")
                // context.startActivity(i);
//                if (result[position].equals("ZUMBA")) {
//                } else if (result[position].equals("YOGA")) {
//
//                } else {
//                    //Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
//                }
            }
        });

        return rowView;
    }

}
