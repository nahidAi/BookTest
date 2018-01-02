package com.test.booktestnotification.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.booktestnotification.Adapter.AdapterFragment;
import com.test.booktestnotification.R;

public class MainActivity extends AppCompatActivity {
    public static Context context;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuToolbar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity_main);




        drawerLayout = findViewById(R.id.navigation_drawer);
        navigationView = findViewById(R.id.navigation_view);
        menuToolbar = findViewById(R.id.menu_toolbar);
        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.setting){
                    Toast.makeText(MainActivity.this, " تنظیمات کلیک شد...!", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
        context = getApplicationContext();
        setTabLayout();


    }



    private void setTabLayout() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new AdapterFragment(getSupportFragmentManager()));
        TabLayout tabStrip = findViewById(R.id.tabLayout);
        //تب لیوت و ویو پیجر رو به هم متصل کردیم
        tabStrip.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)){
            drawerLayout.closeDrawer(Gravity.RIGHT);
        }else {
            super.onBackPressed();

        }

    }
}
