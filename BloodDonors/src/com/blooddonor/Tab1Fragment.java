package com.blooddonor;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.starter.R;

import java.util.List;

/**
 * Created by claudiu.haidu on 7/28/2015.
 */
public class Tab1Fragment extends Fragment implements View.OnClickListener {
   // String AllAddresses;
    private ListView donnorCentersView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return (LinearLayout) inflater.inflate(R.layout.tab1,container,false);

        View view = inflater.inflate(R.layout.tab1, container, false);
        Button mapsButton = (Button) view.findViewById(R.id.mapButton);
        mapsButton.setOnClickListener(this);
        loadCenters();

        donnorCentersView = (ListView)view.findViewById(R.id.centersList);
        //lv.setAdapter(new ListviewContactAdapter(getActivity(), listContact));

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mapButton:
                final Intent intent;
                //context.getClass();
                intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);

                Toast.makeText(getActivity(), "Showing Map", Toast.LENGTH_LONG).show();
                break;
        }
    }

   /* public void getCenters(String Addresses) {
        AllAddresses = Addresses;
        TextView centers = (TextView) getActivity().findViewById(R.id.textview1);
        centers.setText(AllAddresses);
    } */

    private void loadCenters() {

        ParseQuery<BloodCenter> query = ParseQuery.getQuery("BloodCenter");
        query.whereExists("Address");
        query.whereExists("City");

        /*query.findInBackground(new FindCallback<ParseObject>() {
            String Addresses = "";
            //String Point="";
            ParseGeoPoint userLocation;
            @Override

            public void done(List<ParseObject> list, com.parse.ParseException e) {


                if (e == null) {
                    Log.d("score", "Retrieved ");

                    for (int i = 0; i < list.size(); i++) {
                        ParseObject p = list.get(i);

                        //userLocation = list.get(i).getParseGeoPoint("Location");

                       //double geo1Int =userLocation.getLatitude();
                        //double geo2Int = userLocation.getLongitude();

                        //Point =Point+ String.valueOf(geo1Int)+" "+String.valueOf(geo2Int)+";";
                        Addresses = Addresses + p.getString("Address")+";";

                    }
                    getCenters(Addresses);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
         }); */
        query.findInBackground(new FindCallback<BloodCenter>() {

            @Override
            public void done(List<BloodCenter> list, ParseException e) {
                if (e != null) {
                    Log.e("List", "Error retrieving the blood center list.", e);
                } else {

                    BloodCenterAdapter adapter = new BloodCenterAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
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
                view = mInflater.inflate(R.layout.item_center, parent, false);
                holder = new ViewHolder();
                holder.city = (TextView)view.findViewById(R.id.tvCity);
                holder.address = (TextView)view.findViewById(R.id.tvAddress);
               // holder.openingTime = (TextView)view.findViewById(R.id.center_opening_time);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder)view.getTag();
            }

            BloodCenter bloodCenter = bloodCenters.get(position);
            holder.city.setText(bloodCenter.getCity());
            holder.address.setText(bloodCenter.getAddress());
           // holder.openingTime.setText(bloodCenter.getOpenAt() + " - " + bloodCenter.getCloseAt());

            return view;
        }

        private class ViewHolder {

            public TextView city, address;
        }
    }

}
