package exampls.com.foodorderingapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import exampls.com.foodorderingapp.R;

/**
 * Created by 450 G1 on 24/02/2018.
 */

public class AccountFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EmailAccountFragment emailAccountFragment = new EmailAccountFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, emailAccountFragment)
                .commit();
    }
}
