package exampls.com.foodorderingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import exampls.com.foodorderingapp.Models.Restaurant;
import exampls.com.foodorderingapp.R;

/**
 * Created by 450 G1 on 19/02/2018.
 */

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.MyViewHolder> {
    Context context;
    List<Restaurant> restaurants;
    LayoutInflater inflater;

    public RestaurantsAdapter(Context context, List<Restaurant> restaurants) {

        this.context = context;
        this.restaurants = restaurants;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_restaurant, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);

        Picasso.with(context).load(restaurants.get(position).getImgPath()).into(holder.imgRestaurant);
        holder.nameRestaurant.setText(restaurant.getName());
        //
        holder.ratingRestaurant.setRating(restaurant.getRate().getFullrate().getRate());
        holder.numOfPeople.setText("("+restaurant.getRate().getFullrate().getNumOfPeople()+")");
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

        public MyViewHolder(View itemView) {
            super(itemView);

            imgRestaurant = itemView.findViewById(R.id.img_restaurant);
            nameRestaurant = itemView.findViewById(R.id.restaurant_name);
            ratingRestaurant = itemView.findViewById(R.id.ratingBar);
            numOfPeople = itemView.findViewById(R.id.numOfPeople_rate);
        }
    }
}
