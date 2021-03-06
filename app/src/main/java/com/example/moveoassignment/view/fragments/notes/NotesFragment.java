package com.example.moveoassignment.view.fragments.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moveoassignment.databinding.FragmentNotesBinding;
import com.example.moveoassignment.model.Note;
import com.example.moveoassignment.view.activities.NotesActivity;
import com.example.moveoassignment.view.adapters.NotesAdapter;
import com.example.moveoassignment.viewmodel.NotesViewModel;

import java.util.Map;

public class NotesFragment extends Fragment {
    private FragmentNotesBinding mFragmentNotesBinding;
    private NotesViewModel mNotesViewModel;

    public NotesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView notesRecyclerView = mFragmentNotesBinding.notesRecyclerView;
        final TextView emptyView = mFragmentNotesBinding.emptyView;
        if (getActivity() != null) {
            mNotesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
            mNotesViewModel.init();
            ((NotesActivity) getActivity()).setNotesViewModel(mNotesViewModel);
        }
        NotesAdapter mNoteAdapter = new NotesAdapter(mNotesViewModel.getNotesMap().getValue(), (NotesAdapter.OnNoteListener) getActivity());
        notesRecyclerView.setAdapter(mNoteAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNotesViewModel.getIsDataChanged().observe(getViewLifecycleOwner(), isDataChanged ->
        {
            if (isDataChanged) {
                Map<String, Note> notesMap = mNotesViewModel.getNotesMap().getValue();
                if (notesMap.size() > 0) {
                    mNoteAdapter.setStatusesList(notesMap);
                    notesRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                } else {
                    notesRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentNotesBinding = FragmentNotesBinding.inflate(inflater, null, false);
        return mFragmentNotesBinding.getRoot();
    }
}