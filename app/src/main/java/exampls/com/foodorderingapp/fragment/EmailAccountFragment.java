package exampls.com.foodorderingapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exampls.com.foodorderingapp.R;

/**
 * Created by 450 G1 on 24/02/2018.
 */

public class EmailAccountFragment extends Fragment  {
    Button myFacebookLoginBtn;

    TextView response;
    FirebaseAuth firebaseAuth = null;
    String TAG = "emailaccountfragment";

    EmailAccountFragmentListener listener;
    public interface EmailAccountFragmentListener {

        void onEmailTrue(String email);

    }

    TextInputLayout emailEt;
    Button nextStep;
    FirebaseAuth.AuthStateListener authStateListener;

    public void setListener(EmailAccountFragmentListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateview in fragment");
        return inflater.inflate(R.layout.fragment_account_email, container, false);
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated");

        nextStep = view.findViewById(R.id.chk_email_btn);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getEditText().getText().toString().trim();
                if(isEmailValid(email)){

                    listener.onEmailTrue(email);

                }
                else{

                    Log.e(TAG, "not email");

                }
            }
        });

        RelativeLayout relativeLayout = view.findViewById(R.id.relative_root);
        emailEt = (TextInputLayout) view.findViewById(R.id.email_txt_input);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailEt.setSelected(false);
            }
        });

    }
}
