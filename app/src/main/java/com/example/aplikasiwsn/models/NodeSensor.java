package com.example.aplikasiwsn.models;

public class NodeSensor {
    private Integer imageId;
    private String name;
    private String status;
    private String statusSensing;

    private String keasaman;
    private String kelembaban;
    private String suhuTanah;
    private String suhuUdara;
    private String waktu;


    public NodeSensor (Integer imageId, String name, String status, String statusSensing) {
        this.imageId = imageId;
        this.name = name;
        this.status = status;
        this.statusSensing = statusSensing;
    }

    public NodeSensor (Integer imageId, String name, String keasaman, String kelembaban, String suhuTanah, String suhuUdara, String waktu) {
        this.imageId = imageId;
        this.name = name;
        this.keasaman = keasaman;
        this.kelembaban = kelembaban;
        this.suhuTanah = suhuTanah;
        this.suhuUdara = suhuUdara;
        this.waktu = waktu;
    }

    public NodeSensor (String name, String keasaman, String kelembaban, String suhuTanah, String suhuUdara, String waktu) {
        this.name = name;
        this.keasaman = keasaman;
        this.kelembaban = kelembaban;
        this.suhuTanah = suhuTanah;
        this.suhuUdara = suhuUdara;
        this.waktu = waktu;
    }

    public Integer getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusSensing() {
        return statusSensing;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusSensing(String statusSensing) {
        this.statusSensing = statusSensing;
    }

    public String getKeasaman() {
        return keasaman;
    }

    public void setKeasaman(String keasaman) {
        this.keasaman = keasaman;
    }

    public String getKelembaban() {
        return kelembaban;
    }

    public void setKelembaban(String kelembaban) {
        this.kelembaban = kelembaban;
    }

    public String getSuhuTanah() {
        return suhuTanah;
    }

    public void setSuhuTanah(String suhuTanah) {
        this.suhuTanah = suhuTanah;
    }

    public String getSuhuUdara() {
        return suhuUdara;
    }

    public void setSuhuUdara(String suhuUdara) {
        this.suhuUdara = suhuUdara;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
