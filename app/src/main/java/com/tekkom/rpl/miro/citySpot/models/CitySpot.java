package com.tekkom.rpl.miro.citySpot.models;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

/**
 * Created by awwabi on 02/01/17.
 */

public class CitySpot implements Serializable {
    private int id;
    private String nama;
    private List<GambarCitySpot> gambar;
    private String deskripsi;
    private String alamat;
    private LatLng lokasi;

    public CitySpot() {
    }

    public CitySpot(int id, String nama, @Nullable List<GambarCitySpot> gambar, String deskripsi, String alamat, LatLng lokasi) {
        this.id = id;
        this.nama = nama;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
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

    public List<GambarCitySpot> getGambar() {
        return gambar;
    }

    public void setGambar(List<GambarCitySpot> gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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
