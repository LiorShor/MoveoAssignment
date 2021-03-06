package com.example.moveoassignment.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveoassignment.databinding.NoteRowBinding;
import com.example.moveoassignment.model.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private final ArrayList<Note> mNotesList;
    private final OnNoteListener mOnNoteListener;
    private final SimpleDateFormat dateFormatForDate = new SimpleDateFormat("dd/MM/yyyy 'at' HH:mm", Locale.getDefault());

    public NotesAdapter(Map<String, Note> noteMap, OnNoteListener onNoteListener) {
        mNotesList = new ArrayList<>();
        this.mNotesList.addAll(noteMap.values());
        Collections.sort(mNotesList);
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(NoteRowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false),mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        Note note = mNotesList.get(position);
        holder.mNoteRowBinding.noteTitleTextView.setText(note.getTitle());
        holder.mNoteRowBinding.noteDateCreationTextView.setText(dateFormatForDate.format(note.getCreationDate()));
    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final NoteRowBinding mNoteRowBinding;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull NoteRowBinding noteRowBinding, OnNoteListener onNoteListener) {
            super(noteRowBinding.getRoot());
            this.mNoteRowBinding = noteRowBinding;
            this.onNoteListener = onNoteListener;
            noteRowBinding.getRoot().setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(mNotesList.get(getAdapterPosition()));
        }
    }

    public void setStatusesList(Map<String, Note> notesMap) {
        mNotesList.clear();
        this.mNotesList.addAll(notesMap.values());
        Collections.sort(mNotesList);
        notifyDataSetChanged();
    }

    public interface OnNoteListener {
        void onNoteClick(Note note);
    }
}
