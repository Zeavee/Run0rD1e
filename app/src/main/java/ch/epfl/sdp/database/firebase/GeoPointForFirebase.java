package ch.epfl.sdp.database.firebase;

public class GeoPointForFirebase {
    private double longitude;
    private double latitude;

    public GeoPointForFirebase(){}

    public GeoPointForFirebase(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
