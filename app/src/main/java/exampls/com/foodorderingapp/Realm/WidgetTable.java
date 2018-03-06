package exampls.com.foodorderingapp.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 450 G1 on 06/03/2018.
 */

public class WidgetTable extends RealmObject {
    @PrimaryKey
    int id;
    String name;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
