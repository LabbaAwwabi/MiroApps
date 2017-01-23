package com.tekkom.rpl.miro.listMikrolet.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by awwabi on 28/12/16.
 */

public class Terminal {
    private int id;
    private String nama;
    private String alamat;
    private LatLng lokasi;

    public Terminal() {
    }

    public Terminal(int id, String nama, String alamat, LatLng lokasi) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.lokasi = lokasi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public LatLng getLokasi() {
        return lokasi;
    }

    public void setLokasi(LatLng lokasi) {
        this.lokasi = lokasi;
    }
}
