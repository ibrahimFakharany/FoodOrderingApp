package exampls.com.foodorderingapp.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by 450 G1 on 01/03/2018.
 */

public class RateTable extends RealmObject {
    private FullRateTable fullrate;
    private RealmList<ReviewTable> listOfReviews;

    public RateTable(FullRateTable fullrate, RealmList<ReviewTable> listOfReviews) {
        this.fullrate = fullrate;
        this.listOfReviews = listOfReviews;
    }

    public RateTable() {
    }

    public FullRateTable getFullrate() {
        return fullrate;
    }

    public void setFullrate(FullRateTable fullrate) {
        this.fullrate = fullrate;
    }

    public RealmList<ReviewTable> getListOfReviews() {
        return listOfReviews;
    }

    public void setListOfReviews(RealmList<ReviewTable> listOfReviews) {
        this.listOfReviews = listOfReviews;
    }



}
