package exampls.com.foodorderingapp.Utils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exampls.com.foodorderingapp.Models.FullRate;
import exampls.com.foodorderingapp.Models.Location;
import exampls.com.foodorderingapp.Models.Menu;
import exampls.com.foodorderingapp.Models.MenuItem;
import exampls.com.foodorderingapp.Models.Rate;
import exampls.com.foodorderingapp.Models.Restaurant;
import exampls.com.foodorderingapp.Models.Review;
import exampls.com.foodorderingapp.Models.SubCategory;

/**
 * Created by 450 G1 on 23/02/2018.
 */

public class NetworkCalls {
    MyListener myListener;
    MyRestaurantListener myRestaurantListener;

    public void setMyRestaurantListener(MyRestaurantListener myRestaurantListener) {
        this.myRestaurantListener = myRestaurantListener;
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }
    public interface MyRestaurantListener{
        void onFinish(Restaurant restaurant);
    }
    public interface MyListener{
        void onFinish(DataSnapshot dataSnapshot);
    }
    String TAG = "networkcalls";
    public NetworkCalls(){}
    public void findRestaurantsImgAndName() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference allReviewRateRef = database.getReference();
        allReviewRateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               myListener.onFinish(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void findRestaurantsImgAndName(final int id) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference allReviewRateRef = database.getReference();
        allReviewRateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                final List<Restaurant> restaurants = new ArrayList<>();
                ArrayList<HashMap<String, HashMap<String, ArrayList<HashMap<String, String>>>>> allRestaurants = (ArrayList<HashMap</*rate*/String, /*allReviews*/ HashMap</*name, date... */String, /*values...*/ ArrayList<HashMap<String, String>>>>>) dataSnapshot.child("restaurants").getValue();
                Review reviewObj;
                ArrayList<HashMap<String, HashMap<String, HashMap<String, String>>>> fullRate = (ArrayList<HashMap<String, HashMap<String, HashMap<String, String>>>>) dataSnapshot.child("restaurants").getValue();
                ArrayList<HashMap<String, String>> restaurantsNameAndImg = (ArrayList<HashMap<String, String>>) dataSnapshot.child("restaurants").getValue();
                ArrayList<HashMap<String, ArrayList<HashMap<String, ArrayList<HashMap<String, String>>>>>> value = (ArrayList<HashMap<String, ArrayList<HashMap<String, ArrayList<HashMap<String, String>>>>>>) dataSnapshot.child("restaurants").getValue();
                ArrayList<HashMap<String, ArrayList<HashMap<String, String>>>> valueName = (ArrayList<HashMap<String, ArrayList<HashMap<String, String>>>>) dataSnapshot.child("restaurants").getValue();


                int i = 0;
                for (HashMap<String, HashMap<String, ArrayList<HashMap<String, String>>>> rate : allRestaurants) {

                    HashMap<String, ArrayList<HashMap<String, String>>> rootRate = rate.get("rate");
                    ArrayList<HashMap<String, String>> allReviews = rootRate.get("allreviews");
                    Rate rateObj = null;
                    // reviews
                    List<Review> listReviews= new ArrayList<>();
                    for (HashMap<String, String> review : allReviews) {

                        String date = review.get("date");
                        String name = review.get("name");
                        int rateUsr = Integer.parseInt(review.get("rate"));
                        String reviewUsr = review.get("review");
                        String log = "date :" + date + " ,name :" + name + " ,rate :" + rateUsr + " ,review :" + reviewUsr;
                        Log.e(TAG, log);
                        reviewObj = new Review(date, name, rateUsr, reviewUsr);
                        listReviews.add(reviewObj);
                        rateObj = new Rate();
                        rateObj.setListOfReviews(listReviews);

                    }


                    // menu
                    ArrayList<HashMap<String, ArrayList<HashMap<String, String>>>> menu = value.get(i).get("Menu");
                    List<MenuItem> menuItemList = new ArrayList<>();
                    if (menu != null) {

                        for (int j = 0; j < menu.size(); j++) {

                            ArrayList<HashMap<String, String>> allCategories = menu.get(j).get("allcategories");
                            SubCategory subCategory = null;
                            List<SubCategory> subCategories = new ArrayList<>();
                            Log.e(TAG, "-subcategories");
                            for (HashMap<String, String> category : allCategories) {

                                String name = category.get("name");
                                String basicPrice = category.get("Basic Price");
                                String imgPath = category.get("imgPath");
                                String description = category.get("description");
                                Log.e(TAG, "name :" + name + " ,basic price :" + basicPrice + " ,imgPath :" + imgPath + " ,description :" + description);
                                subCategory = new SubCategory(name, basicPrice, imgPath, description);
                                subCategories.add(subCategory);

                            }
                            String menuItemName = valueName.get(i).get("Menu").get(j).get("name");
                            MenuItem menuItem = new MenuItem(menuItemName, subCategories);
                            menuItemList.add(menuItem);
                        }

                    } else {
                        Log.e(TAG, "there is no menu here");
                    }

                    Menu menuObj = new Menu(menuItemList);
                    int numOfPeople = Integer.parseInt(fullRate.get(i).get("rate").get("fullrate").get("numOfPeople"));
                    Float fullRateInt = Float.parseFloat(fullRate.get(i).get("rate").get("fullrate").get("rate"));
                    String resName = restaurantsNameAndImg.get(i).get("name");
                    String imgPath = restaurantsNameAndImg.get(i).get("imgPath");
                    FullRate fullRateRes = new FullRate(numOfPeople, fullRateInt);
                    rateObj.setFullrate(fullRateRes);
                    int deliveryfee = Integer.parseInt(restaurantsNameAndImg.get(i).get("deliveryfee"));
                    int minOrder = Integer.parseInt(restaurantsNameAndImg.get(i).get("minorder"));
                    String lat = restaurantsNameAndImg.get(i).get("lat");

                    String lang = restaurantsNameAndImg.get(i).get("lang");

                    Location location = new Location(lat, lang);

                    Restaurant restaurant = new Restaurant(resName, imgPath, rateObj, menuObj, minOrder, deliveryfee, location);
                    restaurants.add(restaurant);
                    if(i == id){

                        myRestaurantListener.onFinish(restaurants.get(id));
                        break;

                    }
                    i++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
