package com.solodroid.androidnewsapp.utils;

import android.content.Context;
import android.content.res.Resources;
import com.solodroid.androidnewsapp.R;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils {

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    /*
    input rawJsonDate: 0,0,0,2,36,23 (0 year, 0 month, 0 day, 2 hours, 36 minutes, 23 seconds)
    output: return "2 hours ago"
     */
    public static String convertTime (Context ctx, String rawJsonDate){
        String array_date[] = rawJsonDate.split(",");
        String time = ctx.getResources().getString(R.string.second_ago);

        try {
            if (!array_date[0].equals(new String("0"))){
                time = new String (array_date[0]+" " + ctx.getResources().getString(R.string.year_ago));
                return time;
            } else if (!array_date[1].equals(new String("0"))){
                time = new String (array_date[1]+" " + ctx.getResources().getString(R.string.month_ago));
                return time;
            } else if (!array_date[2].equals(new String("0"))){
                time = new String (array_date[2]+" " + ctx.getResources().getString(R.string.day_ago));
                return time;
            } else if (!array_date[3].equals(new String("0"))){
                time = new String (array_date[3]+" " + ctx.getResources().getString(R.string.hour_ago));
                return time;
            } else if (!array_date[4].equals(new String("0"))) {
                time = new String(array_date[4] + " " + ctx.getResources().getString(R.string.minute_ago));
                return time;
            } else {
               return time;
            }

        } catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
        }
        return time;
    }
}