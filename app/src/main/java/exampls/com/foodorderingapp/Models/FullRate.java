package exampls.com.foodorderingapp.Models;

/**
 * Created by 450 G1 on 20/02/2018.
 */

public class FullRate {

    private int numOfPeople;
    private float rate;

    public FullRate(int numOfPeople, float rate) {
        this.numOfPeople = numOfPeople;
        this.rate = rate;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
