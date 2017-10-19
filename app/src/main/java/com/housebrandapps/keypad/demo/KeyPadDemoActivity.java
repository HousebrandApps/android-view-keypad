package com.housebrandapps.keypad.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.housebrandapps.keypad.demo.databinding.ActivityKeyPadDemoBinding;


public class KeyPadDemoActivity extends AppCompatActivity implements KeyPadDemoViewModel.KeyPadDemoView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_pad_demo);
        ActivityKeyPadDemoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_key_pad_demo);
        binding.setViewModel(new KeyPadDemoViewModel(this));
    }

    @Override
    public void showValue(String value) {
        Toast.makeText(this, value, Toast.LENGTH_LONG).show();
        Log.v(this.getClass().getSimpleName(), "onOkay value: " + value);
    }
}
