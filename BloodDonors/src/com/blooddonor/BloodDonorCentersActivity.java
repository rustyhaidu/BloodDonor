package com.blooddonor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.starter.R;

import java.util.List;


public class BloodDonorCentersActivity extends Activity {

    private static final String TAG= "BloodDonnorCenter";

    private ListView donnorCentersView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donor_centers);
        donnorCentersView = (ListView)findViewById(R.id.centers_list);

        SearchView searchView = (SearchView)findViewById(R.id.city_filter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                loadCenters(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        loadCenters("");
    }

    private void loadCenters(String locationFilter) {

        ParseQuery<BloodCenter> query = ParseQuery.getQuery(BloodCenter.class);
        if (locationFilter != null && locationFilter.trim().length() > 0) {
            query.whereContains("Address", locationFilter);
        }

        query.findInBackground(new FindCallback<BloodCenter>() {

            @Override
            public void done(List<BloodCenter> list, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error retrieving the blood center list.", e);
                } else {

                    BloodCenterAdapter adapter = new BloodCenterAdapter(BloodDonorCentersActivity.this, android.R.layout.simple_list_item_1,
                            list);
                    donnorCentersView.setAdapter(adapter);
                }
            }
        });
    }

    private static class BloodCenterAdapter extends ArrayAdapter<BloodCenter> {
        List<BloodCenter> bloodCenters;
        private LayoutInflater mInflater;
        @Override
        public int getCount() {
            return bloodCenters.size();
        }


        public BloodCenterAdapter(Context context, int resource,
                                List<BloodCenter> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
            this.bloodCenters = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if(convertView == null) {
                view = mInflater.inflate(R.layout.center_list_item, parent, false);
                holder = new ViewHolder();
                holder.name = (TextView)view.findViewById(R.id.center_name);
                holder.address = (TextView)view.findViewById(R.id.center_address);
                holder.openingTime = (TextView)view.findViewById(R.id.center_opening_time);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder)view.getTag();
            }

            BloodCenter bloodCenter = bloodCenters.get(position);
            holder.name.setText(bloodCenter.getName());
            holder.address.setText(bloodCenter.getAddress());
            holder.openingTime.setText(bloodCenter.getOpenAt() + " - " + bloodCenter.getCloseAt());

            return view;
        }

        private class ViewHolder {

            public TextView name, address, openingTime;
        }
    }
}
