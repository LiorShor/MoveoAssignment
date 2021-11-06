package com.example.moveoassignment.view.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.moveoassignment.R;
import com.example.moveoassignment.databinding.DialogNewNoteBinding;
import com.example.moveoassignment.model.Note;
import com.example.moveoassignment.viewmodel.NotesViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class NewNoteDialog extends DialogFragment {
    private final NotesViewModel mNotesViewModel;
    private DialogNewNoteBinding mDialogNewNoteBinding;
    private final SimpleDateFormat dateFormatForDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public NewNoteDialog(NotesViewModel mNotesViewModel) {
        this.mNotesViewModel = mNotesViewModel;
    }

    public static NewNoteDialog newInstance(NotesViewModel notesViewModel) {
        return new NewNoteDialog(notesViewModel);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final DatePicker datePicker = mDialogNewNoteBinding.datePicker;
        mDialogNewNoteBinding.saveButton.setOnClickListener(view1 ->
        {
            String title = mDialogNewNoteBinding.titleEditText.getText().toString();
            String body = mDialogNewNoteBinding.bodyTextView.getText().toString();
            Date date = getDateFromDatePicker(datePicker);
            Note note = new Note(UUID.randomUUID().toString(),date,body,title);
            mNotesViewModel.writeNewNoteToDatabase(note);
        });
        mDialogNewNoteBinding.cancelButton.setOnClickListener(view1 ->
        {
            this.dismiss();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDialogNewNoteBinding = DialogNewNoteBinding.inflate(LayoutInflater.from(getContext()));
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);
        return mDialogNewNoteBinding.getRoot();

    }
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
