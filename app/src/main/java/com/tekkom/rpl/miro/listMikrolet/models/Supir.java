package com.tekkom.rpl.miro.listMikrolet.models;

/**
 * Created by awwabi on 28/12/16.
 */

public class Supir {
    private int id;
    private String nama;
    private String alamat;

    public Supir() {
    }

    public Supir(int id, String nama, String alamat) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
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
}
