package com.example.cia.projectwisatasemarang.Json;

import android.widget.ImageView;

/**
 * Created by cia on 04/10/2017.
 */

public class Result {

    String gambar;
    String nama;
    String alamat;
    String detail;
    String event;
    double latitude;
    double longitude;

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Double getLatitude(){
        return  latitude;
    }

    public Double getLongitude(){
        return longitude;
    }

    public void setLatitude(double latitude){
        this.latitude = Double.valueOf(latitude);
    }

    public void setLongitude(double longitude){
        this.longitude = Double.valueOf(longitude);
    }
}
