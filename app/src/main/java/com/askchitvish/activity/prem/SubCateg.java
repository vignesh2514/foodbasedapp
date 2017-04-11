package com.askchitvish.activity.prem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.gson.reflect.TypeToken;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

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
import java.util.Locale;

public class SubCateg extends AppCompatActivity {
  ListView  subcat_list;


    MaterialSearchView searchView;
    ProgressBar progressBar;
    TextView gourl,giac;
    EditText searchme;
    ImageButton clickser;
    Context context=this;
    ConnectionDetector c;
    MovieA adapter;
    ImageView noim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categ);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#971627"));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upIntent = NavUtils.getParentActivityIntent(SubCateg.this);
                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(SubCateg.this, upIntent);
            }
        });
        subcat_list=(ListView) findViewById(R.id.sub_cat);
gourl=(TextView) findViewById(R.id.anc);
        giac=(TextView)findViewById(R.id.getidandcheck);
        noim=(ImageView) findViewById(R.id.noima);
        progressBar =(ProgressBar) findViewById(R.id.progressBar2);
        Intent details=getIntent();
      final String cid=details.getStringExtra("id");
        giac.setText(cid);
        String catt_nam=details.getStringExtra("cat_name");
        setTitle(catt_nam);
        final String mmn=giac.getText().toString();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShaPreferences", MODE_PRIVATE);
        final String uid=pref.getString("uid", null);
       /* final String url="http://gettalentsapp.com/askchitvish/androadmin/sub_catog.php?catg_id="+cid+"&uid="+uid;
        new JSONTask().execute(url);*/

        searchme=(EditText) findViewById(R.id.searchedit);
        clickser=(ImageButton) findViewById(R.id.imabutton);


        c=new ConnectionDetector(SubCateg.this);
        if(c.isConnect()){
             String url="http://gettalentsapp.com/askchitvish/androadmin/sub_catog.php?catg_id="+cid+"&uid="+uid;
            new JSONTask().execute(url);
            clickser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String query=searchme.getText().toString();
                    if (query.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Please enter a keyword",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String url3 = "http://gettalentsapp.com/askchitvish/androadmin/pom_pom.php?catg_id=" + cid + "&uid=" + uid + "&pompom=" + query;
                        new JSONTask().execute(url3);

                    }
                }
            });
        }


        else {
             final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
             final Gson gson = new Gson();
            String json = sharedPrefs.getString(mmn, null);
            Type type = new TypeToken<List<Subcategory>>() {}.getType();
            final ArrayList<Subcategory> arrayList = gson.fromJson(json, type);

             adapter = new MovieA(SubCateg.this, R.layout.list_cubcate, arrayList);
            subcat_list.setAdapter(adapter);
            subcat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Subcategory subcategory = arrayList.get(position);
                    Intent intent = new Intent(SubCateg.this,DetailsActivity.class);
                    intent.putExtra("subcatdetails", new Gson().toJson(subcategory));
                    startActivity(intent);

                }
            });
            clickser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = searchme.getText().toString();
                    if (!text.isEmpty()) {
                        if (arrayList.size()>1) {
                            for (int k = 0; k < arrayList.size(); k++) {
                                for (Subcategory subcategory : arrayList) {
                                    String abc = subcategory.getTopic_name().toLowerCase();
                                    if (!abc.contains(text)) {
                                        arrayList.remove(subcategory);
                                        break;
                                    }
                                }
                            }
                            adapter = new MovieA(SubCateg.this, R.layout.list_cubcate, arrayList);
                            subcat_list.setAdapter(adapter);
                            subcat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Subcategory subcategory = arrayList.get(position);
                                    Intent intent = new Intent(SubCateg.this, DetailsActivity.class);
                                    intent.putExtra("subcatdetails", new Gson().toJson(subcategory));
                                    startActivity(intent);

                                }
                            });
                        }
                        else
                        {
                            subcat_list.setVisibility(View.INVISIBLE);
                            noim.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),"No results found",Toast.LENGTH_SHORT).show();

                        }
                    }

                    else
                    {
                        String json = sharedPrefs.getString(mmn, null);
                        Type type = new TypeToken<List<Subcategory>>() {}.getType();
                        final ArrayList<Subcategory> arrayList = gson.fromJson(json, type);
                        adapter = new MovieA(SubCateg.this, R.layout.list_cubcate, arrayList);
                        subcat_list.setAdapter(adapter);
                        subcat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Subcategory subcategory = arrayList.get(position);
                                Intent intent = new Intent(SubCateg.this,DetailsActivity.class);
                                intent.putExtra("subcatdetails", new Gson().toJson(subcategory));
                                startActivity(intent);

                            }
                        });
                    }
                }
            });
            searchme.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                    // TODO Auto-generated method

                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                    // TODO Auto-generated method stub
                    String text = searchme.getText().toString().toLowerCase(Locale.getDefault());
                    if (text.isEmpty()) {
                        String json = sharedPrefs.getString(mmn, null);
                        Type type = new TypeToken<List<Subcategory>>() {}.getType();
                        final ArrayList<Subcategory> arrayList = gson.fromJson(json, type);
                        adapter = new MovieA(SubCateg.this, R.layout.list_cubcate, arrayList);
                        subcat_list.setAdapter(adapter);
                        subcat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Subcategory subcategory = arrayList.get(position);
                                Intent intent = new Intent(SubCateg.this,DetailsActivity.class);
                                intent.putExtra("subcatdetails", new Gson().toJson(subcategory));
                                startActivity(intent);

                            }
                        });
                    }

                }
            });

        }

    }




    public class JSONTask extends AsyncTask<String,String, List<Subcategory> > {

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
            subcat_list.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            noim.setVisibility(View.INVISIBLE);
            if(movieModelList.size()>0){
                MovieA adapter = new MovieA(SubCateg.this, R.layout.list_cubcate, movieModelList);
                subcat_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                subcat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Subcategory subcategory = movieModelList.get(position);
                        Intent intent = new Intent(SubCateg.this, DetailsActivity.class);
                        intent.putExtra("subcatdetails", new Gson().toJson(subcategory));
                        startActivity(intent);
                    }
                });
            }
            else {
                subcat_list.setVisibility(View.INVISIBLE);
                noim.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"No results found!",Toast.LENGTH_SHORT).show();



                }


            }
        }


    }



