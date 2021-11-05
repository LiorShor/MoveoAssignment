package com.example.moveoassignment.viewmodel;

import static com.example.moveoassignment.model.Validation.isEmailValid;
import static com.example.moveoassignment.model.Validation.isNameValid;
import static com.example.moveoassignment.model.Validation.isPasswordValid;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moveoassignment.R;
import com.example.moveoassignment.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class RegisterViewModel extends ViewModel {
    private final MutableLiveData<FormState> registerFormState = new MutableLiveData<>();
    private final UserRepository userRepository;

    public RegisterViewModel() {
        userRepository = UserRepository.getInstance();
    }

    public LiveData<FormState> getRegisterFormState() {
        return registerFormState;
    }

    public LiveData<FirebaseUser> getUser() {
        return userRepository.getLoggedInUser();
    }

    public void registerDataChanged(String fullName, String email, String password) {
        if (!isNameValid(fullName)) {
            registerFormState.setValue(new FormState(R.string.enter_full_name, null, null));
        } else if (!isEmailValid(email)) {
            registerFormState.setValue(new FormState(null, R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new FormState(null, null, R.string.invalid_password));
        } else {
            registerFormState.setValue(new FormState(true));
        }
    }

    public void register(String fullName, String email, String password, Context context) {
        userRepository.registerNewUser(fullName, email, password, context);
    }
}
