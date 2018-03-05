package exampls.com.foodorderingapp.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by 450 G1 on 01/03/2018.
 */

public class MenuTable extends RealmObject {

    private RealmList<MenuItemTable> menuItems;


    public MenuTable(RealmList<MenuItemTable> menuItems) {
        this.menuItems = menuItems;
    }

    public MenuTable() {
    }

    public RealmList<MenuItemTable> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(RealmList<MenuItemTable> menuItems) {
        this.menuItems = menuItems;
    }


}
