package com.example.moveoassignment.model;

import android.util.Patterns;

public class Validation {
    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public static boolean isNameValid(String fullName) {
        return fullName != null && fullName.contains(" ") && fullName.trim().length() > 2;
    }

    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return false;
        }
    }
}
