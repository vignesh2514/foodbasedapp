package com.v7ench.askchitvish;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
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
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.Y;
import static com.v7ench.askchitvish.R.id.audi;

public class DetailsActivity extends AppCompatActivity {
TextView topic_na,shor,ingre,met;
    TextView tad,tvd,timad,tsidi,ttv;
    MediaPlayer mediaplayer;
    ImageView adi,vdi,favori,iim;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    ImageButton ddns;
    private SQLiteHandler db;
    String URL="http://gettalentsapp.com/vignesh2514/askchitvish/androadmin/sub_catog_details.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        topic_na=(TextView) findViewById(R.id.sivna);
//        shor=(TextView) findViewById(R.id.sh);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        final String uid = user.get("uid");
        mediaplayer = new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        ingre=(TextView) findViewById(R.id.ingretext);
ttv=(TextView) findViewById(R.id.ttvb);
        met=(TextView) findViewById(R.id.methditext);
adi=(ImageView) findViewById(audi);
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
            JSONObject myJsonObj = null;
            myJsonObj = new JSONObject(strObj);
            String sid = myJsonObj.getString("id");

            getall(sid,uid);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        setTitle(topic_na.getText().toString());
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

                    Intent intent = new Intent(DetailsActivity.this, YouTubActivity.class);
                    intent.putExtra("ccpl", ach);
                    startActivity(intent);

            }
        });
ddns.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        }
);
        final boolean[] showingF = {true};
        String favon=ttv.getText().toString();
        if (favon.equals("Y"))
        {
            favori.setImageResource(R.drawable.like);
            showingF[0]=false;
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
        String aacg=timad.getText().toString();
        Intent intent=new Intent(DetailsActivity.this,Imageload.class);
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
    }

    public void getall(final String sid, final String uid)
    {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject user = jObj.getJSONObject("result");
                    String topic_name = user.getString("topic_name");
                    String short_desc = user.getString("short_desc");
                    String favonn=user.getString("favon");
                    String ingerdi=user.getString("ingredients1");
                    String methi=user.getString("method1");
                    String adio=user.getString("audio");
                    String vide=user.getString("video");
                    String imag=user.getString("images");
                    addtextinview(sid,topic_name,short_desc,ingerdi,methi,adio,vide,imag,favonn);
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

public void addtextinview(String sid,String topic_name, String short_desc, String ingerdi, String methi,String adio,String vide, String imag,String favonn)
{
    topic_na.setText(topic_name);
    //shor.setText(short_desc);
tsidi.setText(sid);
    ingre.setText(ingerdi);
    String[] parts = methi.split("\\.",3); // escape .
    String part1 = parts[0]+parts[1];
    String part2 = parts[2];
         met.setText(part1+".\n\n"+part2);
    tad.setText(adio);
    tvd.setText(vide);
    timad.setText(imag);
    ttv.setText(favonn);
//    Picasso.with(this).load(imag).fit().error(R.drawable.load).fit().into(maima);

}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaplayer.stop();
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
    private void zoomImageFromThumb(final View thumbView, String imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
        Picasso.with(this).load(imageResId).fit().error(R.drawable.backnom).fit().into(expandedImageView);
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.aaaon).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(1f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(1f);
        expandedImageView.setPivotY(1f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
    public void favinsert(final String uid, final String sidin)
    {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, "http://gettalentsapp.com/vignesh2514/askchitvish/androadmin/favin.php", new Response.Listener<String>() {
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
        StringRequest stringRequest =new StringRequest(Request.Method.POST, "http://gettalentsapp.com/vignesh2514/askchitvish/androadmin/favdel.php", new Response.Listener<String>() {
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
        StringRequest stringRequest =new StringRequest(Request.Method.POST, "http://gettalentsapp.com/vignesh2514/askchitvish/androadmin/recme.php", new Response.Listener<String>() {
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
