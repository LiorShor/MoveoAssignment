package com.example.moveoassignment.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.moveoassignment.model.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NotesRepository {
    private static NotesRepository instance;
    //    private static DataLoadListener dataLoadListener;
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final MutableLiveData<Boolean> isDataChanged = new MutableLiveData<>();
    private final Map<String, Note> mNotesMap;

    public NotesRepository() {
        mNotesMap = new HashMap<>();
    }

    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://moveoassignment-default-rtdb.europe-west1.firebasedatabase.app");

    public static NotesRepository getInstance() {
        if (instance == null) {
            instance = new NotesRepository();
        }
        return instance;
    }

    private void writeNewNoteToDatabase(Note note) {
        DatabaseReference databaseReference = database.getReference("notes").child(mAuth.getCurrentUser().getUid()).child(note.getNoteID());
        databaseReference.setValue(note);
        mNotesMap.put(note.getNoteID(), note);
        isDataChanged.postValue(true);
    }

    private void getNotesFromDatabase() {
        DatabaseReference reference = database.getReference("notes").child(mAuth.getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                    mNotesMap.put(childDataSnapshot.getKey(), childDataSnapshot.getValue(Note.class));
                }
                isDataChanged.postValue(true);
//                dataLoadListener.onTaskLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addNoteToRepository(Note note) {
        writeNewNoteToDatabase(note);
        MutableLiveData<Map<String, Note>> data = new MutableLiveData<>();
        data.setValue(mNotesMap);
    }

    public MutableLiveData<Boolean> getIsDataChanged() {
        return isDataChanged;
    }
    public MutableLiveData<Map<String, Note>> getNotesMap() {
        getNotesFromDatabase();
        MutableLiveData<Map<String, Note>> data = new MutableLiveData<>();
        data.setValue(mNotesMap);
        return data;
    }

    public void logOut() {
        mNotesMap.clear();
        isDataChanged.postValue(true);
    }
}
