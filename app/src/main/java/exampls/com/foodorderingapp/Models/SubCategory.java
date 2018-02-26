package exampls.com.foodorderingapp.Models;

/**
 * Created by 450 G1 on 20/02/2018.
 */

public class SubCategory {

    private String name;
    private String basicPrice;
    private String imgPath;
    private String description;


    public SubCategory(String name, String basicPrice, String imgPath, String description) {
        this.name = name;
        this.basicPrice = basicPrice;
        this.imgPath = imgPath;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(String basicPrice) {
        this.basicPrice = basicPrice;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
