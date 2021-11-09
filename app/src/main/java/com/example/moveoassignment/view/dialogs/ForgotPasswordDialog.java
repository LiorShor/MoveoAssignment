package com.example.moveoassignment.view.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.moveoassignment.R;
import com.example.moveoassignment.databinding.ForgotPasswordBinding;
import com.example.moveoassignment.viewmodel.LoginRegisterViewModel;

public class ForgotPasswordDialog extends DialogFragment {
    private  LoginRegisterViewModel mUserViewModel;
    private ForgotPasswordBinding mForgotPasswordBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mForgotPasswordBinding = ForgotPasswordBinding.inflate(inflater,container,false);
        return mForgotPasswordBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);
        setOnCLickRecover();
    }

    public static ForgotPasswordDialog newInstance() {
        return new ForgotPasswordDialog();
    }

    private void setOnCLickRecover() {
        final Button recoverLostPassword = mForgotPasswordBinding.recoverButton;
        final EditText emailTextView = mForgotPasswordBinding.emailTextInputEditText;
        recoverLostPassword.setOnClickListener(view ->
        {
            String email = emailTextView.getText().toString();
            mUserViewModel.recoverLostPassword(email,getContext());
            mUserViewModel.getForgotPasswordErrorString().observe(getViewLifecycleOwner(), result ->
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show());
        });
    }


}