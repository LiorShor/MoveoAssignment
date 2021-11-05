package com.example.moveoassignment.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.moveoassignment.databinding.ActivityNotesBinding;

public class NotesActivity extends AppCompatActivity {
    private ActivityNotesBinding mActivityNotesBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNotesBinding = ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(mActivityNotesBinding.getRoot());
    }
}