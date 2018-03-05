package exampls.com.foodorderingapp.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by 450 G1 on 01/03/2018.
 */

public class MenuItemTable extends RealmObject  {
    private  String name;
    private RealmList<SubCategoryTable> subCategories;

    public MenuItemTable(String name, RealmList<SubCategoryTable> subCategories) {
        this.name = name;
        this.subCategories = subCategories;
    }

    public MenuItemTable() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<SubCategoryTable> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(RealmList<SubCategoryTable> subCategories) {
        this.subCategories = subCategories;
    }

}
