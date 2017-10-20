package com.housebrandapps.keypad.demo.pages;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.housebrandapps.keypad.demo.KeyPadDemoViewModel;
import com.housebrandapps.keypad.demo.databinding.FragDemoBasicBinding;

public class DemoBasicFragment extends Fragment implements KeyPadDemoViewModel.KeyPadDemoView {

    public DemoBasicFragment() {
    }

    public static DemoBasicFragment newInstance() {
        return new DemoBasicFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragDemoBasicBinding binding = FragDemoBasicBinding.inflate(inflater, container, false);
        binding.setViewModel(new KeyPadDemoViewModel(this));
        return binding.getRoot();
    }

    @Override
    public void showValue(String value) {
        Snackbar.make(getView(), "PIN: " + value, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
