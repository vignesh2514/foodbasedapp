package com.askchitvish.activity.prem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vignesh2514 on 23/1/17.
 */


public class MovieA extends ArrayAdapter {

    private ArrayList<Subcategory> arraylist;
    private List<Subcategory> movieModelList;
    private int resource;
    private Context context;
    private LayoutInflater inflater;
    MovieA(Context context, int resource, List<Subcategory> objects) {
        super(context, resource, objects);
        movieModelList = objects;
        this.context =context;
        this.resource = resource;
        inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getViewTypeCount() {

        return 1;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder  ;
        if(convertView == null){
            convertView = inflater.inflate(resource,null);
            holder = new ViewHolder();
            holder.topic = (TextView)convertView.findViewById(R.id.topic_n);
            holder.short_dec=(TextView) convertView.findViewById(R.id.short_d);
            holder.naming=(TextView) convertView.findViewById(R.id.namei);
            holder.ima=(ImageView) convertView.findViewById(R.id.ohna);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Subcategory subcategory= movieModelList.get(position);
        holder.topic.setText(subcategory.getTopic_name());
        holder.short_dec.setText(subcategory.getShort_desc());
        holder.naming.setText(subcategory.getName());

        try {
            String aac=subcategory.getNewon();

            if (aac.equals("Y"))
            {
                holder.ima.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.ima.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;

    }

    private class ViewHolder{
        private ImageView ima;

        private TextView topic,short_dec,naming;


    }


}