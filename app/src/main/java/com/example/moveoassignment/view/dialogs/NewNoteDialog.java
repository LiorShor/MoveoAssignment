package com.example.moveoassignment.view.dialogs;

import static android.content.Context.LOCATION_SERVICE;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private final Note mEditNote;

    public NewNoteDialog(NotesViewModel mNotesViewModel, Note existingNote) {
        this.mNotesViewModel = mNotesViewModel;
        this.mEditNote = existingNote;
    }

    public static NewNoteDialog newInstance(NotesViewModel notesViewModel, Note existingNote) {
        return new NewNoteDialog(notesViewModel, existingNote);
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
                deleteButton.setVisibility(View.INVISIBLE);
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

/*        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);*/
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

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    // Permission Denied
                    Toast.makeText(getContext(), "no permissions", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
