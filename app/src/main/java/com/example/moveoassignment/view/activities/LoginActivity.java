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
import com.example.moveoassignment.view.fragments.login.LoginFragment;
import com.example.moveoassignment.view.fragments.login.RegistrationFragment;
import com.example.moveoassignment.viewmodel.LoginRegisterViewModel;

public class LoginActivity extends AppCompatActivity implements Communicator {
    private ActivityLoginBinding mActivityLoginBinding;
    private LoginRegisterViewModel mLoginRegisterViewModel;
    private boolean isSignInFragment = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mActivityLoginBinding.getRoot());
        mLoginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);
        mLoginRegisterViewModel.getLoggedOutLiveData().observe(this, isLoggedOut ->
        {
            if (!isLoggedOut) {
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
}