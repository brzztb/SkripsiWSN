package com.example.aplikasiwsn.models;

public class Tanah {
    private String id_tanah;
    private String jenis_tanah;
    private String ph_tanah;
    private String suhu_tanah;
    private String kelembaban_tanah;
    private String suhu_udara;
    private String kelembaban_udara;
    private String kode_petak;
    private String waktu_sensing;

    public String getId_tanah() {
        return id_tanah;
    }

    public void setId_tanah(String id_tanah) {
        this.id_tanah = id_tanah;
    }

    public String getJenis_tanah() {
        return jenis_tanah;
    }

    public void setJenis_tanah(String jenis_tanah) {
        this.jenis_tanah = jenis_tanah;
    }

    public String getPh_tanah() {
        return ph_tanah;
    }

    public void setPh_tanah(String ph_tanah) {
        this.ph_tanah = ph_tanah;
    }

    public String getSuhu_tanah() {
        return suhu_tanah;
    }

    public void setSuhu_tanah(String suhu_tanah) {
        this.suhu_tanah = suhu_tanah;
    }

    public String getKelembaban_tanah() {
        return kelembaban_tanah;
    }

    public void setKelembaban_tanah(String kelembaban_tanah) {
        this.kelembaban_tanah = kelembaban_tanah;
    }

    public String getSuhu_udara() {
        return suhu_udara;
    }

    public void setSuhu_udara(String suhu_udara) {
        this.suhu_udara = suhu_udara;
    }

    public String getKelembaban_udara() {
        return kelembaban_udara;
    }

    public void setKelembaban_udara(String kelembaban_udara) {
        this.kelembaban_udara = kelembaban_udara;
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
}
