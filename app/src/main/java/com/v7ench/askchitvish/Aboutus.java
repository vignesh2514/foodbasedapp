package com.v7ench.askchitvish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Aboutus extends AppCompatActivity {
TextView tt1,tt2,tt3,tt4,tt5,tt6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tt1=(TextView) findViewById(R.id.textview1);
        tt2=(TextView) findViewById(R.id.textview2);
        tt3=(TextView) findViewById(R.id.textview3);
        tt4=(TextView) findViewById(R.id.textview4);
        tt5=(TextView) findViewById(R.id.textview5);
        tt6=(TextView) findViewById(R.id.textview6);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Aboutus.this,HomeScreen.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.incre) {

            tt1.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt1.getTextSize()+2);
            tt2.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt2.getTextSize()+2);
            tt3.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt3.getTextSize()+2);
            tt4.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt4.getTextSize()+2);
            tt5.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt5.getTextSize()+2);
            tt6.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt6.getTextSize()+2);

        }
        else if (id == R.id.decre)
        {

            tt1.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt1.getTextSize()-2);
            tt2.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt2.getTextSize()-2);
            tt3.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt3.getTextSize()-2);
            tt4.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt4.getTextSize()-2);
            tt5.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt5.getTextSize()-2);
            tt6.setTextSize(TypedValue.COMPLEX_UNIT_PX,tt6.getTextSize()-2);


        }

        return true;
    }
}
