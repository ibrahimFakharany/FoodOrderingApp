package exampls.com.foodorderingapp.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import exampls.com.foodorderingapp.Models.Menu;
import exampls.com.foodorderingapp.Models.MenuItem;
import exampls.com.foodorderingapp.Models.Restaurant;
import exampls.com.foodorderingapp.Models.SubCategory;
import exampls.com.foodorderingapp.R;
import exampls.com.foodorderingapp.Utils.Constants;
import exampls.com.foodorderingapp.Utils.NetworkCalls;


/**
 * Created by 450 G1 on 02/03/2018.
 */

public class MealDetailFragment extends Fragment implements NetworkCalls.MyRestaurantListener {
    private static final String TAG = "Mealdetailfragment";
    Context context;
    ImageView imgMealView;
    TextView nameMealView;
    TextView basicPriceMealView;
    TextView descriptionView;
    int catId = -1;
    int mealId = -1;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_detail, container, false);
    }
    View view;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle  =getArguments();
        int resId = bundle.getInt(Constants.RES_ID);
         catId = bundle.getInt(Constants.CATEGORY_ID);
         mealId = bundle.getInt(Constants.MEAL_ID);

        this.view = view;
        NetworkCalls networkCalls = new NetworkCalls();
        networkCalls.setMyRestaurantListener(this);
        networkCalls.findRestaurantsImgAndName(resId);


    }

    @Override
    public void onFinish(Restaurant restaurant) {
        if (restaurant != null) {
            Menu menuTable = restaurant.getMenu();

            if (menuTable != null) {
                List<MenuItem> list = menuTable.getMenuItems();

                if (list != null) {
                    List<SubCategory> subCategoryTableList = list.get(catId).getSubCategories();

                    if (subCategoryTableList != null) {

                        SubCategory subCategoryTable = subCategoryTableList.get(mealId);
                        String name = subCategoryTable.getName();
                        String basicPrice = subCategoryTable.getBasicPrice();
                        String description = subCategoryTable.getDescription();
                        String imgPath = subCategoryTable.getImgPath();

                        imgMealView  = view.findViewById(R.id.back_drop_image);
                        Picasso.with(context).load(imgPath).into(imgMealView);

                        descriptionView = view.findViewById(R.id.description_text);
                        descriptionView.setText(description);

                        nameMealView = view.findViewById(R.id.name_meal);
                        nameMealView.setText(name);

                        basicPriceMealView = view.findViewById(R.id.basic_price_value);
                        basicPriceMealView.setText(getString(R.string.egypt)+basicPrice);


                    }
                }
            }
        }
    }
}
