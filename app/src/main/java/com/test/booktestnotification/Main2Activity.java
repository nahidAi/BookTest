package com.test.booktestnotification;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.booktestnotification.Activity.MainActivity;

public class Main2Activity extends AppCompatActivity {
    private int id;
    private String name;
    private String contrnt;
    private String more;
    private String img_adrress;

    private int layOutId;
    private String pageName;

    private TextView content;
    private TextView txtMore;
    private ImageView avatar;
    private ImageView imgShare;
    private ImageView imgCopy;
    private FloatingActionButton floatingActionButton;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String destPath;
    private SQLiteDatabase database;
    private String favoriteState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extrus = getIntent().getExtras();
        if (extrus != null) {
            layOutId = Integer.parseInt(extrus.getString("id"));
            pageName = extrus.getString("name");
            ////////////////////////////
            assert pageName != null;
            ////////////////////////////
            if (pageName.equals("BigPerson")) {
                id = MainActivity.person.get(layOutId).getId();
                name = MainActivity.person.get(layOutId).getName();
                contrnt = MainActivity.person.get(layOutId).getContent();
                more = MainActivity.person.get(layOutId).getMore();
                img_adrress = MainActivity.person.get(layOutId).getImg_adrress();
            } else if (pageName.equals("favorite")) {
                id = MainActivity.favorite.get(layOutId).getId();
                name = MainActivity.favorite.get(layOutId).getName();
                contrnt = MainActivity.favorite.get(layOutId).getContent();
                more = MainActivity.favorite.get(layOutId).getMore();
                img_adrress = MainActivity.favorite.get(layOutId).getImg_adrress();
            }
        }
        Log.i("LOG", "id : " + id);
        Log.i("LOG", "name: " + name);
        Log.i("LOG", "content: " + contrnt);

        content = findViewById(R.id.content_text);
        txtMore = findViewById(R.id.content_more_text2);
        avatar = findViewById(R.id.avatar);
        imgShare = findViewById(R.id.img_share_main2);
        imgCopy = findViewById(R.id.img_mail_main2);
        floatingActionButton = findViewById(R.id.favorite);
        collapsingToolbarLayout = findViewById(R.id.collAps);
        collapsingToolbarLayout.setTitle(name);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        content.setText(contrnt);
        txtMore.setText(more);
        int imageId = MainActivity.context.getResources().getIdentifier(img_adrress, "drawable", MainActivity.context.getPackageName());
        avatar.setImageResource(imageId);
        selectDB();
        if (selectFavoriteState()) {
            floatingActionButton.setImageResource(R.drawable.heart);

        } else {
            floatingActionButton.setImageResource(R.drawable.heart_outline);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectFavoriteState()) {
                    floatingActionButton.setImageResource(R.drawable.heart_outline);
                    updateUnFavorite();
                } else {
                    floatingActionButton.setImageResource(R.drawable.heart);
                    updateFavorite();

                }
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, contrnt);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, name);
                startActivity(Intent.createChooser(shareIntent, "اشتراک"));
            }
        });
        imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
                    final android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)Main2Activity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    final  android.content.ClipData clipData = android.content.ClipData.newPlainText(name,contrnt);
                    clipboardManager.setPrimaryClip(clipData);
                }else{
                    final android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)Main2Activity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setText(contrnt);


                }
                Snackbar.make(view,"متن کپی شد",Snackbar.LENGTH_LONG).show();
            }
        });


    }

    private void selectDB() {
        destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/book-database/";
        database = SQLiteDatabase.openOrCreateDatabase(destPath + "/dbs_b_n.sqlite", null);

    }

    private boolean selectFavoriteState() {
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_b_n WHERE id = " + id, null);
        while (cursor.moveToNext()) {
            favoriteState = cursor.getString(cursor.getColumnIndex("fav"));
        }
        if (favoriteState.equals("1")) {
            return true;
        } else {
            return false;

        }

    }

    private void updateFavorite() {
        database.execSQL("UPDATE tbl_b_n SET fav = 1 WHERE id = " + id);

    }

    private void updateUnFavorite() {
        database.execSQL("UPDATE tbl_b_n SET fav = 0 WHERE id = " + id);

    }
}
