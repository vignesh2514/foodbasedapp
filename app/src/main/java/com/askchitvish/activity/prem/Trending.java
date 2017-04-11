package com.askchitvish.activity.prem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Trending extends AppCompatActivity {
    private ProgressDialog dialog;
    ListView subcat_list;

    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upIntent = NavUtils.getParentActivityIntent(Trending.this);
                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(Trending.this, upIntent);
            }
        });
           subcat_list=(ListView) findViewById(R.id.sub_cat);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShaPreferences", MODE_PRIVATE);
        String uid=pref.getString("uid", null);

        String url="http://gettalentsapp.com/askchitvish/androadmin/aac.php?uid="+uid;
        new JSONTask().execute(url);

    }
    public class JSONTask extends AsyncTask<String,String, List<Subcategory> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }
        @Override
        protected List<Subcategory> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<Subcategory> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    if (finalObject.getString("active").contains("Y")) {
                        Subcategory subcategory = gson.fromJson(finalObject.toString(), Subcategory.class);
                        movieModelList.add(subcategory);

                    }

                }
                return movieModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;

        }

        @Override
        protected void onPostExecute(final List<Subcategory> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();
            if(movieModelList != null) {
                MovieA adapter = new MovieA(Trending.this, R.layout.list_cubcate, movieModelList);
                subcat_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                subcat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Subcategory subcategory = movieModelList.get(position);
                        Intent intent = new Intent(Trending.this, DetailsActivity.class);
                        intent.putExtra("subcatdetails", new Gson().toJson(subcategory));
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                final Gson gson = new Gson();
                String json = sharedPrefs.getString("trendinginto", null);
                Type type = new TypeToken<List<Subcategory>>() {}.getType();
                final ArrayList<Subcategory> arrayList = gson.fromJson(json, type);

                MovieA adapter = new MovieA(Trending.this, R.layout.list_cubcate, arrayList);
                subcat_list.setAdapter(adapter);
                subcat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Subcategory subcategory = arrayList.get(position);
                        Intent intent = new Intent(Trending.this,DetailsActivity.class);
                        intent.putExtra("subcatdetails", new Gson().toJson(subcategory));
                        startActivity(intent);

                    }
                });
            }
        }

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


