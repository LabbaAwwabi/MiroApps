package com.tekkom.rpl.miro.listMikrolet.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.tekkom.rpl.miro.listMikrolet.models.Terminal;
import com.tekkom.rpl.miro.listMikrolet.models.TipeMikrolet;
import com.tekkom.rpl.miro.listMikrolet.presenters.ListMikroletPresenter;
import com.tekkom.rpl.miro.listMikrolet.utils.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListMikroletActivity extends AppCompatActivity implements MikroletAdapter.Listener, ListMikroletPresenter.Listener {

    private ListMikroletPresenter presenter;

    private RecyclerView rvMikrolet;
    private MikroletAdapter adapter;

    private ArrayList<TipeMikrolet> mikrolets;

    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mikrolet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvMikrolet = (RecyclerView) findViewById(R.id.rv_mikrolet);

        presenter = new ListMikroletPresenter(this, this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        mikrolets = new ArrayList<>();
        adapter = new MikroletAdapter(mikrolets, this);

        rvMikrolet.setLayoutManager(new LinearLayoutManager(this));
        rvMikrolet.setItemAnimator(new DefaultItemAnimator());
        rvMikrolet.setAdapter(adapter);

        presenter.loadData();
    }

    @Override
    public void addData(TipeMikrolet tipeMikrolet) {
        adapter.add(tipeMikrolet);
    }

    @Override
    public void showMaps(TipeMikrolet tipeMikrolet) {
        Intent intent = new Intent(this, MapsMicroletActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.ID, tipeMikrolet.getId());
        bundle.putString(Constant.TIPE, tipeMikrolet.getNamaTipe());
        bundle.putString(Constant.URL_GAMBAR, tipeMikrolet.getUrlGambar());
        bundle.putDouble(Constant.ONGKOS, tipeMikrolet.getOngkosNaik());
        bundle.putString(Constant.TERMINAL1, tipeMikrolet.getTerminals().get(0).getNama());
        bundle.putString(Constant.TERMINAL2, tipeMikrolet.getTerminals().get(1).getNama());
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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
