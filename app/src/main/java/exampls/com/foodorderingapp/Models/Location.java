package exampls.com.foodorderingapp.Models;

/**
 * Created by 450 G1 on 04/03/2018.
 */

public class Location {

    String lat;
    String lang;


    public Location() {
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Location(String lat, String lang) {
        this.lat = lat;
        this.lang = lang;
    }
}
