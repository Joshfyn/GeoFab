package com.example.jadeegbe.geofab;

public class EstimotePackets {

    private int _id;
    private String _timestamp;
    private String estimoteIdentifier;
    private String _estimoteRSSI;

    public EstimotePackets() {}

    public EstimotePackets(String _timestamp, String estimoteIdentifier, String _estimoteRSSI) {
        this._timestamp = _timestamp;
        this.estimoteIdentifier = estimoteIdentifier;
        this._estimoteRSSI = _estimoteRSSI;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_timestamp() {
        return _timestamp;
    }

    public void set_timestamp(String _timestamp) {
        this._timestamp = _timestamp;
    }

    public String getEstimoteIdentifier() {
        return estimoteIdentifier;
    }

    public void setEstimoteIdentifier(String estimoteIdentifier) {
        this.estimoteIdentifier = estimoteIdentifier;
    }

    public String get_estimoteRSSI() {
        return _estimoteRSSI;
    }

    public void set_estimoteRSSI(String _estimoteRSSI) {
        this._estimoteRSSI = _estimoteRSSI;
    }

    @Override
    public String toString() {
        StringBuilder b =new StringBuilder();
        b.append(" Id: ");
        b.append(get_id());
        b.append(" Identifier: ");
        b.append(getEstimoteIdentifier());
        b.append(" RSSI: ");
        b.append(get_estimoteRSSI());
        return b.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
