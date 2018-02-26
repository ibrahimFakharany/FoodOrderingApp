package exampls.com.foodorderingapp.Models;

import java.util.List;

/**
 * Created by 450 G1 on 20/02/2018.
 */

public class Rate {

    private FullRate fullrate;
    private List<Review> listOfReviews;
    public Rate(){

    }
    public Rate(FullRate fullrate, List<Review> listOfReviews) {
        this.fullrate = fullrate;
        this.listOfReviews = listOfReviews;
    }

    public FullRate getFullrate() {
        return fullrate;
    }

    public void setFullrate(FullRate fullrate) {
        this.fullrate = fullrate;
    }

    public List<Review> getListOfReviews() {
        return listOfReviews;
    }

    public void setListOfReviews(List<Review> listOfReviews) {
        this.listOfReviews = listOfReviews;
    }
}
