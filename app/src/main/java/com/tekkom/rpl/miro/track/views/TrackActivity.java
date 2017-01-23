package com.tekkom.rpl.miro.track.views;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.track.models.Lokasi;
import com.tekkom.rpl.miro.track.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrackActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener,
        SearchAdapter.Listener{

    private RecyclerView rvSearch;
    private SearchAdapter adapter;

    SearchView searchView;

    private List<Lokasi> lokasiList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvSearch = (RecyclerView) findViewById(R.id.rv_search);
        adapter = new SearchAdapter(this, this);

        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.setItemAnimator(new DefaultItemAnimator());
        rvSearch.setAdapter(adapter);

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        geocode(query);
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onClose() {
        searchView.clearFocus();
        return true;
    }

    @Override
    public void showTrack(Lokasi lokasi) {
        Intent intent = new Intent(getApplicationContext(), MapsTrackActivity.class);
        intent.putExtra(Constant.TITLE_DESTINASI, lokasi.getAlamat());
        intent.putExtra(Constant.LATITUDE, lokasi.getLatLng().latitude);
        intent.putExtra(Constant.LONGITUDE, lokasi.getLatLng().longitude);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void geocode(String name) {
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        adapter.clear();
        lokasiList.clear();
        try {
            List<Address> addresses = geoCoder.getFromLocationName(name, 10);
            Log.i("debug", "geocode address size :" + addresses.size());
            for (Address address : addresses ) {
                Lokasi lokasi = new Lokasi();
                lokasi.setAlamat(getCaptionFromAddress(address));
                lokasi.setLatLng(new LatLng(address.getLatitude(), address.getLongitude()));

                Log.i("debug", "geocode alamat lokasi : " + lokasi.getAlamat());

                lokasiList.add(lokasi);
            }
            adapter.setData((ArrayList<Lokasi>) lokasiList);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
