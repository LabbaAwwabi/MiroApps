package com.tekkom.rpl.miro.track.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by awwabi on 18/01/17.
 */

public class Lokasi {
    private int id;
    private String alamat;
    private LatLng latLng;

    public Lokasi(int id, String alamat, LatLng latLng) {
        this.id = id;
        this.alamat = alamat;
        this.latLng = latLng;
    }

    public Lokasi() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
