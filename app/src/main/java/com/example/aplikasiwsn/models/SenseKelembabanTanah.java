package com.example.aplikasiwsn.models;

public class SenseKelembabanTanah {
    private String kode_petak;
    private String kelembaban_tanah;
    private String waktu_sensing;

    public String getKode_petak() {
        return kode_petak;
    }

    public void setKode_petak(String kode_petak) {
        this.kode_petak = kode_petak;
    }

    public String getWaktu_sensing() {
        return waktu_sensing;
    }

    public void setWaktu_sensing(String waktu_sensing) {
        this.waktu_sensing = waktu_sensing;
    }

    public String getKelembaban_tanah() {
        return kelembaban_tanah;
    }

    public void setKelembaban_tanah(String kelembaban_tanah) {
        this.kelembaban_tanah = kelembaban_tanah;
    }

}
