package exampls.com.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import exampls.com.foodorderingapp.Utils.Constants;
import exampls.com.foodorderingapp.fragment.InfoFragment;
import exampls.com.foodorderingapp.fragment.MealsFragment;
import exampls.com.foodorderingapp.fragment.RestaurantFragment;

public class RestaurantActivity extends AppCompatActivity implements RestaurantFragment.RestaurantFragmentListener, MealsFragment.MealsListener {
    boolean twoPane = false;
    int resId, categoryId;

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                } else
                    NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.info:
                // inflate fragment info
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for (int i = 0; i < fragments.size(); i++) {
                    if (fragments.get(i).getTag() == Constants.RESTAURANT_RATING) {
                        return true;
                    }
                }
                InfoFragment infoFragment = new InfoFragment();
                infoFragment.setArguments(getIntent().getExtras());

                if(findViewById(R.id.restaurant_frame)!=null){

                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.restaurant_frame, infoFragment, Constants.RESTAURANT_RATING)
                            .addToBackStack(Constants.RESTAURANT_RATING)
                            .commit();

                }



                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (savedInstanceState == null) {

            Bundle bundle = getIntent().getExtras();
            RestaurantFragment restaurantFragment = new RestaurantFragment();
            restaurantFragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
            ft1.replace(R.id.restaurant_frame, restaurantFragment, Constants.RESTAURANT_FRAGMENT_TAG)
                    .commit();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.restaurant_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCategoryClick(int restaurantPosition, int categoryPosition) {
        resId = restaurantPosition;
        categoryId = categoryPosition;
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.RES_ID, restaurantPosition);
        bundle.putInt(Constants.CATEGORY_ID, categoryPosition);
        MealsFragment mealsFragment = new MealsFragment();
        mealsFragment.setArguments(bundle);
        ft.replace(R.id.restaurant_frame, mealsFragment, Constants.MEALS_FRAGMENT_TAG)
                .addToBackStack(Constants.MEAL_FRAGMENT_TAG)
                .commit();


    }

    @Override
    public void onMealClick(int mealPosition) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.RES_ID, resId);
        bundle.putInt(Constants.CATEGORY_ID, categoryId);
        bundle.putInt(Constants.MEAL_ID, mealPosition);
        if (twoPane) {

            // TODO: IMPLEMENT TWO PANE

        } else {
            Intent intent = new Intent(this, MealDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
