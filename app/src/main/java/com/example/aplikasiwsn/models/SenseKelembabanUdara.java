package com.example.aplikasiwsn.models;

public class SenseKelembabanUdara {
    private String kode_petak;
    private String kelembaban_udara;
    private String waktu_sensing;

    public SenseKelembabanUdara(String kode_petak, String kelembaban_udara, String waktu_sensing) {
        this.kode_petak = kode_petak;
        this.kelembaban_udara = kelembaban_udara;
        this.waktu_sensing = waktu_sensing;
    }

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

    public String getKelembaban_udara() {
        return kelembaban_udara;
    }

    public void setKelembaban_udara(String kelembaban_udara) {
        this.kelembaban_udara = kelembaban_udara;
    }
}
