package com.example.moveoassignment.view.fragments.login;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Method;
import java.util.function.Function;

public abstract class ViewBindedFragment<T> extends Fragment {
    private T binding;
    private final Function<View, T> bindSupplier;

    public ViewBindedFragment(@LayoutRes int contentLayoutId, Function<View, T> bindSupplier) {
        super(contentLayoutId);
        this.bindSupplier = bindSupplier;
    }

    @SuppressWarnings("unchecked")
    public ViewBindedFragment(@LayoutRes int contentLayoutId, Class<T> viewBindingClass) {
        this(contentLayoutId, view -> {
            try {
                Method bindMethod = viewBindingClass.getDeclaredMethod("bind", View.class);
                return (T) bindMethod.invoke(null, view);
            } catch (Exception e) {
                throw new RuntimeException("Error has occurred while invoking bind(View)", e);
            }
        });
        try {
            viewBindingClass.getDeclaredMethod("bind", View.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("bind(View) method is missing. Class: " + viewBindingClass.getName());
        }
    }

    protected T getBinding() {
        return binding;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding = bindSupplier.apply(view);
        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
