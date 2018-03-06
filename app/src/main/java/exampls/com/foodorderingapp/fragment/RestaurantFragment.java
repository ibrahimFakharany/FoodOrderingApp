package exampls.com.foodorderingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import com.squareup.picasso.Picasso;

import java.util.List;

import exampls.com.foodorderingapp.Models.Menu;
import exampls.com.foodorderingapp.Models.MenuItem;
import exampls.com.foodorderingapp.Models.Rate;
import exampls.com.foodorderingapp.Models.Restaurant;
import exampls.com.foodorderingapp.R;
import exampls.com.foodorderingapp.Utils.Constants;
import exampls.com.foodorderingapp.Utils.MyService;
import exampls.com.foodorderingapp.Utils.NetworkCalls;

/**
 * Created by 450 G1 on 01/03/2018.
 */

public class RestaurantFragment extends android.support.v4.app.Fragment implements AllCategoryAdapter.AllCategoryListener, NetworkCalls.MyRestaurantListener {

    private static boolean ENTERED_ON_FINISH = false;
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
    View view = null;
    Bundle savedInstanceState;
    Bundle bundle;

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
        return inflater.inflate(R.layout.fragment_restaurant, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bundle = getArguments();
        this.view = view;
        int resId = bundle.getInt(Constants.RES_ID, -1);
        this.savedInstanceState = savedInstanceState;

        if (resId != -1) {

            this.restaurantPosition = resId;
            NetworkCalls networkCalls = new NetworkCalls();
            networkCalls.setMyRestaurantListener(this);
            networkCalls.findRestaurantsImgAndName(resId);


        }
    }

    private void updateWidgetTable(String resName) {
        Intent intent = new Intent(getActivity(), MyService.class);
        intent.putExtra("name", resName);
        context.startService(intent);
    }


    @Override
    public void onFinish(Restaurant restaurant) {

        Log.e(TAG, "onFinish");

        if (restaurant != null) {

            updateWidgetTable(restaurant.getName());

            String nameRes = restaurant.getName();
            Rate rateTable = restaurant.getRate();
            String imgRes = restaurant.getImgPath();
            int minOrder = restaurant.getMinOrder();
            int deliveryFee = restaurant.getDeliveryOrder();

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

            Menu menuTable = restaurant.getMenu();
            AllCategoryAdapter adapter = new AllCategoryAdapter(this, menuTable.getMenuItems());
            RecyclerView allCategoryRv = view.findViewById(R.id.allcategory_recycler);
            allCategoryRv.setLayoutManager(new LinearLayoutManager(getActivity()));
            allCategoryRv.setAdapter(adapter);

        }
    }

    public interface RestaurantFragmentListener {

        void onCategoryClick(int restaurantPosition, int categoryPosition);

    }


    @Override
    public void onCategoryClick(int position) {

        listener.onCategoryClick(restaurantPosition, position);
    }
}
class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.MyViewHolder> {
    Context context;
    List<MenuItem> menuItems;
    LayoutInflater inflater;
    AllCategoryListener listener;

    public AllCategoryAdapter(RestaurantFragment fragment, List<MenuItem> menuItems) {
        this.context = fragment.getActivity();
        this.menuItems = menuItems;
        inflater = LayoutInflater.from(this.context);
        if (fragment instanceof AllCategoryListener) {

            listener = (AllCategoryListener) fragment;

        } else
            throw new IllegalArgumentException("The fragment must implement AllCategoryListener");
    }

    public interface AllCategoryListener {

        void onCategoryClick(int position);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_allcategory_menu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.nameCategory.setText(menuItems.get(position).getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCategoryClick(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameCategory;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            nameCategory = itemView.findViewById(R.id.name_category);
            relativeLayout = itemView.findViewById(R.id.item_menu_relative);

        }
    }

}


