package com.example.aplikasiwsn.models;

public class NodeSensorStatus {
    private String nama_node;
    private String status_node;
    private String status_sensing;

    public String getNama_node() {
        return nama_node;
    }

    public void setNama_node(String nama_node) {
        this.nama_node = nama_node;
    }

    public String getStatus_node() {
        return status_node;
    }

    public void setStatus_node(String status_node) {
        this.status_node = status_node;
    }

    public String getStatus_sensing() {
        return status_sensing;
    }

    public void setStatus_sensing(String status_sensing) {
        this.status_sensing = status_sensing;
    }
}
