package exampls.com.foodorderingapp.Models;

import java.util.List;

/**
 * Created by 450 G1 on 20/02/2018.
 */

public class Menu {

    private List<MenuItem> menuItems;

    public Menu(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
