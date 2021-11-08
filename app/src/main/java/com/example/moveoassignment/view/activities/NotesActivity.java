package com.example.moveoassignment.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.example.moveoassignment.R;
import com.example.moveoassignment.databinding.ActivityNotesBinding;
import com.example.moveoassignment.model.Note;
import com.example.moveoassignment.view.adapters.NotesAdapter;
import com.example.moveoassignment.view.dialogs.NewNoteDialog;
import com.example.moveoassignment.viewmodel.LoginRegisterViewModel;
import com.example.moveoassignment.viewmodel.NotesViewModel;

public class NotesActivity extends AppCompatActivity implements NotesAdapter.OnNoteListener {
    private ActivityNotesBinding mActivityNotesBinding;
    private LoginRegisterViewModel mLoginRegisterViewModel;

    public NotesViewModel getNotesViewModel() {
        return mNotesViewModel;
    }

    private NotesViewModel mNotesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNotesBinding = ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(mActivityNotesBinding.getRoot());
        mLoginRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);

/*        mNotesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        mNotesViewModel.init();*/
        NavController navController = Navigation.findNavController(this,  R.id.noteContainer);
        NavigationUI.setupWithNavController(mActivityNotesBinding.bottomNavigationView,navController);

        mLoginRegisterViewModel.getUser().observe(this,firebaseUser ->mActivityNotesBinding.userNameTextView.setText(String.format("Hello,\n%s",firebaseUser.getDisplayName())));
        mActivityNotesBinding.exitButton.setOnClickListener(view -> {

            mNotesViewModel.logout();
            mLoginRegisterViewModel.logOut();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        });

        mActivityNotesBinding.addNotefloatingActionButton.setOnClickListener(view ->
        {
            showNewTaskDialog(null);
        } );

    }

    public void showNewTaskDialog(Note note){
        FragmentManager fm = getSupportFragmentManager();
        NewNoteDialog newTaskDialog = NewNoteDialog.newInstance(mNotesViewModel,note);
        newTaskDialog.show(fm, null);
    }
    public void setNotesViewModel(NotesViewModel notesViewModel) {
        mNotesViewModel = notesViewModel;
    }

    @Override
    public void onNoteClick(Note note) {
        showNewTaskDialog(note);

    }
}