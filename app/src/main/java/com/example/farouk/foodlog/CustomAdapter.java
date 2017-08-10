package com.example.farouk.foodlog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.farouk.foodlog.R;

import java.util.ArrayList;

/**
 * Created by Farouk on 12/10/2016.
 */

class CustomAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<CustomObject> objects;

        private class ViewHolder{
            TextView textView1;
            TextView textView2;
        }

    CustomAdapter(Context context, ArrayList<CustomObject> objects){
            inflater = LayoutInflater.from(context);
            this.objects = objects;
        }

        public int getCount(){
            return objects.size();
        }

        public CustomObject getItem(int position){
            return objects.get(position);
        }

        public long getItemId(int position){
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.itemlayout, null);
                holder.textView1 = (TextView) convertView.findViewById(R.id.food);
                holder.textView2 = (TextView) convertView.findViewById(R.id.calories);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView1.setText(objects.get(position).getFood());
            holder.textView2.setText(objects.get(position).getCals());
            return convertView;
        }
}

