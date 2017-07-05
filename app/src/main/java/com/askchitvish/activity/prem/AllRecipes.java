package com.askchitvish.activity.prem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AllRecipes extends AppCompatActivity {

    ListView subcat_list;
ProgressBar progressBar;
    TextView gourl;
ImageView noim;
    EditText searchme;
    ImageButton clickser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categ);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subcat_list = (ListView) findViewById(R.id.sub_cat);
        progressBar =(ProgressBar) findViewById(R.id.progressBar2);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShaPreferences", MODE_PRIVATE);
        final String uid=pref.getString("uid", null);
        gourl=(TextView) findViewById(R.id.anc);
        Intent intent = getIntent();
        String getmeall = intent.getStringExtra("getme");
        noim=(ImageView) findViewById(R.id.noima);
         String url = "http://gettalentsapp.com/askchitvish/androadmin/mainsercfi.php?uid="+uid+"&pompom="+getmeall;
        new JSONTask().execute(url);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#971627"));
        }
        searchme=(EditText) findViewById(R.id.searchedit);
        clickser=(ImageButton) findViewById(R.id.imabutton);
        clickser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query=searchme.getText().toString();
                String url2 = "http://gettalentsapp.com/vignesh2514/askchitvish/androadmin/mainsercfi.php?uid="+uid+"&pompom="+query;
                new JSONTask().execute(url2);
                searchme.setText("");
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, List<Subcategory>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         progressBar.setVisibility(View.VISIBLE);
            subcat_list.setVisibility(View.INVISIBLE);
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
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<Subcategory> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                    for (int i = 0; i < parentArray.length(); i++) {
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
                if (connection != null) {

                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(final List<Subcategory> movieModelList) {
            super.onPostExecute(movieModelList);
            progressBar.setVisibility(View.INVISIBLE);
            subcat_list.setVisibility(View.VISIBLE);
            noim.setVisibility(View.INVISIBLE);
            if (movieModelList.size()>0) {
                MovieA adapter = new MovieA(AllRecipes.this, R.layout.list_cubcate, movieModelList);
                subcat_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                subcat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Subcategory subcategory = movieModelList.get(position);
                        Intent intent = new Intent(AllRecipes.this, DetailsActivity.class);
                        intent.putExtra("subcatdetails", new Gson().toJson(subcategory));
                        startActivity(intent);
                    }
                });
            }
            else
            {
                subcat_list.setVisibility(View.INVISIBLE);
                noim.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"No results found!",Toast.LENGTH_SHORT).show();

            }
        }

    }


}