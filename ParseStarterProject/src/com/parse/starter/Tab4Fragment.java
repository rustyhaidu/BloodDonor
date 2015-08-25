package com.parse.starter;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by claudiu.haidu on 7/28/2015.
 */
public class Tab4Fragment extends Fragment implements View.OnClickListener{
    private static final String TAG= "RegisterDonor";


    /*logout = (Button) findViewById(R.id.logout);

		// Logout Button Click Listener
		logout.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// Logout current user
				ParseUser.logOut();
				finish();
				Intent backIntent = new Intent(Welcome.this,LoginSignupActivity.class );
				Welcome.this.startActivity(backIntent);
			}
		}); */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
       View view =  inflater.inflate(R.layout.tab4,container,false);
       Button saveButton = (Button) view.findViewById(R.id.saveButton);
       Button logout = (Button) view.findViewById(R.id.logOutButton);
        logout.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadData();
        //Toast.makeText(getActivity().getApplicationContext(),"OFFF",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    public void onSave() {

       /* BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setName(((EditText) getView().findViewById(R.id.name)).getText().toString());
        bloodDonor.setCity(((EditText) getView().findViewById(R.id.city)).getText().toString());
        bloodDonor.setBloodGroup(((EditText) getView().findViewById(R.id.blood_group)).getText().toString());
        bloodDonor.setRH(((EditText) getView().findViewById(R.id.blood_rh)).getText().toString());

        try {
            Geocoder geocoder = new Geocoder(getActivity());
            List<Address> address = geocoder.getFromLocationName(bloodDonor.getName(), 1);
            if (address != null) {
                bloodDonor.setLocation(new ParseGeoPoint(address.get(0).getLatitude(), address.get(0).getLongitude()));
            }
        }catch (Exception ex){
            Log.d(TAG, "Error getting lcoation", ex);
        }

        bloodDonor.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getActivity(), "Saved with success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Saved with error:" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }); */
        String currentUser = ParseUser.getCurrentUser().getUsername().toString();
        ParseQuery query = new ParseQuery("Donor");
        query.whereEqualTo("username", currentUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> donorList, ParseException e)
            {
                if (e == null)
                {
                    for (ParseObject nameObj : donorList)
                    {
                        nameObj.put("Name", ((EditText) getActivity().findViewById(R.id.name)).getText().toString());
                        nameObj.put("City", ((EditText) getActivity().findViewById(R.id.city)).getText().toString());
                        nameObj.put("BloodGroup", ((EditText) getActivity().findViewById(R.id.blood_group)).getText().toString());
                        nameObj.put("RH", ((EditText) getActivity().findViewById(R.id.blood_rh)).getText().toString());

                        nameObj.put("Type", ((Spinner) getActivity().findViewById(R.id.spType)).getSelectedItem().toString());

                        nameObj.put("Validity", ((EditText) getActivity().findViewById(R.id.etValidity)).getText().toString());
                        nameObj.saveInBackground();
                    }
                }
                else
                {
                    Log.d("Post retrieval", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveButton:
                onSave();
                Toast.makeText(getActivity(), "Pressed", Toast.LENGTH_LONG).show();
                break;
            case R.id.logOutButton:
                ParseUser.logOut();
                getActivity().finish();
                Intent backIntent = new Intent(getActivity(),LoginSignupActivity.class );
               getActivity().startActivity(backIntent);
                break;
        }
    }

    public void loadData(){

        ParseQuery query = new ParseQuery("Donor");
        String currentUser = ParseUser.getCurrentUser().getUsername().toString();

        Toast.makeText(getActivity().getApplicationContext(),currentUser,Toast.LENGTH_LONG).show();

        //query.whereExists("username");
        query.whereExists("Name");
        //query.whereExists("Surname");
        query.whereExists("City");
        query.whereExists("BloodGroup");
        query.whereExists("RH");
        query.whereEqualTo("username",currentUser);

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {


                if (e == null) {
                    Log.d("score", "Retrieved ");
                    Toast.makeText(getActivity().getApplicationContext(),"Found "+(int)list.size(),Toast.LENGTH_LONG).show();
                    for (int i = 0; i < list.size(); i++) {
                       // Toast.makeText(getActivity().getApplicationContext(),"Toast",Toast.LENGTH_LONG).show();

                        ParseObject p = list.get(i);

                        EditText eName = (EditText)getActivity().findViewById(R.id.name);
                        EditText eCity = (EditText)getActivity().findViewById(R.id.city);
                        EditText eBloodGroup = (EditText)getActivity().findViewById(R.id.blood_group);
                        EditText eRH = (EditText)getActivity().findViewById(R.id.blood_rh);
                       // EditText eType = (EditText)getActivity().findViewById(R.id.etType);
                        Spinner sType = (Spinner)getActivity().findViewById(R.id.spType);

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                R.array.dooner_type, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                        sType.setAdapter(adapter);

                        EditText eValidity = (EditText)getActivity().findViewById(R.id.etValidity);

                       eName.setText(p.getString("Name"));
                       eCity.setText(p.getString("City"));
                       eBloodGroup.setText(p.getString("BloodGroup"));
                       eRH.setText(p.getString("RH"));

                        for(int j= 0; j <sType.getAdapter().getCount(); j++) {
                            if (sType.getAdapter().getItem(j).toString().contains(p.getString("Type"))) {
                                sType.setSelection(j);
                            }
                        }
                       eValidity.setText(p.getString("Validity"));



                    }
                   // getCenters(Addresses);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
         });
    }


}