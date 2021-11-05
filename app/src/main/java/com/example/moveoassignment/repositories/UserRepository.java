package com.example.moveoassignment.repositories;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserRepository {
    private static final String TAG = "Error";
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static UserRepository instance;
    private final MutableLiveData<FirebaseUser> loggedInUser = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loggedOutLiveData = new MutableLiveData<>();

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public UserRepository() {
        if (mAuth.getCurrentUser() != null) {
            loggedInUser.postValue(mAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }

    public MutableLiveData<FirebaseUser> getLoggedInUser() {
        return loggedInUser;
    }

    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(authResult -> {
                    if (authResult.isSuccessful()) {
                        loggedInUser.postValue(mAuth.getCurrentUser());
                    }
                });
    }

    public void registerNewUser(String fullName, String email, String password, Context context) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        assert firebaseUser != null;
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fullName).build();
                        firebaseUser.updateProfile(profileUpdates);
/*                        String uid = firebaseUser.getUid();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = database.getReference("users").child(uid);
                        databaseReference.setValue(user);*/
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                });
    }

    public void logOutFromUser() {
        mAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }
}
