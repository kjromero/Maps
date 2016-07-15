package mapscom.kenny.maps;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Luisa on 15/07/2016.
 */
public class LatLong {

    private double lat ;
    private double lon ;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLong(){
    }

    public LatLong(double lat, double lon, String title){
        this.lat = lat;
        this.lon = lon;
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
