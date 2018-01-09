package com.test.booktestnotification.Activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.test.booktestnotification.Adapter.AdapterFragment;
import com.test.booktestnotification.R;
import com.test.booktestnotification.Structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static Context context;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuToolbar;
    SQLiteDatabase sqLiteDatabase;
    String desPath;
    FloatingActionButton floatingActionButton;

    public static ArrayList<Structure> person = new ArrayList<Structure>();
    public static ArrayList<Structure> favorite = new ArrayList<Structure>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity_main);

        context = getApplicationContext();
        setTabLayout();


        drawerLayout = findViewById(R.id.navigation_drawer);
        navigationView = findViewById(R.id.navigation_view);
        floatingActionButton = findViewById(R.id.floating);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(floatingActionButton," اسنک بار",Snackbar.LENGTH_LONG).show();
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


    }


    private void selectPerson() {
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(desPath + "/dbs_b_n.sqlite", null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_b_n WHERE sobject = 'person'", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String more = cursor.getString(cursor.getColumnIndex("more"));
            String imageAddress = cursor.getString(cursor.getColumnIndex("img_adrress"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));

            Structure struct = new Structure(name, content, more, imageAddress, id);
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
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(desPath + "/dbs_b_n.sqlite", null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_b_n WHERE fav = 1", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String more = cursor.getString(cursor.getColumnIndex("more"));
            String imageAddress = cursor.getString(cursor.getColumnIndex("img_adrress"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));

            Structure struct = new Structure(name, content, more, imageAddress, id);
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
        if (!favorite.isEmpty()){
            favorite.clear();
            selectFavorite();
        }else if (!person.isEmpty()){
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
}
