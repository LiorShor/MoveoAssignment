package com.example.moveoassignment.viewmodel;

import static com.example.moveoassignment.model.Validation.isEmailValid;
import static com.example.moveoassignment.model.Validation.isPasswordValid;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moveoassignment.R;
import com.example.moveoassignment.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<FirebaseUser> loggedInUser;
    private final MutableLiveData<FormState> loginFormState = new MutableLiveData<>();
    private final UserRepository userRepository;

    public LiveData<FormState> getRegisterFormState() {
        return loginFormState;
    }

    public LoginViewModel() {
        userRepository = UserRepository.getInstance();
        loggedInUser = userRepository.getLoggedInUser();
    }

    public void login(String email, String password) {
        userRepository.login(email, password);
    }

    public LiveData<FirebaseUser> getUser() {
        return loggedInUser;
    }

    public void loginDataChanged(String email, String password) {

        if (!isEmailValid(email)) {
            loginFormState.setValue(new FormState(null, R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new FormState(null, null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new FormState(true));
        }
    }
}
