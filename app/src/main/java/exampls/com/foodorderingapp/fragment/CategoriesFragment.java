package exampls.com.foodorderingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import exampls.com.foodorderingapp.R;
import exampls.com.foodorderingapp.Realm.MenuItemTable;
import exampls.com.foodorderingapp.Realm.MenuTable;
import exampls.com.foodorderingapp.Realm.RestaurantTable;
import exampls.com.foodorderingapp.Utils.Constants;
import io.realm.Realm;

/**
 * Created by 450 G1 on 04/03/2018.
 * fragment contains recyclerview in restaurant fragment
 */

public class CategoriesFragment extends Fragment implements AllCategoryAdapter.AllCategoryListener {
    CategoriesListener listener;

    public interface CategoriesListener {

        void onCategoryClick(int position);

    }

    public void setListener(CategoriesListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        int resId = bundle.getInt(Constants.RES_ID, -1);
        if (resId != -1) {
            Realm.init(getActivity().getApplicationContext());
            Realm realm = Realm.getDefaultInstance();

            RestaurantTable restaurantTable = realm.where(RestaurantTable.class).equalTo("resId", resId).findFirst();
            if (restaurantTable != null) {

                MenuTable menuTable = restaurantTable.getMenu();
                AllCategoryAdapter adapter = new AllCategoryAdapter(this, menuTable.getMenuItems());
                RecyclerView allCategoryRv = view.findViewById(R.id.allcategory_recycler);
                allCategoryRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                allCategoryRv.setAdapter(adapter);


            }
        }
    }

    @Override
    public void onCategoryClick(int position) {
        listener.onCategoryClick(position);
    }
}


class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.MyViewHolder> {
    Context context;
    List<MenuItemTable> menuItems;
    LayoutInflater inflater;
    AllCategoryListener listener;

    public AllCategoryAdapter(CategoriesFragment fragment, List<MenuItemTable> menuItems) {
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
