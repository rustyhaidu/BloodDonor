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
public class Tab3Fragment extends Fragment {
    private ListView requestsView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
       // return (LinearLayout) inflater.inflate(R.layout.tab3,container,false);
        View view = inflater.inflate(R.layout.tab3, container, false);
       // View view_header = inflater.inflate(R.layout.header_requests, container, false);
        //loadRequests();

        requestsView = (ListView) view.findViewById(R.id.requestsList);
       // requestsView.addHeaderView(view_header);
        return view;
    }
    public void onResume(){
        super.onResume();
        loadRequests();
    }

    private void loadRequests() {

        ParseQuery<BloodDonor> query = ParseQuery.getQuery("Donor");
       // query.whereExists("City");
       // query.whereExists("BloodGroup");
       // query.whereExists("RH");
       // query.whereExists("Validity");
       //  query.whereExists("Type");
        query.whereEqualTo("Type","requester");


        query.findInBackground(new FindCallback<BloodDonor>() {

            @Override
            public void done(List<BloodDonor> list, ParseException e) {
                if (e != null) {
                    Log.e("List", "Error retrieving the blood donors list.", e);
                } else {

                    BloodDonorAdapter adapter = new BloodDonorAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
                            list);
                    requestsView.setAdapter(adapter);
                    Log.e("List", "Success retrieving the blood requests list.", e);
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
                view = mInflater.inflate(R.layout.item_requests, parent, false);
                holder = new ViewHolder();
                holder.city = (TextView) view.findViewById(R.id.tvRequestCity);
                holder.bloodGroup = (TextView) view.findViewById(R.id.tvRequestBloodGroup);
                holder.bloodRH = (TextView)view.findViewById(R.id.tvRequestBloodRH);
                holder.Validity = (TextView)view.findViewById(R.id.tvRequestValidity);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            BloodDonor blooddonor = bloodDonors.get(position);
            holder.city.setText(blooddonor.getCity());
            holder.bloodGroup.setText(blooddonor.getBloodGroup());
            holder.bloodRH.setText(blooddonor.getRH());
            holder.Validity.setText(blooddonor.getValidity());

            return view;
        }

        private class ViewHolder {

            public TextView city, bloodGroup,bloodRH,Validity;
        }
    }
}
