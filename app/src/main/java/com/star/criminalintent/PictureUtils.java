package com.star.criminalintent;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;

public class PictureUtils {

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int widthRatio = (int) (srcWidth / destWidth);
        int heightRatio = (int) (srcHeight / destHeight);

        options.inSampleSize = Math.max(Math.max(widthRatio, heightRatio), 1);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        Display display = activity.getWindowManager().getDefaultDisplay();

        display.getSize(size);

        return getScaledBitmap(path, size.x, size.y);
    }
}
