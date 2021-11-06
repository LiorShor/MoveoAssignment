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

public class LoginRegisterViewModel extends ViewModel {
    //TODO: Implement forgotpassword via firebase
    private final MutableLiveData<FormState> registerFormState = new MutableLiveData<>();
    private final MutableLiveData<FormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> loggedInUser;
    private final UserRepository userRepository;

    public LoginRegisterViewModel() {
        userRepository = UserRepository.getInstance();
        loggedInUser = userRepository.getLoggedInUser();
    }

    public void login(String email, String password, Context context) {
        userRepository.login(email, password, context);
    }

    public void logOut() {
        this.userRepository.logOutFromUser();
    }


    public void register(String fullName, String email, String password, Context context) {
        userRepository.registerNewUser(fullName, email, password, context);
    }

    public void loginDataVerify(String email, String password) {

        if (!isEmailValid(email)) {
            loginFormState.setValue(new FormState(null, R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new FormState(null, null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new FormState(true));
        }
    }

    public void registerDataVerify(String fullName, String email, String password) {
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

    public LiveData<FormState> getRegisterFormState() {
        return registerFormState;
    }

    public MutableLiveData<FormState> getLoginFormState() {
        return loginFormState;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return userRepository.getLoggedOutLiveData();
    }

    public LiveData<String> getLoginString() {
        return userRepository.getLoginErrorString();
    }

    public LiveData<String> getRegisterString() {
        return userRepository.getRegisterErrorString();
    }

    public LiveData<FirebaseUser> getUser() {
        return loggedInUser;
    }
}
