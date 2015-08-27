package com.blooddonor;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by sebi on 8/4/15.
 */
@ParseClassName("Donor")
public class BloodDonor extends ParseObject{
    public String getName() {
        return getString("Name");
    }

    public void setName(String name) {
        put("Name", name);
    }

    public void setUserName(String name) {

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
       // ParseACL.setDefaultACL(defaultACL, true);
        put("username", name);
        put("ACL", defaultACL);
    }
    public String getUserName() {
        return getString("username");
    }

    public String getCity() {
        return getString("City");
    }

    public void setCity(String name) {
        put("City", name);
    }


    public void setRH(String name) {
        put("RH", name);
    }

    public String getRH() {
        return getString("RH");
    }


    public void setBloodGroup(String name) {
        put("BloodGroup", name);
    }

    public String getBloodGroup() {
        return getString("BloodGroup");
    }

    public String getValidity() {
        return getString("Validity");
    }

    public void setLocation(ParseGeoPoint location) {
        put("Location", location);
    }
}
