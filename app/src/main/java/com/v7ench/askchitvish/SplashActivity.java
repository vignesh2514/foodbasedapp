package com.v7ench.askchitvish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         SharedPreferences sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor=sharedPreferences.edit();
         boolean  firstTime=sharedPreferences.getBoolean("first", true);
         if(firstTime) {
             editor.putBoolean("first",false);
              editor.apply();
             Intent intent = new Intent(SplashActivity.this, Splash2.class);
             startActivity(intent);
             finish();
         }else {
             new Handler().postDelayed(new Runnable(){
                 @Override
                 public void run() {
             Intent intent = new Intent(SplashActivity.this, HomeScreen.class);
             startActivity(intent);
             finish();
                 }
             }, SPLASH_DISPLAY_LENGTH);
         }
    }

}
