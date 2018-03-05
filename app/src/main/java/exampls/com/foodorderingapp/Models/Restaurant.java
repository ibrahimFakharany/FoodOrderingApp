package exampls.com.foodorderingapp.Models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by 450 G1 on 20/02/2018.
 */
@IgnoreExtraProperties
public class Restaurant {
    private String resName;
    private String imgPath;
    private Rate rate;
    private Menu menu;
    private int minOrder;
    private int deliveryOrder;
    Location location;

    public Restaurant(String resName, String imgPath, Rate rate, Menu menu, int minOrder, int deliveryOrder, Location location) {
        this.resName = resName;
        this.imgPath = imgPath;
        this.rate = rate;
        this.menu = menu;
        this.minOrder = minOrder;
        this.deliveryOrder = deliveryOrder;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(int minOrder) {
        this.minOrder = minOrder;
    }

    public int getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(int deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public Restaurant() {
    }

    public String getName() {
        return resName;
    }

    public void setName(String resName) {
        this.resName = resName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
