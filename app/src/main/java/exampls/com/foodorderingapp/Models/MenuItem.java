package exampls.com.foodorderingapp.Models;

import java.util.List;

/**
 * Created by 450 G1 on 20/02/2018.
 */

public class MenuItem {

    private  String name;
    private List<SubCategory> subCategories;

    public MenuItem(String name, List<SubCategory> subCategories) {
        this.name = name;
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
