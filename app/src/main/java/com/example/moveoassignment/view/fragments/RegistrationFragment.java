package com.example.moveoassignment.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.moveoassignment.databinding.FragmentRegisterBinding;

import com.example.moveoassignment.viewmodel.RegisterViewModel;

public class RegistrationFragment extends Fragment {
    private FragmentRegisterBinding mFragmentRegisterBinding;
    private RegisterViewModel mRegisterViewModel;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText fullNameEditText = mFragmentRegisterBinding.fullNameInputEditText;
        final EditText emailEditText = mFragmentRegisterBinding.emailTextInputEditText;
        final EditText passwordEditText = mFragmentRegisterBinding.passwordTextInputEditText;
        final Button registerButton = mFragmentRegisterBinding.registerButton;
        mRegisterViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        mRegisterViewModel.getRegisterFormState().observe(getViewLifecycleOwner(), registerFormState -> {
            if (registerFormState == null) {
                return;
            }
            registerButton.setEnabled(registerFormState.ismIsDataValid());
            if (registerFormState.getUsernameError() != null) {
                emailEditText.setError(getString(registerFormState.getUsernameError()));
            }
            if (registerFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(registerFormState.getPasswordError()));
            }
            if (registerFormState.getFullNameError() != null) {
                fullNameEditText.setError(getString(registerFormState.getFullNameError()));
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
                mRegisterViewModel.registerDataChanged(fullNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        fullNameEditText.addTextChangedListener(afterTextChangedListener);
        registerButton.setOnClickListener(view1 ->
        {
            mRegisterViewModel.register(
                    fullNameEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    getContext());
            mRegisterViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
                if (user == null) {
                    Toast.makeText(getContext(), "Error, could not create new user", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentRegisterBinding = FragmentRegisterBinding.inflate(LayoutInflater.from(getContext()));
        return mFragmentRegisterBinding.getRoot();
    }
}
