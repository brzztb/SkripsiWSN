package com.example.aplikasiwsn.models;

public class SenseSuhuUdara {
    private String kode_petak;
    private String suhu_udara;
    private String waktu_sensing;

    public SenseSuhuUdara(String kode_petak, String suhu_udara, String waktu_sensing) {
        this.kode_petak = kode_petak;
        this.suhu_udara = suhu_udara;
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

    public String getSuhu_udara() {
        return suhu_udara;
    }

    public void setSuhu_udara(String suhu_udara) {
        this.suhu_udara = suhu_udara;
    }
}
