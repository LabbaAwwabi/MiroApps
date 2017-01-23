package com.tekkom.rpl.miro.listMikrolet.views;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.listMikrolet.models.Mikrolet;
import com.tekkom.rpl.miro.listMikrolet.models.TipeMikrolet;
import com.tekkom.rpl.miro.listMikrolet.presenters.MapsMikroletPresenter;
import com.tekkom.rpl.miro.listMikrolet.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MapsMicroletActivity extends AppCompatActivity
        implements
        OnMapReadyCallback, SearchView.OnQueryTextListener, SearchView.OnCloseListener,
        ClusterManager.OnClusterItemClickListener<Mikrolet>, ClusterManager.OnClusterClickListener<Mikrolet>, MapsMikroletPresenter.Listener,
        MikroletBottomsheetFragment.Listener {

    private GoogleMap mMap;
    ClusterManager<Mikrolet> mikroletClusterManager;

    private MapsMikroletPresenter presenter;

    SearchViewAdapter searchViewAdapter;
    //view
    SearchView mSearchView;
    SearchView.SearchAutoComplete searchAutoComplete;

    // Progress dialog
    private ProgressDialog pDialog;

    private BottomSheetLayout bottomsheet;
    private MikroletBottomsheetFragment mikroletBottomsheetFragment;
    private SupportMapFragment mapFragment;

    private BitmapDescriptor marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_microlet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new MapsMikroletPresenter(this, this);

        marker = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_mikrolet);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        bottomsheet = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mikroletBottomsheetFragment = new MikroletBottomsheetFragment();
        mikroletBottomsheetFragment.setListener(this);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mikroletClusterManager = new ClusterManager<Mikrolet>(this, mMap);
        mikroletClusterManager.setOnClusterItemClickListener(this);
        mikroletClusterManager.setOnClusterClickListener(this);

        mikroletClusterManager.setRenderer(new DefaultClusterRenderer<Mikrolet>(this, mMap, mikroletClusterManager) {
            @Override
            protected int getColor(int clusterSize) {
                return getResources().getColor(R.color.colorPrimary);
            }

            @Override
            protected void onBeforeClusterItemRendered(Mikrolet item, MarkerOptions markerOptions) {
                markerOptions.icon(marker);
                super.onBeforeClusterItemRendered(item, markerOptions);
            }
        });

        View myLocationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent())
                .findViewById(Integer.parseInt("2"));
        myLocationButton.performClick();

        //scatterSample();
        presenter.getMikrolets(getIntent().getExtras().getInt(Constant.ID), getIntent().getExtras().getString(Constant.URL_GAMBAR));

    }

    @Override
    public void setData(ArrayList<Mikrolet> mikroletArrayList) {
        mikroletClusterManager.addItems(mikroletArrayList);

        mMap.setOnCameraIdleListener(mikroletClusterManager);
        mMap.setOnMarkerClickListener(mikroletClusterManager);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.245697, 112.737797), 15));
        }
    }

    @Override
    public void seeDetail(Mikrolet mikrolet) {
        Intent intent = new Intent(this, DetailMikroletActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.ID, mikrolet.getId());
        bundle.putString(Constant.URL_GAMBAR, getIntent().getExtras().getString(Constant.URL_GAMBAR));
        bundle.putString(Constant.TIPE, mikrolet.getTipe().getNamaTipe());
        bundle.putString(Constant.PLAT_NOMOR, mikrolet.getPlat_nomor());
        bundle.putString(Constant.SUPIR, mikrolet.getSupir().getNama());
        bundle.putString(Constant.TERMINAL1, getIntent().getExtras().getString(Constant.TERMINAL1));
        bundle.putString(Constant.TERMINAL2, getIntent().getExtras().getString(Constant.TERMINAL2));
        bundle.putDouble(Constant.ONGKOS, getIntent().getExtras().getDouble(Constant.ONGKOS));
        bundle.putString(Constant.ALAMAT, getAddress(mikrolet.getLokasiSekarang()));
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchAutoComplete.setTextColor(Color.WHITE);

        searchViewAdapter = new SearchViewAdapter(this, R.layout.search_item_layout, new ArrayList<Address>());
        searchAutoComplete.setAdapter(searchViewAdapter);

        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchAutoComplete.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Address a = searchViewAdapter.addresses.get(position);
                        searchAutoComplete.setText("" + searchViewAdapter.getCaptionFromAddress(a));
                        LatLng pos = new LatLng(a.getLatitude(), a.getLongitude());
                        //mMap.addMarker(new MarkerOptions().position(pos).title(getCaptionFromAddress(a)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, a.getLocality() == null ? 8f : 14f));
                    }
                }
        );

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
        return true;
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        geocode(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onClusterClick(Cluster<Mikrolet> cluster) {
        List<Mikrolet> list = new ArrayList<Mikrolet>();
        list.addAll(cluster.getItems());
        mikroletBottomsheetFragment.setData(list);
        mikroletBottomsheetFragment.show(getSupportFragmentManager(),R.id.bottomsheet);
        bottomsheet.setPeekSheetTranslation(MikroletBottomsheetAdapter.ViewHolder.itemHeight);
        return false;
    }

    @Override
    public boolean onClusterItemClick(Mikrolet mikrolet) {
        List<Mikrolet> list = new ArrayList<Mikrolet>();
        list.add(mikrolet);
        mikroletBottomsheetFragment.setData(list);
        mikroletBottomsheetFragment.show(getSupportFragmentManager(),R.id.bottomsheet);
        bottomsheet.setPeekSheetTranslation(MikroletBottomsheetAdapter.ViewHolder.itemHeight);
        return false;
    }

    private String getAddress(LatLng latLng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
            return "Alamat tidak terdeteksi";
        }
        return  addresses.get(0).getAddressLine(0) + addresses.get(0).getLocality();
    }

    @Override
    public void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void scatterSample() {
        LatLng base = new LatLng(-7.326215, 112.685499);

        Random r = new Random();

        Bundle data = getIntent().getExtras();
        TipeMikrolet tipeMikrolet = new TipeMikrolet(data.getInt("id"), data.getString("tipe"), data.getString("urlGambar"), null, data.getDouble("ongkos"), 11);
        for(int i=0;i<15;i++){
            double r1 = (0.1) * r.nextDouble();
            double r2 = (0.1) * r.nextDouble();
            Mikrolet mikrolet = new Mikrolet(i+1, "ABCDE", tipeMikrolet, null, new LatLng(base.latitude+r1,base.longitude+r2));
            mikroletClusterManager.addItem(mikrolet);
        }
    }

    public void geocode(String name) {
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(name, 5);
            searchViewAdapter.addresses.clear();
            searchViewAdapter.addresses.addAll(addresses);

//            if (addresses.size() > 0) {
//                for (int i = 0; i < addresses.size(); i++)
//
//            }
            if (searchViewAdapter.addresses.size() > 0) {
                searchViewAdapter.notifyDataSetChanged();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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
    public void onBackPressed() {
        if(bottomsheet.getState() != BottomSheetLayout.State.HIDDEN) {
            bottomsheet.dismissSheet();
        } else {
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
