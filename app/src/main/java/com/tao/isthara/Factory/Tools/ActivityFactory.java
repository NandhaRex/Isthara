package com.tao.isthara.Factory.Tools;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.Date;

public class ActivityFactory {

    public void takeScreenshot(AppCompatActivity activity, ImageView screenHolder) {
        Date now = new Date();
        ImageFactory imageFactory = new ImageFactory();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            // create bitmap screen capture
            View v1 = activity.getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            Bitmap screenShot = imageFactory.fastblur(bitmap,0.8f,16);
            screenHolder.setImageBitmap(screenShot);
            v1.setDrawingCacheEnabled(false);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

}
