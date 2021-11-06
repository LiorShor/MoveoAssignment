package com.example.moveoassignment.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import com.example.moveoassignment.R;
import com.example.moveoassignment.databinding.ActivityNotesBinding;
import com.example.moveoassignment.viewmodel.LoginRegisterViewModel;

public class NotesActivity extends AppCompatActivity {
    private ActivityNotesBinding mActivityNotesBinding;
    private LoginRegisterViewModel mLoginRegisterViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNotesBinding = ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(mActivityNotesBinding.getRoot());
        NavController navController = Navigation.findNavController(this,  R.id.noteContainer);
        NavigationUI.setupWithNavController(mActivityNotesBinding.bottomNavigationView,navController);

        mLoginRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);
        mLoginRegisterViewModel.getUser().observe(this,firebaseUser ->mActivityNotesBinding.userNameTextView.setText(String.format("Hello %s",firebaseUser.getDisplayName())));
        mActivityNotesBinding.exitButton.setOnClickListener(view -> {

            mLoginRegisterViewModel.logOut();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        });


    }
}