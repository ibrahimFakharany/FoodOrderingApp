package exampls.com.foodorderingapp.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import exampls.com.foodorderingapp.R;



public class AccountFragment extends Fragment implements EmailAccountFragment.EmailAccountFragmentListener {

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private String TAG = "accountfragment";
    private int REQUEST_CODE = 9001;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*if (savedInstanceState == null) {

            EmailAccountFragment emailAccountFragment = new EmailAccountFragment();
            emailAccountFragment.setListener(this);
            getFragmentManager().beginTransaction()
                    .replace(R.id.email_frame, emailAccountFragment)
                    .commit();

        }*/

        view.findViewById(R.id.google_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail().build();


        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onEmailTrue(String email) {
        AccountDetailFragment detailFragment = new AccountDetailFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.email_frame, detailFragment)
                .commit();
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {

            Log.e(TAG, "user is null");

        } else {

            Log.e(TAG, "email " + user.getEmail());

        }
    }


    private void signInWithGoogle() {

        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException ex) {
                Log.e(TAG, "API EXCEPTION");
                ex.printStackTrace();
            }

        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.e(TAG, "firebaseAuthWithGoogle");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task != null) {
                    FirebaseUser user = mAuth.getCurrentUser();

                } else {
                }
            }
        });
    }
}
