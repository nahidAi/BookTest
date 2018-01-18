package com.test.booktestnotification;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;



public class G extends Application {
    private static Typeface font;
   public static Context context;
   public static AlarmManager alarmManager;
    @Override
    public void onCreate() {
        super.onCreate();
        font = Typeface.createFromAsset(getAssets(),"irsans_font.ttf");
        context = getApplicationContext();
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }
    public Typeface getFont(){
        return font;
    }
}
