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

import exampls.com.foodorderingapp.R;
import exampls.com.foodorderingapp.Realm.MenuItemTable;
import exampls.com.foodorderingapp.Realm.MenuTable;
import exampls.com.foodorderingapp.Realm.RestaurantTable;
import exampls.com.foodorderingapp.Realm.SubCategoryTable;
import exampls.com.foodorderingapp.Utils.Constants;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by 450 G1 on 02/03/2018.
 */

public class MealDetailFragment extends Fragment {
    private static final String TAG = "Mealdetailfragment";
    Context context;
    ImageView imgMealView;
    TextView nameMealView;
    TextView basicPriceMealView;
    TextView descriptionView;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle  =getArguments();
        int resId = bundle.getInt(Constants.RES_ID);
        int catId = bundle.getInt(Constants.CATEGORY_ID);
        int mealId = bundle.getInt(Constants.MEAL_ID);

        Realm.init(context.getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        RestaurantTable restaurantTable = realm.where(RestaurantTable.class).equalTo("resId", resId).findFirst();

        if (restaurantTable != null) {
            MenuTable menuTable = restaurantTable.getMenu();

            if (menuTable != null) {
                RealmList<MenuItemTable> list = menuTable.getMenuItems();

                if (list != null) {
                    List<SubCategoryTable> subCategoryTableList = list.get(catId).getSubCategories();

                    if (subCategoryTableList != null) {

                        SubCategoryTable subCategoryTable = subCategoryTableList.get(mealId);
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
