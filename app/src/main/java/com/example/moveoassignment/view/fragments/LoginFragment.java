package com.example.moveoassignment.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.moveoassignment.databinding.FragmentLoginBinding;
import com.example.moveoassignment.view.activities.LoginActivity;
import com.example.moveoassignment.viewmodel.LoginViewModel;


public class LoginFragment extends Fragment {
    private LoginViewModel mLoginViewModel;
    private FragmentLoginBinding mDialogLoginBinding;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDialogLoginBinding = FragmentLoginBinding.inflate(LayoutInflater.from(getContext()));
        return mDialogLoginBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button registerButton = mDialogLoginBinding.registerButton;
        final Button loginButton = mDialogLoginBinding.loginButton;
        final EditText emailEditText = mDialogLoginBinding.emailTextInputEditText;
        final EditText passwordEditText = mDialogLoginBinding.passwordTextInputEditText;
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        registerButton.setOnClickListener(view1 -> {
            if (getActivity() != null) {
                ((LoginActivity) getActivity()).navigateToRegistration();
            }
        });
        mLoginViewModel.getRegisterFormState().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.ismIsDataValid());
            if (loginFormState.getUsernameError() != null) {
                emailEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                mLoginViewModel.loginDataChanged(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        loginButton.setOnClickListener(view1 -> mLoginViewModel.login(
                emailEditText.getText().toString(),
                passwordEditText.getText().toString()));


    }
}