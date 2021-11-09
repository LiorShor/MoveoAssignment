package com.example.moveoassignment.view.fragments.login;

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

import com.example.moveoassignment.viewmodel.LoginRegisterViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationFragment extends Fragment {
    private FragmentRegisterBinding mFragmentRegisterBinding;
    private LoginRegisterViewModel mRegisterViewModel;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText fullNameEditText = mFragmentRegisterBinding.fullNameInputEditText;
        final EditText emailEditText = mFragmentRegisterBinding.emailTextInputEditText;
        final EditText passwordEditText = mFragmentRegisterBinding.passwordTextInputEditText;
        final TextInputLayout fullNameLayout = mFragmentRegisterBinding.fullNameTextInputLayout;
        final TextInputLayout emailLayout = mFragmentRegisterBinding.emailTextInputLayout;
        final TextInputLayout passwordLayout = mFragmentRegisterBinding.passwordTextInputLayout;
        final Button registerButton = mFragmentRegisterBinding.registerButton;
        mRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);
        mRegisterViewModel.getRegisterFormState().observe(getViewLifecycleOwner(), registerFormState -> {
            if (registerFormState == null) {
                return;
            }
            registerButton.setEnabled(registerFormState.ismIsDataValid());
            if (registerFormState.getUsernameError() != null) {
                emailLayout.setError(getString(registerFormState.getUsernameError()));
            } else {
                emailLayout.setError(null);
            }
            if (registerFormState.getPasswordError() != null) {
                passwordLayout.setError(getString(registerFormState.getPasswordError()));
            } else {
                passwordLayout.setError(null);
            }
            if (registerFormState.getFullNameError() != null) {
                fullNameLayout.setError(getString(registerFormState.getFullNameError()));
            } else {
                fullNameLayout.setError(null);
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
                mRegisterViewModel.registerDataVerify(fullNameEditText.getText().toString(),
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
            mRegisterViewModel.getRegisterErrorString().observe(getViewLifecycleOwner(), string -> {
                Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentRegisterBinding = FragmentRegisterBinding.inflate(LayoutInflater.from(getContext()));
        return mFragmentRegisterBinding.getRoot();
    }
}
