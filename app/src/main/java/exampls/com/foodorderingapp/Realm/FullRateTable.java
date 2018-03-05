package exampls.com.foodorderingapp.Realm;

import io.realm.RealmObject;

/**
 * Created by 450 G1 on 01/03/2018.
 */

public class FullRateTable extends RealmObject  {

    private int numOfPeople;
    private float rate;

    public FullRateTable(int numOfPeople, float rate) {
        this.numOfPeople = numOfPeople;
        this.rate = rate;
    }

    public FullRateTable() {
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }


}
