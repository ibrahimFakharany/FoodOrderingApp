package exampls.com.foodorderingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import exampls.com.foodorderingapp.R;
import exampls.com.foodorderingapp.Realm.RateTable;
import exampls.com.foodorderingapp.Realm.RestaurantTable;
import exampls.com.foodorderingapp.Realm.ReviewTable;
import exampls.com.foodorderingapp.Utils.Constants;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by 450 G1 on 04/03/2018.
 * fragment for info about restaurant
 */

public class InfoFragment extends Fragment implements OnMapReadyCallback {
    RestaurantTable restaurantTable =null;
    String  TAG = "infofragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // getting reviews
        Bundle bundle  = getArguments();

        int resId = bundle.getInt(Constants.RES_ID, -1);
        if (resId != -1) {
            Realm.init(getActivity().getApplicationContext());
            Realm realm = Realm.getDefaultInstance();

            RestaurantTable restaurantTable = realm.where(RestaurantTable.class).equalTo("resId", resId).findFirst();
            if (restaurantTable != null) {
                this.restaurantTable = restaurantTable;
                RateTable rateTable = restaurantTable.getRate();
                TextView rateValue = view.findViewById(R.id.rating_value);
                rateValue.setText(Float.toString(rateTable.getFullrate().getRate()));
                Log.e(TAG, "list of reviews "+ rateTable.getListOfReviews().size());
                ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getActivity(), rateTable.getListOfReviews());
                RecyclerView reviewsRecycler = view.findViewById(R.id.reviews_rv);
                reviewsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                reviewsRecycler.setAdapter(reviewsAdapter);


                com.google.android.gms.maps.SupportMapFragment fragment = (com.google.android.gms.maps.SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);

                fragment.getMapAsync(this);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(restaurantTable != null){
            LatLng marker = new LatLng(Double.parseDouble(restaurantTable.getLocation().getLat()),Double.parseDouble(restaurantTable.getLocation().getLang()));

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));

            googleMap.addMarker(new MarkerOptions().title("title here").position(marker));
        }
    }
}
class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolde> {
    Context context;
    LayoutInflater inflater;
    RealmList<ReviewTable> reviews;

    public ReviewsAdapter(Context context, RealmList<ReviewTable> reviews) {
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
        ReviewTable review = reviews.get(position);
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