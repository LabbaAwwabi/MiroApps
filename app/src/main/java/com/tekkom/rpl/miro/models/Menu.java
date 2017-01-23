package com.tekkom.rpl.miro.models;

/**
 * Created by awwabi on 19/12/16.
 */

public class Menu {
    private String title;
    private String deskripsi;
    private int gambar;
    private int warna;

    public Menu(String title, String deskripsi, int gambar, int warna) {
        this.title = title;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.warna = warna;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public int getWarna() {
        return warna;
    }

    public void setWarna(int warna) {
        this.warna = warna;
    }
}
