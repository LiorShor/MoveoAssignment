package com.example.moveoassignment.viewmodel;

public class FormState {
    private final Integer mEmailError;
    private final Integer mPasswordError;
    private final Integer mFullNameError;
    private final boolean mIsDataValid;

    public FormState(Integer fullNameError, Integer emailError, Integer passwordError) {
        this.mEmailError = emailError;
        this.mPasswordError = passwordError;
        this.mFullNameError = fullNameError;
        this.mIsDataValid = false;
    }

    public FormState(boolean isDataValid) {
        this.mEmailError = null;
        this.mPasswordError = null;
        this.mFullNameError = null;
        this.mIsDataValid = isDataValid;
    }

    public Integer getUsernameError() {
        return mEmailError;
    }

    public Integer getFullNameError() {
        return mFullNameError;
    }

    public Integer getPasswordError() {
        return mPasswordError;
    }

    public boolean ismIsDataValid() {
        return mIsDataValid;
    }
}