package com.test.booktestnotification.Activity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.booktestnotification.Adapter.AdapterFragment;
import com.test.booktestnotification.G;
import com.test.booktestnotification.Main3Activity;
import com.test.booktestnotification.Quote;
import com.test.booktestnotification.R;
import com.test.booktestnotification.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static Context context;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuToolbar;
    SQLiteDatabase database;
    String desPath;
    FloatingActionButton floatingActionButton;

    public static ArrayList<Quote> person = new ArrayList<Quote>();
    public static ArrayList<Quote> favorite = new ArrayList<Quote>();
    public static ArrayList<Quote> quoteArrayList = new ArrayList<Quote>();



    Button showButton2;
    Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity_main);

        Intent intent = new Intent(G.context, Task.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(G.context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,2018);
        calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH,18);
        calendar.set(Calendar.HOUR,10);
        calendar.set(Calendar.MINUTE,8);
        calendar.set(Calendar.SECOND,0);



       // G.alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        G.alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, pendingIntent);




        context = getApplicationContext();
        setTabLayout();


        drawerLayout = findViewById(R.id.navigation_drawer);
        navigationView = findViewById(R.id.navigation_view);
        floatingActionButton = findViewById(R.id.floating);
        showButton2 = findViewById(R.id.showButton2);
        showButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                Intent intent1 = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://cafebazaar.ir/search/?q=%D8%AB%D8%B1%D9%88%D8%AA+%D8%A8%DB%8C+%D8%A7%D9%86%D8%AA%D9%87%D8%A7+%D8%A8%D8%A7+%DA%A9%D8%B3%D8%A8+%D8%AF%D8%B1%D8%A2%D9%85%D8%AF+24%D8%B3%D8%A7%D8%B9%D8%AA%D9%87&l=fa"));


                PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                PendingIntent pi1 = PendingIntent.getActivity(MainActivity.this, 0, intent1, 0);


                Resources r = getResources();
                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setTicker("نوتیف")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("لورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از صنعت چاپ و با استفاده از طراحان گرافیک است. چاپگرها و متون بلکه روزنامه و مجله در ستون و سطرآنچنان که لازم است و برای شرایط فعلی تکنولوژی مورد نیاز و کاربردهای متنوع با هدف بهبود ابزارهای کاربردی می باشد. "))
                        .setContentTitle("title")
                        .setSmallIcon(R.drawable.heart)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.email))
                        .setContentIntent(pi)
                        .setAutoCancel(true)
                        .setSound(path)
                        .setColor(getResources().getColor(R.color.colorAccent))
                        // .setNumber(22)
                        .addAction(R.drawable.heart_outline, "GO to site", pi1)
                        .addAction(R.drawable.email, "go to activity", pi)
                        .build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, notification);

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(floatingActionButton, " اسنک بار", Snackbar.LENGTH_LONG).show();
            }
        });
        menuToolbar = findViewById(R.id.menu_toolbar);
        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });


        try {
            desPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/book-database/";
            Log.i("LOG", "destinationPath :" + desPath);
            File file = new File(desPath);

            if (!file.exists()) {
                file.mkdirs();
                file.createNewFile();
                copyDB(getBaseContext().getAssets().open("dbs_b_n.sqlite"), new FileOutputStream(desPath + "/dbs_b_n.sqlite"));

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

        selectPerson();
        selectFavorite();
        //readQuoteFromDatabase();
        listQuoteAndSendNotif();


    }


    private void selectPerson() {
        database = SQLiteDatabase.openOrCreateDatabase(desPath + "/dbs_b_n.sqlite", null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_b_n WHERE sobject = 'person'", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String more = cursor.getString(cursor.getColumnIndex("more"));
            String imageAddress = cursor.getString(cursor.getColumnIndex("img_adrress"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));

            Quote struct = new Quote(name, content, more, imageAddress, id);
            struct.setName(name);
            struct.setContent(content);
            struct.setMore(more);
            struct.setImg_adrress(imageAddress);
            struct.setId(id);
            person.add(struct);

            // Log.i("LOG","person: " + person);
        }

    }

    private void selectFavorite() {
        database = SQLiteDatabase.openOrCreateDatabase(desPath + "/dbs_b_n.sqlite", null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_b_n WHERE fav = 1", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String more = cursor.getString(cursor.getColumnIndex("more"));
            String imageAddress = cursor.getString(cursor.getColumnIndex("img_adrress"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));

            Quote struct = new Quote(name, content, more, imageAddress, id);
            struct.setName(name);
            struct.setContent(content);
            struct.setMore(more);
            struct.setImg_adrress(imageAddress);
            struct.setId(id);
            favorite.add(struct);


        }

    }


    private void setTabLayout() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new AdapterFragment(getSupportFragmentManager()));
        TabLayout tabStrip = findViewById(R.id.tabLayout);
        //تب لیوت و ویو پیجر رو به هم متصل کردیم
        tabStrip.setupWithViewPager(viewPager);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (!favorite.isEmpty()) {
            favorite.clear();
            selectFavorite();
        } else if (!person.isEmpty()) {
            person.clear();
            selectPerson();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();

        }


    }

    private void copyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        inputStream.close();
        outputStream.close();

    }

    private void setNotification(Quote quote) {
        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
        Intent intent1 = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://cafebazaar.ir/search/?q=%D8%AB%D8%B1%D9%88%D8%AA+%D8%A8%DB%8C+%D8%A7%D9%86%D8%AA%D9%87%D8%A7+%D8%A8%D8%A7+%DA%A9%D8%B3%D8%A8+%D8%AF%D8%B1%D8%A2%D9%85%D8%AF+24%D8%B3%D8%A7%D8%B9%D8%AA%D9%87&l=fa"));

        PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        PendingIntent pi1 = PendingIntent.getActivity(MainActivity.this, 0, intent1, 0);

        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(MainActivity.this)
                .setTicker("نوتیف")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(quote.getMore()))
                .setContentTitle(quote.getName())
                .setSmallIcon(R.drawable.heart)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.email))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setSound(path)
                .setColor(getResources().getColor(R.color.colorAccent))
                .addAction(R.drawable.heart_outline, "GO to site", pi1)
                .addAction(R.drawable.email, "go to activity", pi)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }


    public void readQuoteFromDatabase() {
        database = SQLiteDatabase.openOrCreateDatabase(desPath + "/dbs_b_n.sqlite", null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_b_n ", null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String more = cursor.getString(cursor.getColumnIndex("more"));
            String imageAddress = cursor.getString(cursor.getColumnIndex("img_adrress"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));

            Quote struct = new Quote(name, content, more, imageAddress, id);
            struct.setName(name);
            struct.setContent(content);
            struct.setMore(more);
            struct.setImg_adrress(imageAddress);
            struct.setId(id);
            quoteArrayList.add(struct);
        }
        // Toast.makeText(context, "" + quoteArrayList.size(), Toast.LENGTH_SHORT).show();


    }

    public void listQuoteAndSendNotif() {
        readQuoteFromDatabase();
        int quoteSize = quoteArrayList.size();


        Random rand = new Random();
        int n = rand.nextInt(quoteSize);
        quoteArrayList.get(n);


        Toast.makeText(context, "size is :" + quoteArrayList.size(), Toast.LENGTH_SHORT).show();


        Toast.makeText(context, "" + n, Toast.LENGTH_SHORT).show();


         setNotification( quoteArrayList.get(n));


    }
}
