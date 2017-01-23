package com.tekkom.rpl.miro.listMikrolet.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.listMikrolet.utils.Constant;

public class DetailMikroletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mikrolet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView gambar;
        TextView tipe, platNomor, supir, terminal, ongkos, lokasi;

        gambar = (ImageView) findViewById(R.id.gambar);
        tipe = (TextView) findViewById(R.id.tipe);
        platNomor = (TextView) findViewById(R.id.plat_nomor);
        supir = (TextView) findViewById(R.id.supir);
        terminal = (TextView) findViewById(R.id.terminal);
        ongkos = (TextView) findViewById(R.id.ongkos);
        lokasi = (TextView) findViewById(R.id.lokasi);

        Bundle bundle = getIntent().getExtras();

        Glide.with(this)
                .load(bundle.getString(Constant.URL_GAMBAR))
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(gambar);
        tipe.setText("Mikrolet " + bundle.getString(Constant.TIPE));
        platNomor.setText(bundle.getString(Constant.PLAT_NOMOR));
        supir.setText(bundle.getString(Constant.SUPIR));
        terminal.setText(bundle.getString(Constant.TERMINAL1) + " - " + bundle.getString(Constant.TERMINAL2));
        ongkos.setText("Rp " + bundle.getDouble(Constant.ONGKOS));
        lokasi.setText(bundle.getString(Constant.ALAMAT));
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
