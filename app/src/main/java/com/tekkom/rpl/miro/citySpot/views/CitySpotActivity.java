package com.tekkom.rpl.miro.citySpot.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.citySpot.models.CitySpot;
import com.tekkom.rpl.miro.citySpot.models.GambarCitySpot;
import com.tekkom.rpl.miro.citySpot.presenters.CitySpotPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CitySpotActivity extends AppCompatActivity implements CitySpotAdapter.Listener, CitySpotPresenter.Listener {

    private String TAG = "MainActivity";
    private CitySpotPresenter presenter;

    private RecyclerView rvCitySpot;
    CitySpotAdapter adapter;

    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_spot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new CitySpotPresenter(this, this);

        rvCitySpot = (RecyclerView) findViewById(R.id.rvCitySpot);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        presenter.getCitySpotData();
    }

    @Override
    public void setRvData(ArrayList<CitySpot> citySpots) {
        adapter = new CitySpotAdapter(citySpots, this);
        rvCitySpot.setLayoutManager(new LinearLayoutManager(this));
        rvCitySpot.setItemAnimator(new DefaultItemAnimator());
        rvCitySpot.setAdapter(adapter);
    }

    @Override
    public void slideShow(ArrayList<GambarCitySpot> gambar) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("images", gambar);
        bundle.putInt("position", 0);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
        newFragment.setArguments(bundle);
        newFragment.show(ft, "slideshow");
    }

    @Override
    public void showMaps(CitySpot citySpot) {
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", citySpot.getLokasi().latitude);
        bundle.putDouble("lng", citySpot.getLokasi().longitude);
        bundle.putString("title", citySpot.getNama());

        Intent intent = new Intent(this, MapsCitySpotActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void showDesc(CitySpot citySpot) {
        showDeskripsiDialog(citySpot);
    }

    private void showDeskripsiDialog(CitySpot citySpot) {
        FragmentManager fm = getSupportFragmentManager();

        DeskripsiDialogFragment deskripsiDialogFragment = DeskripsiDialogFragment.newInstance(citySpot);
        deskripsiDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        deskripsiDialogFragment.show(fm, "fragment_detail_cityspot");

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
