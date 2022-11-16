package com.example.aplikasiwsn.models;

public class SenseSuhuTanah {
    private String kode_petak;
    private String suhu_tanah;
    private String waktu_sensing;

    public SenseSuhuTanah(String kode_petak, String suhu_tanah, String waktu_sensing) {
        this.kode_petak = kode_petak;
        this.suhu_tanah = suhu_tanah;
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

    public void setSuhu_tanah(String suhu_tanah) {
        this.suhu_tanah = suhu_tanah;
    }

    public String getSuhu_Tanah() {
        return this.suhu_tanah;
    }
}
