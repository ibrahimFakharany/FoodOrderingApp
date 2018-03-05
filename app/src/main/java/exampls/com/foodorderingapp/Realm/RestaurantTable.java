package exampls.com.foodorderingapp.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 450 G1 on 01/03/2018.
 */

public class RestaurantTable extends RealmObject {
    @PrimaryKey
    private int resId;
    private String resName;
    private String imgPath;
    private RateTable rate;
    private MenuTable menu;
    private LocationTable location;
    int minOrder;
    int deliveryFee;

    public RestaurantTable(int resId, String resName, String imgPath, RateTable rate, MenuTable menu, int minOrder, int deliveryFee, LocationTable location) {
        this.resId = resId;
        this.resName = resName;
        this.imgPath = imgPath;
        this.rate = rate;
        this.menu = menu;
        this.minOrder = minOrder;
        this.deliveryFee = deliveryFee;
        this.location = location;
    }

    public LocationTable getLocation() {
        return location;
    }

    public void setLocation(LocationTable location) {
        this.location = location;
    }

    public int getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(int minOrder) {
        this.minOrder = minOrder;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public RestaurantTable() {
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public RateTable getRate() {
        return rate;
    }

    public void setRate(RateTable rate) {
        this.rate = rate;
    }

    public MenuTable getMenu() {
        return menu;
    }

    public void setMenu(MenuTable menu) {
        this.menu = menu;
    }


}
