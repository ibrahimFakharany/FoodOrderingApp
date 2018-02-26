package exampls.com.foodorderingapp.Models;

import java.util.List;

/**
 * Created by 450 G1 on 20/02/2018.
 */

public class Root {

    List<Restaurant> list;

    public Root() {
    }

    public Root(List<Restaurant> list) {
        this.list = list;
    }

    public List<Restaurant> getList() {
        return list;
    }

    public void setList(List<Restaurant> list) {
        this.list = list;
    }
}
