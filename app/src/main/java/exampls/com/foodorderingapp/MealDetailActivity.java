package exampls.com.foodorderingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import exampls.com.foodorderingapp.Utils.Constants;
import exampls.com.foodorderingapp.fragment.MealDetailFragment;

public class MealDetailActivity extends AppCompatActivity {
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            MealDetailFragment mealDetailFragment = new MealDetailFragment();
            mealDetailFragment.setArguments(getIntent().getExtras());
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_meal_detail_activity, mealDetailFragment, Constants.MEAL_FRAGMENT_TAG)
                    .commit();

        }


    }
}
