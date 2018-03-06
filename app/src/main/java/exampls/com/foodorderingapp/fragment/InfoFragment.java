package exampls.com.foodorderingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import exampls.com.foodorderingapp.Models.Rate;
import exampls.com.foodorderingapp.Models.Restaurant;
import exampls.com.foodorderingapp.Models.Review;
import exampls.com.foodorderingapp.R;
import exampls.com.foodorderingapp.Utils.Constants;
import exampls.com.foodorderingapp.Utils.NetworkCalls;

/**
 * Created by 450 G1 on 04/03/2018.
 * fragment for info about restaurant
 */

public class InfoFragment extends Fragment implements OnMapReadyCallback, NetworkCalls.MyRestaurantListener {
    Restaurant restaurantTable = null;
    private ImageView imgResView;
    private TextView nameResView;
    private RatingBar ratingBarView;
    private TextView numOfPeopleView;
    private TextView minOrderValueView;
    private TextView deliveryFeeValueView;
    Context context;
    String TAG = "infofragment";
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // getting reviews
        Bundle bundle = getArguments();
        this.view = view;
        int resId = bundle.getInt(Constants.RES_ID, -1);
        if (resId != -1) {
            NetworkCalls networkCalls = new NetworkCalls();
            networkCalls.setMyRestaurantListener(this);
            networkCalls.findRestaurantsImgAndName(resId);


        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (restaurantTable != null) {
            LatLng marker = new LatLng(Double.parseDouble(restaurantTable.getLocation().getLat()), Double.parseDouble(restaurantTable.getLocation().getLang()));

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));

            googleMap.addMarker(new MarkerOptions().title(getString(R.string.title_here)).position(marker));
        }
    }

    @Override
    public void onFinish(Restaurant restaurant) {
        if (restaurant != null) {
            this.restaurantTable = restaurant;

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


            TextView rateValue = view.findViewById(R.id.rating_value);
            rateValue.setText(Float.toString(rateTable.getFullrate().getRate()));
            ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getActivity(), rateTable.getListOfReviews());
            RecyclerView reviewsRecycler = view.findViewById(R.id.reviews_rv);
            reviewsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            reviewsRecycler.setAdapter(reviewsAdapter);

            com.google.android.gms.maps.SupportMapFragment fragment = (com.google.android.gms.maps.SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);

            fragment.getMapAsync(this);
        }
    }
}

class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolde> {
    Context context;
    LayoutInflater inflater;
    List<Review> reviews;

    public ReviewsAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.reviews = reviews;
    }

    @Override
    public MyViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolde(inflater.inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolde holder, int position) {
        Review review = reviews.get(position);
        holder.dateView.setText(review.getDate());
        holder.nameView.setText(review.getName());
        holder.reviewView.setText(review.getReview());
        holder.rateView.setText(Integer.toString(review.getRate()));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class MyViewHolde extends RecyclerView.ViewHolder {
        TextView dateView, nameView, reviewView, rateView;

        public MyViewHolde(View itemView) {
            super(itemView);
            dateView = itemView.findViewById(R.id.date);
            nameView = itemView.findViewById(R.id.name);
            reviewView = itemView.findViewById(R.id.review);
            rateView = itemView.findViewById(R.id.rate);

        }
    }

}