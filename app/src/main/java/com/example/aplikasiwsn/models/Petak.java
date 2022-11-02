package com.example.aplikasiwsn.models;

public class Petak {
    private String kode_petak;
    private String lintang;
    private String bujur;

    public Petak(String kode_petak, String lintang, String bujur) {
        this.kode_petak = kode_petak;
        this.lintang = lintang;
        this.bujur = bujur;
    }

    public String getKode_petak() {
        return kode_petak;
    }

    public void setKode_petak(String kode_petak) {
        this.kode_petak = kode_petak;
    }

    public String getLintang() {
        return lintang;
    }

    public void setLintang(String lintang) {
        this.lintang = lintang;
    }

    public String getBujur() {
        return bujur;
    }

    public void setBujur(String bujur) {
        this.bujur = bujur;
    }
}
