package com.housebrandapps.keypad.demo.pages;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.housebrandapps.keypad.demo.KeyPadDemoViewModel;
import com.housebrandapps.keypad.demo.databinding.FragDemoAllAttrBinding;
import com.housebrandapps.keypad.demo.databinding.FragDemoBasicBinding;

public class DemoAllAttrFragment extends Fragment implements KeyPadDemoViewModel.KeyPadDemoView {

    public DemoAllAttrFragment() {
    }

    public static DemoAllAttrFragment newInstance() {
        return new DemoAllAttrFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragDemoAllAttrBinding binding = FragDemoAllAttrBinding.inflate(inflater, container, false);
        binding.setViewModel(new KeyPadDemoViewModel(this));
        return binding.getRoot();
    }

    @Override
    public void showValue(String value) {
        Snackbar.make(getView(), "PIN: " + value, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
