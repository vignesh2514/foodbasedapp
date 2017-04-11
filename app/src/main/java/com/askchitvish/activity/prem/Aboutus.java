package com.askchitvish.activity.prem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class Aboutus extends AppCompatActivity {
TextView tt8;
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
                Intent upIntent = NavUtils.getParentActivityIntent(Aboutus.this);
                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(Aboutus.this, upIntent);
            }
        });
        tt8=(TextView) findViewById(R.id.bounceme);
        tt8.setClickable(true);
        tt8.setMovementMethod(LinkMovementMethod.getInstance());
        String content1="<p align=\" justify \"><br>Chitvish is now a household name across the globe. Her countless recipes have taken the culinary world by storm. Each and every mouth watering recipe of hers is tried meticulously by a lonely bride in Alaska or Australia living far away from her dear parents. When she is able to cook a dish just the way her mother did thanks to chitvish’s recipes, her joy knows no bounds and her homesickness vanishes instantly.</p>" +
                "<p align=\" justify \">Despite her strong South Indian roots, Chitvish’s recipes transcend national and international barriers.Be it the typically Tamilian akkaravadisal,the Gujarati dhokla, the Rajasthani Lapsi or the Mexican fajita, her avid followers just make a beeline to her recipes whenever they want to make it. ‘Ask Chitvish’ is the mantra chanted by thousands day in and day out!</p> <br>" +
                "<p>Welcome to the wonderland of Chitvish!</p><br><u><h1> Reviews</h1></u>";
String content2="<h4><a href='http://www.thehindu.com/features/metroplus/hits-likes-and-sambar/article6163393.ece'> The Hindu </a><br><br><a href='http://www.newindianexpress.com/cities/chennai/2014/oct/09/Akkaravadisal-for-the-South-Indian-Soul-669706.html'> New Indian Express </a> <br><br><a href='http://www.rediff.com/getahead/slide-show/slide-show-1-achievers-75-yr-old-chitra-viswanathan-has-a-cooking-app-to-her-name/20140728.htm'> Rediff </a><br></h4><br><u><h1> Testimonials</h1></u>" +
        "<br><u><h5>Shyamala Srivatsan </h5></u><p>Very useful and handy (on hand all the time) cook book from an experienced culinary expert. Chitra has explained everything neatly and clearly.</p><br><u><h5>Sundar Matpadi</h5></u><p>Wonderful collection of recipe, all personally made and verified by the author.</p><br><u><h5>Jayashree Arvind </h5></u><p>Best app and a must in every mobile. Lots of tips and detailed step by step receipe with photos. Since every thing has been tried, it comes out perfectly. Lots of effort has gone into this app.</p><br><h5><br><u><h5>Ayesha Fakhruddin</h5></u><p>Best companion in the kitchen.</p><br><h5>";

        tt8.setText(Html.fromHtml(content1+content2));
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

}
