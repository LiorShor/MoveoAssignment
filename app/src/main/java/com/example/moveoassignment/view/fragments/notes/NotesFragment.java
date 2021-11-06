package com.example.moveoassignment.view.fragments.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moveoassignment.R;
import com.example.moveoassignment.databinding.FragmentNotesBinding;
import com.example.moveoassignment.view.adapters.NotesAdapter;


public class NotesFragment extends Fragment {
    private FragmentNotesBinding mFragmentNotesBinding;
    private NotesAdapter mNoteAdapter;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentNotesBinding = FragmentNotesBinding.inflate(inflater,null,false);
        return mFragmentNotesBinding.getRoot();
    }
}