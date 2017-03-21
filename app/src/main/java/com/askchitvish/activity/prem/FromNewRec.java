package com.askchitvish.activity.prem;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FromNewRec extends AppCompatActivity {

    TextView topic_na,shor,ingre,met;
    TextView tad,tvd,timad,tsidi,ttv;
    MediaPlayer mediaplayer;
    ImageView adi,vdi,favori,iim;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    ImageButton ddns;
    //private SQLiteHandler db;
    TextView tagline,serving;
    String URL="http://gettalentsapp.com/askchitvish/androadmin/sub_catog_details.php";
    TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle=(TextView) toolbar.findViewById(R.id.toolbar_title);
        topic_na=(TextView) findViewById(R.id.sivna);
//        shor=(TextView) findViewById(R.id.sh);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShaPreferences", MODE_PRIVATE);
        final String uid=pref.getString("uid", null);
        mediaplayer = new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        ingre=(TextView) findViewById(R.id.ingretext);
        ttv=(TextView) findViewById(R.id.ttvb);
        serving =(TextView) findViewById(R.id.servings);
        tagline=(TextView) findViewById(R.id.tag_line);
        met=(TextView) findViewById(R.id.methditext);
        adi=(ImageView) findViewById(R.id.audi);
        vdi=(ImageView) findViewById(R.id.vide);
        //  maima=(ImageView) findViewById(R.id.imav);
        iim=(ImageView) findViewById(R.id.lik);
        favori=(ImageView) findViewById(R.id.imagee);
        tad=(TextView) findViewById(R.id.taudi);
        tvd=(TextView) findViewById(R.id.tvidi);
        tsidi=(TextView) findViewById(R.id.sidi);

        timad=(TextView) findViewById(R.id.timai);
        ddns=(ImageButton) findViewById(R.id.ddn);
        String strObj = getIntent().getStringExtra("subcatdetails");


        try {

            JSONObject myJsonObj = new JSONObject(strObj);
            String sid = myJsonObj.getString("id");
            String topic_name = myJsonObj.getString("topic_name");
            String short_desc = myJsonObj.getString("short_desc");
            String ingerdi=myJsonObj.getString("ingredients1");
            String methi=myJsonObj.getString("method1");
            String adio=myJsonObj.getString("audio");
            String vide=myJsonObj.getString("video");
            String imag=myJsonObj.getString("images");
            String favonn=myJsonObj.getString("favon");
            String order_no=myJsonObj.getString("order_no");
            getall(sid,uid);
            addtextinview(sid,topic_name,short_desc,ingerdi,methi,adio,vide,imag,favonn,order_no);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        String favon=ttv.getText().toString();
        mTitle.setText(topic_na.getText().toString());
        setTitle("");
        String acheck =tad.getText().toString();
        if (acheck.isEmpty())
        {
            adi.setVisibility(View.GONE);
        }
        String ach =tvd.getText().toString();
        if (ach.isEmpty())
        {
            vdi.setVisibility(View.GONE);

        }
        vdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ach =tvd.getText().toString();

                Intent intent = new Intent(FromNewRec.this, YouTubActivity.class);
                intent.putExtra("ccpl", ach);
                startActivity(intent);

            }
        });
        ddns.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(FromNewRec.this, Newrecipe.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        final boolean[] showingF = {true};

        if (favon.equals("Y"))
        {
            favori.setImageResource(R.drawable.like);
            showingF[0]=false;
        }
        else
        {
            favori.setImageResource(R.drawable.unlike);
            showingF[0]=true;
        }
        favori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sidin=tsidi.getText().toString();


                if(showingF[0] == true)
                {
                    favinsert(uid,sidin);
                    favori.setImageResource(R.drawable.like);
                    showingF[0] = false;

                }
                else{
                    favdel(uid,sidin);
                    showingF[0] = true;
                    favori.setImageResource(R.drawable.unlike);

                }


            }
        });
        iim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aacg=tsidi.getText().toString();
                Intent intent=new Intent(FromNewRec.this,Imageload.class);
                intent.putExtra("ima",aacg);
                intent.putExtra("topi",topic_na.getText().toString());
                startActivity(intent);
                // zoomImageFromThumb(iim, aacg);

            }
        });



        final boolean[] showingFirst = {true};
        adi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String acheck =tad.getText().toString();
                if(showingFirst[0] == true)
                {
                    mediaplayer.start();
                    adi.setImageResource(R.drawable.stop);
                    showingFirst[0] = false;
                    try {
                        mediaplayer.setDataSource(acheck);
                        mediaplayer.prepare();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mediaplayer.start();
                }
                else{
                    showingFirst[0] = true;
                    adi.setImageResource(R.drawable.play);
                    mediaplayer.stop();

                }

            }
        });

        String sidin=tsidi.getText().toString();
        recme(uid,sidin);
        newmew(uid,sidin);
    }

    public void getall(final String sid, final String uid)
    {
        StringRequest stringRequest =new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject user = jObj.getJSONObject("result");
                    String favonn=user.getString("favon");
                    cmbl(favonn);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id",sid);
                params.put("uid",uid);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void cmbl(String favonn)
    {
        ttv.setText(favonn);
    }

    public void addtextinview(String sid,String topic_name, String short_desc, String ingerdi, String methi,String adio,String vide, String imag,String favonn,String order_no)
    {
        topic_na.setText(topic_name);
        tagline.setText(short_desc);
        tsidi.setText(sid);
        ingerdi=ingerdi.replace("&amp;","&");
        ingre.setText(ingerdi);
        ttv.setText(favonn);
        methi=methi.replace("&amp;","&");
        if (methi.contains("\\."))
        {
            String[] parts = methi.split("\\.",2); // escape .
            String part1 = parts[0]+parts[1];
            String part2 = parts[2];
            met.setText(part1+".\n\n"+part2);
        }
        else {
            met.setText(methi);
        }
        tad.setText(adio);
        tvd.setText(vide);
        timad.setText(imag);
        serving.setText("Servings - "+order_no);
//    Picasso.with(this).load(imag).fit().error(R.drawable.load).fit().into(maima);

    }
    @Override
    public void onBackPressed() {
        mediaplayer.stop();
        Intent intent = new Intent(FromNewRec.this, Newrecipe.class);
        startActivity(intent);
        finish();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5&& keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void favinsert(final String uid, final String sidin)
    {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, "http://gettalentsapp.com/askchitvish/androadmin/favin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String, String>();
                params.put("uid",uid);
                params.put("sid",sidin);

                return params;
            }
        };AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void newmew(final String uid, final String sidin)
    {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, "http://gettalentsapp.com/askchitvish/androadmin/newwithme.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String, String>();
                params.put("uid",uid);
                params.put("sid",sidin);

                return params;
            }
        };AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void favdel(final String uid, final String sidin )
    {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, "http://gettalentsapp.com/askchitvish/androadmin/favdel.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("uid",uid);
                params.put("sid",sidin);

                return params;
            }
        };AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void recme(final String uid, final String sidin )
    {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, "http://gettalentsapp.com/askchitvish/androadmin/recme.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String, String>();
                params.put("uid",uid);
                params.put("sid",sidin);
                return params;
            }
        };AppController.getInstance().addToRequestQueue(stringRequest);
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

            ingre.setTextSize(TypedValue.COMPLEX_UNIT_PX,ingre.getTextSize()+2);
            met.setTextSize(TypedValue.COMPLEX_UNIT_PX,met.getTextSize()+2);
        }
        else if (id == R.id.decre)
        {
            ingre.setTextSize(TypedValue.COMPLEX_UNIT_PX,ingre.getTextSize()-2);
            met.setTextSize(TypedValue.COMPLEX_UNIT_PX,met.getTextSize()-2);
        }

        return true;
    }

}
