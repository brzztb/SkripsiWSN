package com.example.aplikasiwsn.models;

import java.util.ArrayList;

public class NodeSensorEtag {
    private ArrayList<NodeSensorStatus> nodes;
    private String etag;

    public ArrayList<NodeSensorStatus> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<NodeSensorStatus> nodes) {
        this.nodes = nodes;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
