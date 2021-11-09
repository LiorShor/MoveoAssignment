package com.example.moveoassignment.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.moveoassignment.R;
import com.example.moveoassignment.databinding.ActivityLoginBinding;
import com.example.moveoassignment.view.Communicator;
import com.example.moveoassignment.view.dialogs.ForgotPasswordDialog;
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

    @Override
    public void onBackPressed() {
        if (isSignInFragment) {
            this.moveTaskToBack(true);
        } else {
            super.onBackPressed();
            isSignInFragment = true;
        }
    }

    public void showForgotPasswordDialog(){
        FragmentManager fm = getSupportFragmentManager();
        ForgotPasswordDialog newTaskDialog = ForgotPasswordDialog.newInstance();
        newTaskDialog.show(fm, null);
    }
}