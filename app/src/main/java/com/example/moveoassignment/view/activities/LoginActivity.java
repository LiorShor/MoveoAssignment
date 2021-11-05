package com.example.moveoassignment.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;

import com.example.moveoassignment.R;
import com.example.moveoassignment.databinding.ActivityLoginBinding;
import com.example.moveoassignment.view.Communicator;
import com.example.moveoassignment.view.fragments.LoginFragment;
import com.example.moveoassignment.view.fragments.RegistrationFragment;
import com.example.moveoassignment.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements Communicator {
    private ActivityLoginBinding mActivityLoginBinding;
    private LoginViewModel mLoginViewModel;
    private boolean isSignInFragment = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mActivityLoginBinding.getRoot());
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mLoginViewModel.getUser().observe(this, user ->
        {
            if (user != null) {
                Intent intent = new Intent(this, NotesActivity.class);
                startActivity(intent);
            }
        });
        navigateToSignIn();
    }

    @Override
    public void navigateToRegistration() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_right, R.anim.enter_from_right, R.anim.exit_from_right);
        fragmentTransaction.replace(R.id.loginFragmentsFrame, new RegistrationFragment()).addToBackStack(null).commit();
        isSignInFragment = false;
    }

    @Override
    public void navigateToSignIn() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.loginFragmentsFrame, new LoginFragment()).commit();
        isSignInFragment = true;
    }

    // Implement onBackPressed so we will not get struggle with sign in / sign up fragments.
    // When user presses back at the login activity, exit the app.
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (isSignInFragment) {
            // Execute it later, so we will not break the event handling.
            /*  new Handler().post(LoginActivity.this::finish);*/
            finish();
        } else {
            // Turn the flag on cause we were back from SignUpFragment
            isSignInFragment = true;
        }
    }

    /***
     * Use this method when user selects to sign out.
     * We will clear shared preferences and navigate him back to the login screen
     * @param context used for intent
     */
    public static void signOutFromApplication(ContextWrapper context) {
        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
        context.startActivity(intent);
    }
}