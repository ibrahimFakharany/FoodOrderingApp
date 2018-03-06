package exampls.com.foodorderingapp.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

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
import exampls.com.foodorderingapp.R;
import exampls.com.foodorderingapp.Realm.FullRateTable;
import exampls.com.foodorderingapp.Realm.LocationTable;
import exampls.com.foodorderingapp.Realm.MenuItemTable;
import exampls.com.foodorderingapp.Realm.MenuTable;
import exampls.com.foodorderingapp.Realm.RateTable;
import exampls.com.foodorderingapp.Realm.RestaurantTable;
import exampls.com.foodorderingapp.Realm.ReviewTable;
import exampls.com.foodorderingapp.Realm.SubCategoryTable;
import exampls.com.foodorderingapp.RestaurantActivity;
import exampls.com.foodorderingapp.Utils.Constants;
import exampls.com.foodorderingapp.Utils.NetworkCalls;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by 450 G1 on 20/02/2018.
 */

class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.MyViewHolder> {
    Context context;
    List<Restaurant> restaurants;
    LayoutInflater inflater;
    RestaurantClickListener clickListener;

    public void setClickListener(RestaurantClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RestaurantsAdapter(Context context, List<Restaurant> restaurants) {

        this.context = context;
        this.restaurants = restaurants;
        inflater = LayoutInflater.from(context);

    }

    public interface RestaurantClickListener {
        void onRestaurantClick(int resId);
    }

    @Override
    public RestaurantsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantsAdapter.MyViewHolder(inflater.inflate(R.layout.item_restaurant, parent, false));
    }

    @Override
    public void onBindViewHolder(RestaurantsAdapter.MyViewHolder holder, final int position) {
        final Restaurant restaurant = restaurants.get(position);

        Picasso.with(context).load(restaurants.get(position).getImgPath()).into(holder.imgRestaurant);
        holder.nameRestaurant.setText(restaurant.getName());
        //
        holder.ratingRestaurant.setRating(restaurant.getRate().getFullrate().getRate());
        holder.numOfPeople.setText("(" + restaurant.getRate().getFullrate().getNumOfPeople() + ")");


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // apply the listener and pass the id of the restaurant
                clickListener.onRestaurantClick(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRestaurant;
        TextView nameRestaurant;
        RatingBar ratingRestaurant;
        TextView numOfPeople;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgRestaurant = itemView.findViewById(R.id.img_restaurant);
            nameRestaurant = itemView.findViewById(R.id.restaurant_name);
            ratingRestaurant = itemView.findViewById(R.id.ratingBar);
            numOfPeople = itemView.findViewById(R.id.numOfPeople_rate);
            relativeLayout = itemView.findViewById(R.id.item_restaurant_relative);
        }
    }
}

public class RestaurantsFragment extends Fragment implements NetworkCalls.MyListener, RestaurantsAdapter.RestaurantClickListener {
    RecyclerView recyclerView;
    Context context;
    private static final String TAG = "RestaurantsFragment";


    public RestaurantsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurants, container, false);
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
            Log.e(TAG, "name " + resName + " imgPath " + imgPath);
            FullRate fullRateRes = new FullRate(numOfPeople, fullRateInt);
            rateObj.setFullrate(fullRateRes);
            int deliveryfee = Integer.parseInt(restaurantsNameAndImg.get(i).get("deliveryfee"));
            int minOrder = Integer.parseInt(restaurantsNameAndImg.get(i).get("minorder"));
            String lat = restaurantsNameAndImg.get(i).get("lat");

            String lang = restaurantsNameAndImg.get(i).get("lang");

            Location location = new Location(lat, lang);

            Restaurant restaurant = new Restaurant(resName, imgPath, rateObj, menuObj, minOrder, deliveryfee, location);
            restaurants.add(restaurant);
            i++;
            Log.e(TAG, "************* Restaurant **************");
        }
        if (restaurants != null) {

            // save in database
            savedRestaurants(restaurants);


            Log.e(TAG, "after saveing data ");
            // load in views
            RestaurantsAdapter restaurantsAdapter = new RestaurantsAdapter(context, restaurants);
            restaurantsAdapter.setClickListener(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(restaurantsAdapter);

        }
    }

    private void savedRestaurants(List<Restaurant> restaurants) {
        Log.e(TAG, "save to data base");
        Realm.init(context.getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        RestaurantTable restaurantTable = null;
        // init Realm
        int i = 0;
        for (Restaurant restaurant : restaurants) {
            realm.beginTransaction();
            restaurantTable = new RestaurantTable();
            restaurantTable.setResId(i);
            Log.e(TAG, "i >> "+i+"");
            i++;
            String restaurantName = restaurant.getName();
            String imgPath = restaurant.getImgPath();
            restaurantTable.setResName(restaurantName);
            restaurantTable.setImgPath(imgPath);
            // menu
            // subcategory
            RealmList<SubCategoryTable> subCategoriesListTable = null;
            SubCategoryTable subCategoryTable = null;
            MenuItemTable menuItemTable = null;
            RealmList<MenuItemTable> menuItemTables = new RealmList<>();


            for (MenuItem item : restaurant.getMenu().getMenuItems()) {
                menuItemTable = new MenuItemTable();
                menuItemTable.setName(item.getName());
                subCategoriesListTable = new RealmList<>();
                for (SubCategory subCategory : item.getSubCategories()) {
                    subCategoryTable = new SubCategoryTable();
                    subCategoryTable.setBasicPrice(subCategory.getBasicPrice());
                    subCategoryTable.setDescription(subCategory.getDescription());
                    subCategoryTable.setImgPath(subCategory.getImgPath());
                    subCategoryTable.setName(subCategory.getName());
                    subCategoriesListTable.add(subCategoryTable);
                }
                menuItemTable.setSubCategories(subCategoriesListTable);
                menuItemTables.add(menuItemTable);
            }
            MenuTable menuTable = new MenuTable(menuItemTables);
            restaurantTable.setMenu(menuTable);


            // Rate
            RateTable rateTable = new RateTable();
            FullRateTable fullRateTable = new FullRateTable();
            int numOfPeople = restaurant.getRate().getFullrate().getNumOfPeople();

            float rate = restaurant.getRate().getFullrate().getRate();

            fullRateTable.setNumOfPeople(numOfPeople);
            fullRateTable.setRate(rate);
            rateTable.setFullrate(fullRateTable);
            RealmList<ReviewTable> reviewTables = new RealmList<>();
            for (Review review : restaurant.getRate().getListOfReviews()) {
                reviewTables.add(new ReviewTable(review.getDate(), review.getName(), review.getRate(), review.getReview()));
            }
            rateTable.setListOfReviews(reviewTables);
            restaurantTable.setRate(rateTable);
            restaurantTable.setMinOrder(restaurant.getMinOrder());
            restaurantTable.setDeliveryFee(restaurant.getDeliveryOrder());
            LocationTable locationTable = new LocationTable(restaurant.getLocation().getLat(), restaurant.getLocation().getLang());
            restaurantTable.setLocation(locationTable);

            realm.copyToRealmOrUpdate(restaurantTable);
            realm.commitTransaction();


        }

    }
    @Override
    public void onRestaurantClick(int resId) {
        Intent intent = new Intent(getActivity(), RestaurantActivity.class);
        intent.putExtra(Constants.RES_ID, resId);
        startActivity(intent);
    }
}
