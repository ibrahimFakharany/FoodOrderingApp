package exampls.com.foodorderingapp.Models;

/**
 * Created by 450 G1 on 20/02/2018.
 */

public class Review  {

    private String date;
    private String name;
    private int rate;
    private String review;

    public Review(String date, String name, int rate, String review) {
        this.date = date;
        this.name = name;
        this.rate = rate;
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
