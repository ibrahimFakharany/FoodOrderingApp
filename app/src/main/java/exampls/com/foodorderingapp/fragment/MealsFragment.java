package exampls.com.foodorderingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class MealsFragment extends android.support.v4.app.Fragment implements MealsAdapter.MealsAdapterListener {
    Context context;
    MealsListener listener;

    @Override
    public void onMealClick(int mealPositino) {
        listener.onMealClick(mealPositino);
    }

    public interface MealsListener {
        void onMealClick(int mealPosition);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (this.context instanceof MealsListener) {
            listener = (MealsListener) context;
        } else {
            throw new IllegalArgumentException("activity must implement MealsListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            int resId = bundle.getInt(Constants.RES_ID);
            int categoryId = bundle.getInt(Constants.CATEGORY_ID);


            Realm.init(context.getApplicationContext());
            Realm realm = Realm.getDefaultInstance();
            RestaurantTable restaurantTable = realm.where(RestaurantTable.class).equalTo("resId", resId).findFirst();
            if (restaurantTable != null) {
                MenuTable menuTable = restaurantTable.getMenu();

                if (menuTable != null) {
                    RealmList<MenuItemTable> list = menuTable.getMenuItems();

                    if (list != null) {
                        List<SubCategoryTable> subCategoryTableList = list.get(categoryId).getSubCategories();

                        if (subCategoryTableList != null) {

                            MealsAdapter mealsAdapter = new MealsAdapter(context, subCategoryTableList);
                            mealsAdapter.setListener(this);
                            RecyclerView mealsRv = view.findViewById(R.id.meals_rv);
                            mealsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            mealsRv.setAdapter(mealsAdapter);

                        }
                    }
                }
            }
        }


    }
}

class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MyViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<SubCategoryTable> list;

    public MealsAdapter(Context context, List<SubCategoryTable> list) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;

    }

    MealsAdapterListener listener;

    public void setListener(MealsAdapterListener listener) {

        this.listener = listener;

    }

    public interface MealsAdapterListener {

        void onMealClick(int mealPositino);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_category_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        SubCategoryTable subCategoryTable = list.get(position);
        String name = subCategoryTable.getName();
        String imgPath = subCategoryTable.getImgPath();
        String basicPrice = subCategoryTable.getBasicPrice();

        if ((name != null) && (basicPrice != null)) {
            holder.nameMeal.setText(name);
            holder.priceMeal.setText(context.getString(R.string.egypt).concat(" ").concat(basicPrice));
        }
        if (imgPath != null) {

            Picasso.with(context).load(imgPath).into(holder.imgMeal);

        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMealClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        TextView nameMeal;
        TextView priceMeal;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgMeal = itemView.findViewById(R.id.img_meal);
            nameMeal = itemView.findViewById(R.id.name_meal);
            priceMeal = itemView.findViewById(R.id.price_meal);
            relativeLayout = itemView.findViewById(R.id.relative_item_meal);

        }
    }

}