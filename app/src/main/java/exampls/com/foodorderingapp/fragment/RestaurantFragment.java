package exampls.com.foodorderingapp.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exampls.com.foodorderingapp.Adapters.RestaurantsAdapter;
import exampls.com.foodorderingapp.Models.FullRate;
import exampls.com.foodorderingapp.Models.Menu;
import exampls.com.foodorderingapp.Models.MenuItem;
import exampls.com.foodorderingapp.Models.Rate;
import exampls.com.foodorderingapp.Models.Restaurant;
import exampls.com.foodorderingapp.Models.Review;
import exampls.com.foodorderingapp.Models.SubCategory;
import exampls.com.foodorderingapp.R;
import exampls.com.foodorderingapp.Utils.NetworkCalls;

/**
 * Created by 450 G1 on 20/02/2018.
 */

public class RestaurantFragment extends Fragment implements NetworkCalls.MyListener {
    RecyclerView recyclerView;
    Context context;
    private static final String TAG = "RestaurantFragment";
    ArrayList<Restaurant> myRestaurants = new ArrayList<>();


    public RestaurantFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.restaurant_rv);
        NetworkCalls networkCalls = new NetworkCalls();
        networkCalls.setMyListener(this);
        networkCalls.findRestaurantsImgAndName();




    }

    @Override
    public void onFinish(DataSnapshot dataSnapshot) {

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
            for (HashMap<String, String> review : allReviews) {

                String date = review.get("date");
                String name = review.get("name");
                int rateUsr = Integer.parseInt(review.get("rate"));
                String reviewUsr = review.get("review");
                String log = "date :" + date + " ,name :" + name + " ,rate :" + rateUsr + " ,review :" + reviewUsr;
                Log.e(TAG, log);
                reviewObj = new Review(date, name, rateUsr, reviewUsr);
                List<Review> listReviews = new ArrayList<>();
                listReviews.add(reviewObj);
                rateObj = new Rate();
                rateObj.setListOfReviews(listReviews);

            }


            // menu
            ArrayList<HashMap<String, ArrayList<HashMap<String, String>>>> menu = value.get(i).get("Menu");
            List<MenuItem> menuItemList  =new ArrayList<>();
            if(menu != null){

                for(int j = 0; j < menu.size(); j++){

                    ArrayList<HashMap<String, String>> allCategories = menu.get(j).get("allcategories");
                    SubCategory subCategory = null;
                    List<SubCategory> subCategories = new ArrayList<>();
                    Log.e(TAG, "-subcategories");
                    for(HashMap<String, String> category : allCategories){

                        String name = category.get("name");
                        String basicPrice = category.get("Basic Price");
                        String imgPath  = category.get("imgPath");
                        String description = category.get("description");
                        Log.e(TAG, "name :"+name+ " ,basic price :"+ basicPrice +" ,imgPath :"+ imgPath+ " ,description :"+description);
                        subCategory = new SubCategory(name, basicPrice,imgPath,description);
                        subCategories.add(subCategory);

                    }
                    String menuItemName = valueName.get(i).get("Menu").get(j).get("name");
                    MenuItem menuItem = new MenuItem(menuItemName, subCategories);
                    menuItemList.add(menuItem);
                }

            }else {
                Log.e(TAG, "there is no menu here");
            }

            Menu menuObj = new Menu(menuItemList);
            int numOfPeople = Integer.parseInt(fullRate.get(i).get("rate").get("fullrate").get("numOfPeople"));
            Float fullRateInt = Float.parseFloat(fullRate.get(i).get("rate").get("fullrate").get("rate"));
            String resName = restaurantsNameAndImg.get(i).get("name");
            String imgPath = restaurantsNameAndImg.get(i).get("imgPath");
            Log.e(TAG, "name "+ resName+ " imgPath "+ imgPath);
            FullRate fullRateRes = new FullRate(numOfPeople, fullRateInt);
            rateObj.setFullrate(fullRateRes);

            Restaurant restaurant = new Restaurant(resName, imgPath, rateObj, menuObj);
            restaurants.add(restaurant);
            i++;
            Log.e(TAG, "************* Restaurant **************");
        }
        if(restaurants!= null){

            // load in views

            RestaurantsAdapter restaurantsAdapter = new RestaurantsAdapter(context,restaurants);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(restaurantsAdapter);

        }
    }
}
