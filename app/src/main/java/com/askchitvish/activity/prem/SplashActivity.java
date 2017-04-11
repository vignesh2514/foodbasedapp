package com.askchitvish.activity.prem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.UUID;

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
   // private SQLiteHandler db;
Context context=this;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Realm myRealm = Realm.getInstance(context);
         SharedPreferences sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor=sharedPreferences.edit();
         boolean  firstTime=sharedPreferences.getBoolean("first", true);
         if(firstTime) {
             editor.putBoolean("first",false);
             String uid= UUID.randomUUID().toString();
             editor.putString("uid",uid);
              editor.apply();

             Intent intent = new Intent(SplashActivity.this, Splash2.class);
             startActivity(intent);
             finish();
         }else {
             new Handler().postDelayed(new Runnable(){
                 @Override
                 public void run() {
             Intent intent = new Intent(SplashActivity.this, Splash2.class);
             startActivity(intent);
             finish();
                 }
             }, SPLASH_DISPLAY_LENGTH);
         }
    }

}
