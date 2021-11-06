package com.example.moveoassignment.view.fragments.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moveoassignment.databinding.FragmentNotesBinding;
import com.example.moveoassignment.view.activities.NotesActivity;
import com.example.moveoassignment.view.adapters.NotesAdapter;
import com.example.moveoassignment.viewmodel.NotesViewModel;


public class NotesFragment extends Fragment {
    private FragmentNotesBinding mFragmentNotesBinding;
    private NotesViewModel mNotesViewModel;
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
        if(getActivity()!=null) {
            mNotesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
            mNotesViewModel.init();
            ((NotesActivity) getActivity()).setNotesViewModel(mNotesViewModel);
            mNoteAdapter = new NotesAdapter(mNotesViewModel.getNotesMap().getValue(), getContext());
            mFragmentNotesBinding.notesRecyclerView.setAdapter(mNoteAdapter);
            mFragmentNotesBinding.notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        mNotesViewModel.getIsDataChanged().observe(getViewLifecycleOwner(),isDataChanged ->
        {
            if (isDataChanged)
            {
                mNoteAdapter.setStatusesList(mNotesViewModel.getNotesMap().getValue());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentNotesBinding = FragmentNotesBinding.inflate(inflater,null,false);
        return mFragmentNotesBinding.getRoot();
    }
}