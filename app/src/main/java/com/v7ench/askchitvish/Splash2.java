package com.v7ench.askchitvish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public class Splash2 extends AppCompatActivity {
    private  SQLiteHandler db;
    private final int SPLASH_DISPLAY_LENGTH = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        db = new SQLiteHandler(getApplicationContext());
        String uid= UUID.randomUUID().toString();
        db.addUser(uid);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash2.this,HomeScreen.class);
               startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
