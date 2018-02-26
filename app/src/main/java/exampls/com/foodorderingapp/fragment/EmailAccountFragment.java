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
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import exampls.com.foodorderingapp.R;

/**
 * Created by 450 G1 on 24/02/2018.
 */

public class EmailAccountFragment extends Fragment implements View.OnClickListener {
    Button myFacebookLoginBtn;
    LoginButton facebookLoginButton;
    CallbackManager callbackManager;
    TextView response;
    FirebaseAuth firebaseAuth = null;
    String TAG = "emailaccountfragment";

    FirebaseAuth.AuthStateListener authStateListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_email, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myFacebookLoginBtn = view.findViewById(R.id.myFacebookButton);
        myFacebookLoginBtn.setOnClickListener(this);

        facebookLoginButton = view.findViewById(R.id.facebookButton);
        response = view.findViewById(R.id.response);
        callbackManager =CallbackManager.Factory.create();

        facebookLoginButton.setReadPermissions("email");
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleUserLogin(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.e(TAG, "in firebase auth state changed");
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(AccessToken.getCurrentAccessToken() != null){

                    response.setText("this in listenere "+ "access token is null");

                }


                if(user != null){
                    String email = user.getEmail();
                    String username = user.getDisplayName();

                    Log.e(TAG, email.concat(" ").concat(username));
                    response.setText(email.concat(" ").concat(username));

                }else {

                    Log.e(TAG, "user obj in firebase auth listener is null ");


                }
            }
        };

    }

    private void handleUserLogin(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String  email = task.getResult().getUser().getEmail();
                Log.e(TAG, "onComplete");
                if(!task.isSuccessful()){

                    System.out.println("task is not successfull");

                }
                response.setText(email);

            }
        } );
    }

    @Override
    public void onClick(View view) {
        facebookLoginButton.performClick();
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.e(TAG, "onStart");
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    public void onStop() {
        super.onStop();

        if(authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
