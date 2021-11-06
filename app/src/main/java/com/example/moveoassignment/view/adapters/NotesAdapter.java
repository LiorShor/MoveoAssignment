package com.example.moveoassignment.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveoassignment.databinding.NoteRowBinding;
import com.example.moveoassignment.model.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private ArrayList<Note> mNotesList;
    private final Context mContext;

    public NotesAdapter(Map<String,Note> noteMap, Context context) {
        mNotesList = new ArrayList<>();
        this.mNotesList.addAll(noteMap.values());
        this.mContext = context;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(NoteRowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        Note note = mNotesList.get(position);
        holder.mNoteRowBinding.noteTitleTextView.setText(note.getTitle());
        holder.mNoteRowBinding.noteDateCreationTextView.setText(note.getCreationDate().toString());
    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final NoteRowBinding mNoteRowBinding;

        public ViewHolder(@NonNull NoteRowBinding noteRowBinding) {
            super(noteRowBinding.getRoot());
            this.mNoteRowBinding = noteRowBinding;
        }
    }
    public void setStatusesList(Map<String,Note> notesMap) {
        mNotesList.clear();
        this.mNotesList.addAll( notesMap.values());
        notifyDataSetChanged();
    }


}
