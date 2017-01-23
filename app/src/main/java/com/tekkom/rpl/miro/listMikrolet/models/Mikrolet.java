package com.tekkom.rpl.miro.listMikrolet.models;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by awwabi on 28/12/16.
 */

public class Mikrolet implements ClusterItem {
    private int id;
    private String plat_nomor;
    private TipeMikrolet tipe;
    private Supir supir;
    private LatLng lokasiSekarang;

    public Mikrolet() {
    }

    public Mikrolet(int id, String plat_nomor, @Nullable TipeMikrolet tipe, @Nullable Supir supir, LatLng lokasiSekarang) {
        this.id = id;
        this.plat_nomor = plat_nomor;
        this.tipe = tipe;
        this.supir = supir;
        this.lokasiSekarang = lokasiSekarang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlat_nomor() {
        return plat_nomor;
    }

    public void setPlat_nomor(String plat_nomor) {
        this.plat_nomor = plat_nomor;
    }

    public TipeMikrolet getTipe() {
        return tipe;
    }

    public void setTipe(TipeMikrolet tipe) {
        this.tipe = tipe;
    }

    public Supir getSupir() {
        return supir;
    }

    public void setSupir(Supir supir) {
        this.supir = supir;
    }

    public LatLng getLokasiSekarang() {
        return lokasiSekarang;
    }

    public void setLokasiSekarang(LatLng lokasiSekarang) {
        this.lokasiSekarang = lokasiSekarang;
    }

    @Override
    public String toString() {
        return "Mikrolet{" +
                "id=" + id +
                ", plat_nomor='" + plat_nomor + '\'' +
                ", tipe=" + tipe +
                ", supir=" + supir +
                ", lokasiSekarang=" + lokasiSekarang +
                '}';
    }

    @Override
    public LatLng getPosition() {
        return lokasiSekarang;
    }
}
