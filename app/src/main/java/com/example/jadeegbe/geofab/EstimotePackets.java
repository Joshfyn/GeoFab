package com.example.jadeegbe.geofab;

public class EstimotePackets {

    private int _id;
    private String _timestamp;
    private String estimoteIdentifier;
    private String _estimoteRSSI;
    private String _NearIdentifierAccel;
    private String _xAcceleraTion;
    private String _yAcceleraTion;
    private String _zAcceleraTion;
    private String _xyzAcceleraTion;

    public EstimotePackets() {}

    public EstimotePackets(String _timestamp, String estimoteIdentifier, String _estimoteRSSI, String _NearIdentifierAccel, String _xAcceleraTion, String _yAcceleraTion, String _zAcceleraTion, String _xyzAcceleraTion) {
        this._timestamp = _timestamp;
        this.estimoteIdentifier = estimoteIdentifier;
        this._estimoteRSSI = _estimoteRSSI;
        this._NearIdentifierAccel = _NearIdentifierAccel;
        this._xAcceleraTion = _xAcceleraTion;
        this._yAcceleraTion = _yAcceleraTion;
        this._zAcceleraTion = _zAcceleraTion;
        this._xyzAcceleraTion = _xyzAcceleraTion;
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

    public String get_NearIdentifierAccel() {
        return _NearIdentifierAccel;
    }

    public void set_NearIdentifierAccel(String _NearIdentifierAccel) {
        this._NearIdentifierAccel = _NearIdentifierAccel;
    }

    public String get_xAcceleraTion() {
        return _xAcceleraTion;
    }

    public void set_xAcceleraTion(String _xAcceleraTion) {
        this._xAcceleraTion = _xAcceleraTion;
    }

    public String get_yAcceleraTion() {
        return _yAcceleraTion;
    }

    public void set_yAcceleraTion(String _yAcceleraTion) {
        this._yAcceleraTion = _yAcceleraTion;
    }

    public String get_zAcceleraTion() {
        return _zAcceleraTion;
    }

    public void set_zAcceleraTion(String _zAcceleraTion) {
        this._zAcceleraTion = _zAcceleraTion;
    }

    public String get_xyzAcceleraTion() {
        return _xyzAcceleraTion;
    }

    public void set_xyzAcceleraTion(String _xyzAcceleraTion) {
        this._xyzAcceleraTion = _xyzAcceleraTion;
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
