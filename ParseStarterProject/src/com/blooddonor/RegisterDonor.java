/*package com.parse.starter;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.SaveCallback;

import java.util.List;


public class RegisterDonor extends Activity {
    private static final String TAG= "RegisterDonor";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_donor);
    }


    public void onSave(View view) {

        BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setName(((EditText) findViewById(R.id.name)).getText().toString());
        bloodDonor.setCity(((EditText) findViewById(R.id.city)).getText().toString());
        bloodDonor.setBloodGroup(((EditText) findViewById(R.id.blood_group)).getText().toString());
        bloodDonor.setRH(((EditText) findViewById(R.id.blood_rh)).getText().toString());

        try {
            Geocoder geocoder = new Geocoder(this);
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
                    Toast.makeText(RegisterDonor.this, "Saved with success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterDonor.this, "Saved with error:" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}*/
