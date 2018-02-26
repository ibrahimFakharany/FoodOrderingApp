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

    public Restaurant() {}

    public Restaurant(String name, String imgPath, Rate rate, Menu menu) {
        this.resName = name;
        this.imgPath = imgPath;
        this.rate = rate;
        this.menu = menu;
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
