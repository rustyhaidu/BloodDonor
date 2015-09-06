package com.blooddonor;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.starter.R;

import java.util.List;

/**
 * Created by claudiu.haidu on 7/28/2015.
 */
public class Tab2Fragment extends Fragment  {
    private ListView donnersView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.tab2, container, false);
        loadOffers();

        donnersView = (ListView) view.findViewById(R.id.offersList);
        return view;
    }
    public void onResume(){
        super.onResume();
        loadOffers();
    }


       private void loadOffers() {

        ParseQuery<BloodDonor> query = ParseQuery.getQuery("Donor");
       // query.whereExists("City");
       // query.whereExists("BloodGroup");
       // query.whereExists("RH");
        query.whereEqualTo("Type","donner");
           //query.whereMatches("Type","donner");

        query.findInBackground(new FindCallback<BloodDonor>() {

            @Override
            public void done(List<BloodDonor> list, ParseException e) {
                if (e != null) {
                    Log.e("List", "Error retrieving the blood donors list.", e);
                } else {

                    BloodDonorAdapter adapter = new BloodDonorAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
                            list);
                    donnersView.setAdapter(adapter);
                    Log.e("List", "Success retrieving the blood donors list.", e);
                }
            }
        });
    }

    private static class BloodDonorAdapter extends ArrayAdapter<BloodDonor> {
        List<BloodDonor> bloodDonors;
        private LayoutInflater mInflater;

        @Override
        public int getCount() {
            return bloodDonors.size();
        }


        public BloodDonorAdapter(Context context, int resource,
                                  List<BloodDonor> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
            this.bloodDonors = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = mInflater.inflate(R.layout.item_offers, parent, false);
                holder = new ViewHolder();
                holder.city = (TextView) view.findViewById(R.id.tvOfferCity);
                holder.bloodGroup = (TextView) view.findViewById(R.id.tvOfferBloodGroup);
                 holder.bloodRH = (TextView)view.findViewById(R.id.tvOfferBloodRH);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            BloodDonor blooddonor = bloodDonors.get(position);
            holder.city.setText(blooddonor.getCity());
            holder.bloodGroup.setText(blooddonor.getBloodGroup());
            holder.bloodRH.setText(blooddonor.getRH());

            return view;
        }

        private class ViewHolder {

            public TextView city, bloodGroup,bloodRH;
        }
    }

}
