package exampls.com.foodorderingapp.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import exampls.com.foodorderingapp.MainActivity;
import exampls.com.foodorderingapp.R;
import exampls.com.foodorderingapp.menu.DrawerAdapter;
import exampls.com.foodorderingapp.menu.DrawerItem;
import exampls.com.foodorderingapp.menu.SimpleItem;

/**
 * Created by 450 G1 on 19/02/2018.
 */

public class PrepareLayout {
    private SlidingRootNav slidingRootNav;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_MESSAGES = 2;
    private static final int POS_CART = 3;
    public Context context;

    Bundle savedInstanceState;
    Toolbar toolbar;
    MainActivity mainActivity;


    public DrawerAdapter getDrawerAdapter() {
        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ACCOUNT),
                createItemFor(POS_MESSAGES),
                createItemFor(POS_CART)));
        adapter.setSelected(POS_DASHBOARD);

        return adapter;
    }

    public PrepareLayout(MainActivity mainActivity, Bundle savedInstanceState, Toolbar toolbar) {
        this.context = mainActivity;
        this.savedInstanceState = savedInstanceState;
        this.toolbar = toolbar;
        this.mainActivity = mainActivity;
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();
    }

    public SlidingRootNav getSlidingRootNavBuilder() {
        slidingRootNav = new SlidingRootNavBuilder(mainActivity)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .withDragDistance(200)
                .withRootViewScale(.8f)
                .withRootViewElevation(0)
                .withRootViewYTranslation(6)
                .inject();
        return slidingRootNav;
    }


    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return context.getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = context.getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(context, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(context, res);
    }
}
