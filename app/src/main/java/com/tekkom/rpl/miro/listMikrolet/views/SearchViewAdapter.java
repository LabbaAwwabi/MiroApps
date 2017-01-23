package com.tekkom.rpl.miro.listMikrolet.views;

import android.location.Address;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by raizal.pregnanta on 04/03/2016.
 */
public class SearchViewAdapter extends ArrayAdapter<String> {

    public ArrayList<Address> addresses;
    public MapsMicroletActivity activity;

    public SearchViewAdapter(MapsMicroletActivity context, int resource, ArrayList<Address> addresses) {
        super(context, resource);
        this.addresses = addresses;
        activity = context;
    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public String getItem(int position) {
        return getCaptionFromAddress(addresses.get(position));
    }

    public String getCaptionFromAddress(Address address) {

        String street = "";
        for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            street += address.getAddressLine(i);
            street += ", ";
        }
        String city = address.getLocality();
        String state = address.getAdminArea();
        String country = address.getCountryName();
//        String postalCode = address.getPostalCode();
//        String knownName = address.getFeatureName();

        String result = "";
        if (street.length()!=0)
            result += street;
        if (city != null)
            result += city + ", ";
        if (state != null)
            result += state + ", ";
        if (country != null)
            result += country;

        return result;
    }

}
