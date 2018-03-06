package exampls.com.foodorderingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import exampls.com.foodorderingapp.R;
import exampls.com.foodorderingapp.Realm.RateTable;
import exampls.com.foodorderingapp.Realm.RestaurantTable;
import exampls.com.foodorderingapp.Utils.Constants;
import exampls.com.foodorderingapp.Utils.MyService;
import io.realm.Realm;

/**
 * Created by 450 G1 on 01/03/2018.
 */

public class RestaurantFragment extends android.support.v4.app.Fragment implements CategoriesFragment.CategoriesListener {

    private ImageView imgResView;
    private TextView nameResView;
    private RatingBar ratingBarView;
    private TextView numOfPeopleView;
    private TextView minOrderValueView;
    private TextView deliveryFeeValueView;
    private RestaurantFragmentListener listener;
    int restaurantPosition = -1;
    private static String TAG = "RestaurantFragment";
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RestaurantFragmentListener) {
            this.context = context;
            listener = (RestaurantFragmentListener) context;
        } else {
            throw new IllegalArgumentException("the activity must implement RestaurantFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.restaurant_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        int resId = bundle.getInt(Constants.RES_ID, -1);
        if (resId != -1) {
            Realm.init(getActivity().getApplicationContext());
            Realm realm = Realm.getDefaultInstance();

            RestaurantTable restaurantTable = realm.where(RestaurantTable.class).equalTo("resId", resId).findFirst();
            if (restaurantTable != null) {

                updateWidgetTable(restaurantTable.getResName());

                restaurantPosition = resId;
                String nameRes = restaurantTable.getResName();
                RateTable rateTable = restaurantTable.getRate();
                String imgRes = restaurantTable.getImgPath();
                int minOrder = restaurantTable.getMinOrder();
                int deliveryFee = restaurantTable.getDeliveryFee();

                imgResView = view.findViewById(R.id.img_res);
                Picasso.with(context).load(imgRes).into(imgResView);

                nameResView = view.findViewById(R.id.name_res);
                nameResView.setText(nameRes);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(nameRes);
                ratingBarView = view.findViewById(R.id.rating_res);
                ratingBarView.setRating(rateTable.getFullrate().getRate());

                numOfPeopleView = view.findViewById(R.id.numOfPeople_rate);
                numOfPeopleView.setText("(" + Integer.toString(rateTable.getFullrate().getNumOfPeople()) + ")");

                minOrderValueView = view.findViewById(R.id.min_value);
                minOrderValueView.setText(getString(R.string.egypt) + " " + Integer.toString(minOrder));

                deliveryFeeValueView = view.findViewById(R.id.delivery_value);
                deliveryFeeValueView.setText(getString(R.string.egypt) + " " + Integer.toString(deliveryFee));

                // inflate categories fragment
                if (savedInstanceState == null) {
                    Log.e(TAG, "saved instance is null");
                    CategoriesFragment categoriesFragment = new CategoriesFragment();
                    categoriesFragment.setListener(this);
                    categoriesFragment.setArguments(bundle);

                    InfoFragment infoFragment = new InfoFragment();
                    infoFragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_categories_info, categoriesFragment, Constants.CATEGORY_ID)
                            .commit();
                } else {
                    CategoriesFragment categoriesFragment = (CategoriesFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Constants.CATEGORY_ID);

                    if (categoriesFragment != null) {
                        categoriesFragment.setListener(this);

                    }

                }


            }
        }
    }

    private void updateWidgetTable(String resName) {
        Intent intent = new Intent(getActivity(), MyService.class);
        intent.putExtra("name", resName);
        context.startService(intent);
    }

    public interface RestaurantFragmentListener {
        void onCategoryClick(int restaurantPosition, int categoryPosition);
    }

    @Override
    public void onCategoryClick(int position) {
        // send it to activity
        if (restaurantPosition != -1) {

            listener.onCategoryClick(restaurantPosition, position);

        } else {

            Log.e(TAG, "no restaurant selected");
        }
    }
}


