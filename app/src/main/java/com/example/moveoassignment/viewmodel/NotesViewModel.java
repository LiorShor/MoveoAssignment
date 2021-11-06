package com.example.moveoassignment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moveoassignment.model.Note;
import com.example.moveoassignment.repositories.NotesRepository;

import java.util.Map;

public class NotesViewModel extends ViewModel {
    private MutableLiveData<Map<String, Note>> mNotesMap;
    private NotesRepository mNotesRepository;
    public NotesViewModel() {
    }

    public void init() {
        if(mNotesMap == null) {
            mNotesRepository = NotesRepository.getInstance();
            mNotesMap = mNotesRepository.getNotesMap();
        }
    }

    public MutableLiveData<Map<String, Note>> getNotesMap() {
        return mNotesMap;
    }
    public void writeNewNoteToDatabase(Note note){
        mNotesRepository.addNoteToRepository(note);
    }
    public LiveData<Boolean> getIsDataChanged(){
        return mNotesRepository.getIsDataChanged();
    }

    public void logout() {
        mNotesRepository.logOut();
    }
}
