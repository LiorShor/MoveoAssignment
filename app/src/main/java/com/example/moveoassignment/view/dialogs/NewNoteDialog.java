package com.example.moveoassignment.view.dialogs;

import static android.content.Context.LOCATION_SERVICE;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.moveoassignment.R;
import com.example.moveoassignment.databinding.DialogNewNoteBinding;
import com.example.moveoassignment.model.Note;
import com.example.moveoassignment.viewmodel.NotesViewModel;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class NewNoteDialog extends DialogFragment {

    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private final NotesViewModel mNotesViewModel;
    private DialogNewNoteBinding mDialogNewNoteBinding;
    private final Note mEditNote;
    private static final String TAG = "Permissions";


    public NewNoteDialog(NotesViewModel mNotesViewModel, Note existingNote) {
        this.mNotesViewModel = mNotesViewModel;
        this.mEditNote = existingNote;
        registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if (result) {
                        getLocation();
                    } else {
                        Log.e(TAG, "onActivityResult: PERMISSION DENIED");
                    }
                });
    }

    public static NewNoteDialog newInstance(NotesViewModel notesViewModel, Note existingNote) {
        return new NewNoteDialog(notesViewModel, existingNote);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final DatePicker datePicker = mDialogNewNoteBinding.datePicker;
        final Button cancelButton = mDialogNewNoteBinding.cancelButton;
        final Button saveButton = mDialogNewNoteBinding.saveButton;
        final Button deleteButton = mDialogNewNoteBinding.deleteButton;
        final TextView dialogTitleTextView = mDialogNewNoteBinding.dialogTitleTextView;
        final EditText titleEditText = mDialogNewNoteBinding.titleEditText;
        final EditText bodyEditText = mDialogNewNoteBinding.bodyEditText;
        if (mEditNote != null) {
            dialogTitleTextView.setText(R.string.edit_note);
            titleEditText.setText(mEditNote.getTitle());
            bodyEditText.setText(mEditNote.getBody());
            deleteButton.setVisibility(View.VISIBLE);
            Calendar cal = setCalendar(mEditNote.getCreationDate());
            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), null);
            deleteButton.setOnClickListener(view1 -> {
                mNotesViewModel.deleteNote(mEditNote);
                deleteButton.setVisibility(View.GONE);
                this.dismiss();
            });
        }
        cancelButton.setOnClickListener(view1 -> this.dismiss());
        saveButton.setOnClickListener(view1 ->
        {
            String title = titleEditText.getText().toString();
            String body = bodyEditText.getText().toString();
            Date date = getDateFromDatePicker(datePicker);
            if (mEditNote == null) {
                Note note = new Note(UUID.randomUUID().toString(), date, title, body, getLocation());
                mNotesViewModel.writeNoteToDatabase(note);
            } else {
                mNotesViewModel.updateExistingNote(title, body, date, getLocation(), mEditNote);
            }
            this.dismiss();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDialogNewNoteBinding = DialogNewNoteBinding.inflate(LayoutInflater.from(getContext()));
        if (getDialog() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);
        }
        return mDialogNewNoteBinding.getRoot();
    }

    private static Calendar setCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }


    //Get location
    @SuppressLint("MissingPermission")
    public com.example.moveoassignment.model.LatLng getLocation() {
        com.example.moveoassignment.model.LatLng latLng = null;
        if (getActivity() != null) {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
            Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (myLocation == null) {
                myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                latLng = new com.example.moveoassignment.model.LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            }
        }
        return latLng;
    }
}
