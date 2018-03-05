package exampls.com.foodorderingapp.Realm;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by 450 G1 on 04/03/2018.
 */

public class LocationTable extends RealmObject {

    String lat;
    String  lang;

    public LocationTable() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public LocationTable(String lat, String lang) {
        this.lat = lat;
        this.lang = lang;
    }

}
