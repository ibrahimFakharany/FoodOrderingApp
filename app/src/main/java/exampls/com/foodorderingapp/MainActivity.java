package exampls.com.foodorderingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yarolegovich.slidingrootnav.SlidingRootNav;

import exampls.com.foodorderingapp.Utils.PrepareLayout;
import exampls.com.foodorderingapp.fragment.AccountFragment;
import exampls.com.foodorderingapp.fragment.RestaurantsFragment;
import exampls.com.foodorderingapp.menu.DrawerAdapter;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PrepareLayout prepareLayout = new PrepareLayout(this, savedInstanceState, toolbar);
        slidingRootNav = prepareLayout.getSlidingRootNavBuilder();
        DrawerAdapter adapter = prepareLayout.getDrawerAdapter();
        adapter.setListener(this);
        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        Fragment selectedScreen = null;
        if (savedInstanceState == null) {
            selectedScreen = new RestaurantsFragment();
            showFragment(selectedScreen);
        }
    }

    @Override
    public void onItemSelected(int position) {
        slidingRootNav.closeMenu();
        Fragment selectedScreen = null;
        switch (position) {
            case 0:
                selectedScreen = new RestaurantsFragment();
                break;
            case 1:
                selectedScreen = new AccountFragment();

                break;
           /* default:
                selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
                showFragment(selectedScreen);
                break;*/
        }
        showFragment(selectedScreen);

    }

    private void showFragment(Fragment fragment) {
        if (fragment != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }

    }
}
