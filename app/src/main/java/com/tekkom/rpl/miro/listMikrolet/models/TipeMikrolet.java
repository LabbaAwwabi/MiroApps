package com.tekkom.rpl.miro.listMikrolet.models;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by awwabi on 28/12/16.
 */

public class TipeMikrolet  implements Serializable {
    private int id;
    private String namaTipe;
    private String urlGambar;
    private List<Terminal> terminals;
    private double ongkosNaik;
    private int units;

    public TipeMikrolet() {
    }

    public TipeMikrolet(int id, String namaTipe, String urlGambar, @Nullable List<Terminal> terminals, double ongkosNaik, int units) {
        this.id = id;
        this.namaTipe = namaTipe;
        this.urlGambar = urlGambar;
        this.terminals = terminals;
        this.ongkosNaik = ongkosNaik;
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaTipe() {
        return namaTipe;
    }

    public void setNamaTipe(String namaTipe) {
        this.namaTipe = namaTipe;
    }

    public String getUrlGambar() {
        return urlGambar;
    }

    public void setUrlGambar(String urlGambar) {
        this.urlGambar = urlGambar;
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public void setTerminals(List<Terminal> terminals) {
        this.terminals = terminals;
    }

    public double getOngkosNaik() {
        return ongkosNaik;
    }

    public void setOngkosNaik(double ongkosNaik) {
        this.ongkosNaik = ongkosNaik;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
