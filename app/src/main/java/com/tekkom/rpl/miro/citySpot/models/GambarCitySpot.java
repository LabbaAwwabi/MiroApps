package com.tekkom.rpl.miro.citySpot.models;

import java.io.Serializable;

/**
 * Created by awwabi on 02/01/17.
 */

public class GambarCitySpot implements Serializable{
    private String urlGambar;
    private String keterangan;

    public GambarCitySpot() {
    }

    public GambarCitySpot(String urlGambar, String keterangan) {
        this.urlGambar = urlGambar;
        this.keterangan = keterangan;
    }

    public String getUrlGambar() {
        return urlGambar;
    }

    public void setUrlGambar(String urlGambar) {
        this.urlGambar = urlGambar;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
